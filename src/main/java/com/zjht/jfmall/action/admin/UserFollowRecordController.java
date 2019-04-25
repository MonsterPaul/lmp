package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.ApplyRecord;
import com.zjht.jfmall.entity.UserFollowRecord;
import com.zjht.jfmall.service.ApplyRecordService;
import com.zjht.jfmall.service.UserFollowRecordService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * APP用户跟进记录 前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class UserFollowRecordController {

    @Autowired
    private ApplyRecordService service;
    @Autowired
    private UserFollowRecordService recordService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private LogService logService;

    @NoNeedAuth
    @RequestMapping(value = "/apply/cw_record.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", service.find(bean));
        return "apply/cw_record";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/apply/cw_record.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto cw_record(HttpServletRequest request, ModelMap modelMap, UserFollowRecord bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.updateOper(bean.getRecordId(), sessionProvider.getUser().getId(), new Date());
            recordService.insert(bean);
            logService.add("添加用户跟进记录");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @NoNeedAuth
    @RequestMapping(value = "/apply/cs_record.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cs_record(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", service.find(bean));
        modelMap.put("platId", bean.getPlatId());
        return "apply/cs_record";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/apply/cs_record.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto cs_record(HttpServletRequest request, ModelMap modelMap, UserFollowRecord bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.updateOper(bean.getRecordId(), sessionProvider.getUser().getId(), new Date());
            recordService.insert(bean);//添加跟进记录
            logService.add("添加用户跟进记录");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/record/cwo_record_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, UserFollowRecord bean) {
        PageInfo<UserFollowRecord> pageInfo = recordService.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(record -> {
            record.setUser(userService.findById(record.getFollowUserId()));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/record/cso_record_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cso_record_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, UserFollowRecord bean) {
        PageInfo<UserFollowRecord> pageInfo = recordService.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(record -> {
            record.setUser(userService.findById(record.getFollowUserId()));
            if (record.getFollowUserId().equals(sessionProvider.getUser().getId())) {
                record.setType(1);
            }
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }


    @NoNeedAuth
    @RequestMapping(value = "/apply/kf_record.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String kf_record(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", service.find(bean));
        return "customer/apply/kf_record";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/apply/kf_record.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto kf_record(HttpServletRequest request, ModelMap modelMap, UserFollowRecord bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.updateOper(bean.getRecordId(), sessionProvider.getUser().getId(), new Date());
            recordService.insert(bean);
            logService.add("添加用户跟进记录");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/record/kfo_record_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto kfo_record_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, UserFollowRecord bean) {
        PageInfo<UserFollowRecord> pageInfo = recordService.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(record -> {
            record.setUser(userService.findById(record.getFollowUserId()));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

}

