package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.AppUserLoanPlat;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * app用户贷款平台 前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class AppUserLoanPlatController {

    @Value("${collection_id}")
    private String collectionId;

    @Autowired
    private AppUserLoanPlatService service;
    @Autowired
    private NetLoanService netLoanService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private ApplyRecordService applyRecordService;
    @Autowired
    private LogService logService;

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/plat/cwo_plat_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cwo_plat_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUserLoanPlat bean) {
        PageInfo<AppUserLoanPlat> pageInfo = service.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(plat -> {
            plat.setNetLoan(netLoanService.findById(plat.getPlatformId()));
            plat.setUser(userService.findById(plat.getServiceUserId()));
            if(StringUtils.isNotBlank(plat.getCollUserId())){
                plat.setCollUser(userService.findById(plat.getCollUserId()).getNickName());
            }else {
                plat.setCollUser("");
            }
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/plat/cso_plat_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cso_plat_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUserLoanPlat bean) {
        PageInfo<AppUserLoanPlat> pageInfo = service.findPageCS(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(plat -> {
            plat.setNetLoan(netLoanService.findById(plat.getPlatformId()));
            plat.setUser(userService.findById(plat.getServiceUserId()));
            if(StringUtils.isNotBlank(plat.getCollUserId())){
                plat.setCollUser(userService.findById(plat.getCollUserId()).getNickName());
            }else {
                plat.setCollUser("");
            }
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/plat/cw_update_back_loan_status.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_delete(HttpServletRequest request, ModelMap modelMap, AppUserLoanPlat bean) {
        try {
            applyRecordService.updateOper(bean.getRecordId(), sessionProvider.getUser().getId(), new Date());
            service.updateBackLoanStatus(bean.getId(), "1", "0");
            logService.add("修改贷款记录平台" + bean.getId());
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/plat/cw_update_collection.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto cw_update_collection(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        AppUserLoanPlat byId = service.findById(id);
        List<User> users = userService.getUserByRoleId(collectionId);
        if (users.size() == 0) {
            return new ResultFailDto("没有催收角色人员可用");
        }
        Collections.shuffle(users);
        try {
            applyRecordService.updateOper(byId.getRecordId(), sessionProvider.getUser().getId(), new Date());
            service.updateCollection(id, users.get(0).getId());
            logService.add("修改用户贷款平台催收人");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }


    //@NoNeedAuth
    //@RequestMapping(value = "/plat/cw_update_collection.do", method = {RequestMethod.POST, RequestMethod.GET})
    //public String cw_update_collection(HttpServletRequest request, ModelMap modelMap, String id) {
    //    WebSite.setParameters(request,modelMap);
    //    AppUserLoanPlat byId = service.findById(id);
    //    modelMap.put("id", id);
    //    modelMap.put("recordId", byId.getRecordId());
    //    modelMap.put("beans", userService.getUserByRoleId(collectionId));
    //    return "apply/cw_collection";
    //}
    //
    //@NoNeedAuth
    //@ResponseBody
    //@RequestMapping(value = "/plat/cw_update_collection.json", method = {RequestMethod.POST, RequestMethod.GET})
    //public ResultDto cw_update_collection(HttpServletRequest request, ModelMap modelMap, String id, String collectionId, String recordId) {
    //    try {
    //        applyRecordService.updateOper(recordId, sessionProvider.getUser().getId(), new Date());
    //        service.updateCollection(id, collectionId);
    //        logService.add("修改用户贷款平台催收人");
    //        return new ResultSuccessDto();
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return new ResultFailDto("处理失败");
    //    }
    //}

    @NoNeedAuth
    @RequestMapping(value = "/plat/cw_update_comm.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cw_update_comm(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("bean", service.findById(id));
        return "apply/cw_comm";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/plat/cw_update_comm.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto cw_update_comm(HttpServletRequest request, ModelMap modelMap, AppUserLoanPlat bean) {
        try {
            applyRecordService.updateOper(bean.getRecordId(), sessionProvider.getUser().getId(), new Date());
            service.updateComm(bean);
            logService.add("修改平台已收佣金" + bean.getRecordId());
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

}

