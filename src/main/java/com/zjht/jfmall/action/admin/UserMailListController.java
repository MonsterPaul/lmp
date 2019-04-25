package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.UserMailList;
import com.zjht.jfmall.service.UserMailListService;
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
 * app用户通讯录  前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class UserMailListController {

    @Autowired
    private UserMailListService service;

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/mail/cwo_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cwo_appuser_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser bean) {
        PageInfo<UserMailList> pageInfo = service.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

}

