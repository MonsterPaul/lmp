package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.NetLoanService;
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
 * 网贷大全表 前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
@RequestMapping(value = "/net")
public class NetLoanController {

    @Autowired
    private NetLoanService service;
    @Autowired
    private LogService logService;


    @RequestMapping(value = "/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, NetLoan bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "net/list";
    }

    @ResponseBody
    @RequestMapping(value = "/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, NetLoan bean) {
        PageInfo<NetLoan> pageInfo = service.getPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap) {
        WebSite.setParameters(request,modelMap);
        return "net/add";
    }

    @ResponseBody
    @RequestMapping(value = "/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto v_add(HttpServletRequest request, ModelMap modelMap, NetLoan bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.insert(bean);
            logService.add("添加网贷平台");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @RequestMapping(value = "/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("bean", service.findById(id));
        return "net/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, NetLoan bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.update(bean);
            logService.add("编辑网贷平台");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/o_delete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        try {
            service.deleteById(id);
            logService.add("删除网贷平台");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }
    
}

