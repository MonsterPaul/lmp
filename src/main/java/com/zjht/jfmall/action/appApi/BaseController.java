package com.zjht.jfmall.action.appApi;


import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.enums.BusiStatus;
import com.zjht.jfmall.service.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    private AppUserService  appUserService;


    /**
     * 渲染失败数据
     *
     * @return result
     */
    protected JsonResult renderServerError(String status) {
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setStatus(status);
        return result;
    }

    /**
     * 渲染失败数据（带消息）
     *
     * @param msg 需要返回的消息
     * @return result
     */
    protected JsonResult renderServerError(String status, String msg) {
        JsonResult result = renderServerError(status);
        result.setMsg(msg);
        return result;
    }

    /**
     * 渲染服务器异常数据
     *
     * @return result
     */
    protected JsonResult renderServerError() {
        JsonResult result = renderServerError(BusiStatus.SERVER_ERROR.toString());
        result.setMsg(BusiStatus.SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 渲染成功数据
     *
     * @return result
     */
    protected JsonResult renderSuccess() {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setStatus("200");
        result.setMsg(BusiStatus.SUCCESS.getReasonPhrase());
        return result;
    }

    /**
     * 渲染成功数据（带信息）
     *
     * @param msg 需要返回的信息
     * @return result
     */
    protected JsonResult renderSuccess(String msg) {
        JsonResult result = renderSuccess();
        result.setMsg(msg);
        return result;
    }

    /**
     * 渲染成功数据（带消息,数据）
     *
     * @param obj 需要返回的对象
     * @return result
     */
    protected JsonResult renderSuccess(Object obj) {
        JsonResult result = renderSuccess();
        result.setMsg(BusiStatus.SUCCESS.getReasonPhrase());
        result.setData(obj);
        return result;
    }



    /**
     * 用户ID校验
     * @param userId
     * @return
     */
    protected JsonResult checkUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            return renderServerError(BusiStatus.PARAM_ERROR.toString(), "用户ID为空");
        }
        AppUser appUser =appUserService.checkId(userId);
        if (appUser==null) {
            return renderServerError(BusiStatus.USER_NOT_EXISTS.toString(), BusiStatus.USER_NOT_EXISTS.getReasonPhrase());
        }

        if("1".equals(appUser.getStatus())){
            return renderServerError(BusiStatus.USER_NOT_LOGIN.toString(), BusiStatus.USER_NOT_LOGIN.getReasonPhrase());

        }
        return renderSuccess(appUser);
    }
}
