package com.zjht.jfmall.action.appApi;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.UserMailList;
import com.zjht.jfmall.enums.BusiStatus;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.UserMailListService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * app用户通讯录  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/userMailLists")
public class UserMailListsController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(UserMailListsController.class);

    @Autowired
    private UserMailListService userMailListService;
    @Autowired
    private AppUserService appUserService;

    /**
     * 通讯录上传
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonResult upload(String userId,String userMailListList) {
        try {

            JsonResult result = checkUser(userId);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            if (StringUtils.isBlank(userMailListList)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            JSONArray jsonArray =JSONArray.parseArray(userMailListList);
            if(jsonArray.size()<0){
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            List<UserMailList> userMailLists =jsonArray.toJavaList(UserMailList.class);
            userMailListService.insert(userId, userMailLists);

            //更新用户通讯录认证状态
            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);
            appUser.setIdenStatus("1");
            appUserService.updateAppUser(appUser);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("UserMailListsController upload error", e);
            return renderServerError();
        }

    }

}

