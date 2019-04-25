package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.CallContactDetail;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * app用户表 前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class AppUserController {

    @Autowired
    private AppUserService service;
    @Autowired
    private AppUserBasicService basicService;
    @Autowired
    private CollectionRecordService recordService;
    @Autowired
    private CallContactDetailService detailService;
    @Autowired
    private LogService logService;
    @Autowired
    private AppUserLoanPlatService aulpService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/appuser/cwv_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cwv_list(HttpServletRequest request, ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        modelMap.put("loginNum",userService.getLoginNum());
        return "appuser/cw_list";
    }

    @ResponseBody
    @RequestMapping(value = "/appuser/cwo_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser bean) {
        PageInfo<AppUser> pageInfo = service.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(user -> {
            Map<String, Object> obj = new HashMap<>();
            //已放款平台
            int count = aulpService.count(user.getId());
            obj.put("count", count);
            //已放款金额
            Double comm = aulpService.sumAll(user.getId());
            obj.put("comm", comm == null ? 0D : comm);
            //已收佣金
            Double useComm = aulpService.sumUseCommByUserId(user.getId());
            useComm = useComm == null ? 0D : useComm;
            Double stopComm = aulpService.sumStopCommByUserId(user.getId());
            stopComm = stopComm == null ? 0D : stopComm;
            obj.put("allComm", useComm + stopComm);
            //当前跟进人
            User u = userService.findById(user.getServiceId());
            obj.put("u", u.getNickName());
            //查询用户被邀请类型，查询邀请人信息
            switch (user.getIsInvited()) {
                case 1://app用户邀请
                    user.setInvitationName(service.findInvitationName(user.getId()));
                    break;
                case 2://渠道商邀请
                    user.setInvitationName(userService.findInvitationName(user.getId()));
                    break;
                default:
                    break;
            }
            user.setObj(obj);
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/appuser/cw_update.do", method = RequestMethod.POST)
    public Object updateStatus(String id, String status) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        try {
            logService.add("启用/禁用APP用户：" + id);
            service.updateStatus(id, Integer.valueOf(status) == 0 ? 1 : 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
        return new ResultSuccessDto("操作成功");
    }

    @RequestMapping(value = "/appuser/cw_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        AppUser byId = service.findById(bean.getId());
        byId.setObj(basicService.findByAppId(bean.getId()));
        modelMap.put("bean", byId);
        return "appuser/cw_see";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/appuser/cwo_appuser_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cwo_appuser_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser bean) {
        PageInfo<AppUser> pageInfo = service.findBeinvitedPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/appuser/cwo_call_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cwo_basic_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser bean) {
        PageInfo<CallContactDetail> pageInfo = detailService.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

}

