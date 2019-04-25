package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.AppUserDeposit;
import com.zjht.jfmall.service.AppUserBasicService;
import com.zjht.jfmall.service.AppUserDepositService;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * app用户提现表  前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
@RequestMapping(value = "/deposit")
public class AppUserDepositController {

    @Autowired
    private AppUserDepositService service;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserBasicService basicService;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUserDeposit bean, String name, String phone) {
        WebSite.setParameters(request, modelMap);
        AppUser user = new AppUser();
        user.setPhone(phone);
        user.setName(name);
        bean.setAppUser(user);
        modelMap.put("bean", bean);
        return "deposit/list";
    }

    @ResponseBody
    @RequestMapping(value = "/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUserDeposit bean) {
        PageInfo<AppUserDeposit> pageInfo = service.getPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(deposit -> {
            //用户有效客户数量
            deposit.setCount(service.countUser(deposit.getAppUserId()));
            //计算奖励金额
            deposit.setCountAmount(service.sumAmount(deposit.getAppUserId()));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/deal.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto deal(String id) {
        try {
            logService.add("同意用户提现记录" + id);
            service.updateStatus(id, "1");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @RequestMapping(value = "/see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        AppUser byId = appUserService.findById(bean.getId());
        byId.setObj(basicService.findByAppId(bean.getId()));
        modelMap.put("bean", byId);
        return "appuser/deposit_see";
    }

    @RequestMapping(value = "/record.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String record(HttpServletRequest request,ModelMap modelMap, AppUserDeposit bean) {
        WebSite.setParameters(request, modelMap);
        List<AppUserDeposit> page = service.find(bean, 1, 999999);
        modelMap.put("pageInfo", page);
        return "appuser/record";
    }



}

