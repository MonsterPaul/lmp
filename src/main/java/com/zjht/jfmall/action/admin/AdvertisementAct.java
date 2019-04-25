package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.service.AdvertisementService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vip on 2018/11/29.
 */
@Controller
public class AdvertisementAct {

    @Autowired
    private AdvertisementService service;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/ad/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, Advertisement ad) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("ad", ad);
        return "ad/list";
    }

    @ResponseBody
    @RequestMapping(value = "/ad/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, Advertisement ad) {
        PageInfo<Advertisement> pageInfo = service.getPage(ad, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/ad/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap) {
        WebSite.setParameters(request,modelMap);
        return "ad/add";
    }

    @ResponseBody
    @RequestMapping(value = "/ad/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto v_add(HttpServletRequest request, ModelMap modelMap, Advertisement bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.insert(bean);
            logService.add("添加广告");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @RequestMapping(value = "/ad/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("ad", service.findById(id));
        return "ad/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/ad/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, Advertisement bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.update(bean);
            logService.add("编辑广告");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ad/o_delete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String id, int status) {
        WebSite.setParameters(request,modelMap);
        try {
            service.updateStatus(id, status == 1 ? 2 : 1);
            logService.add("停用/启用广告");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

}
