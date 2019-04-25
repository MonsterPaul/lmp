package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.entity.UserFeedback;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.UserFeedbackService;
import com.zjht.jfmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户意见反馈表 前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService service;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/customer/feedback_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, UserFeedback bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "customer/feed/list";
    }

    @ResponseBody
    @RequestMapping(value = "/customer/feedback_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, UserFeedback bean) {
        PageInfo<UserFeedback> pageInfo = service.getPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/customer/feedback_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("bean", service.findById(id));
        return "customer/feed/see";
    }

    @RequestMapping(value = "/customer/feedback_deal.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String feedback_deal(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("bean", service.findById(id));
        return "customer/feed/deal";
    }

    @ResponseBody
    @RequestMapping(value = "/customer/feedback_deal.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, UserFeedback bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.update(bean);
            logService.add("处理用户反馈");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

}

