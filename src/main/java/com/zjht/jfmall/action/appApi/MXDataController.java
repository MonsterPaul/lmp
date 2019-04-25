package com.zjht.jfmall.action.appApi;

import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.entity.CallContactDetail;
import com.zjht.jfmall.service.AppUserBasicService;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.CallContactDetailService;
import com.zjht.jfmall.service.UserMailListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/mxData")
public class MXDataController extends BaseController{
    private final Logger logger = LoggerFactory.getLogger(AppUsersController.class);

    @Autowired
    private AppUserService appUserService;

    /**
     * 新增用户通话记录以及个人信息数据
     * @param postMap
     * @return
     */
    @RequestMapping("/userbasic")
    public JsonResult userbasic(@RequestParam Map<String, String> postMap) {
        try{
            logger.info("新增用户通话记录以及个人信息数据********");
           return appUserService.userbasic(postMap);
        }catch (Exception e){
            logger.error("MXDataController userbasic error", e);
            return renderServerError();
        }
    }
}