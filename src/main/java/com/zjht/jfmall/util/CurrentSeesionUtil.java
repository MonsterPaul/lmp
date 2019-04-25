package com.zjht.jfmall.util;

import com.zjht.jfmall.enums.NameSpace;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 当前session工具类
 */
public class CurrentSeesionUtil {



    /**
     * 保存验证码
     * @param phone
     * @param captcha
     */
    public static void setCaptcha(String phone, String captcha){
        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        httpSession.setAttribute(NameSpace.APP_USER_CAPTCHA.name() + phone, new Captcha(captcha));
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public static Captcha getCaptha(String phone){
        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return (Captcha) httpSession.getAttribute(NameSpace.APP_USER_CAPTCHA.name() + phone);
    }

    /**
     * 删除验证码
     * @param phone
     */
    public static void deleteCaptcha(String phone){
        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        httpSession.setAttribute(NameSpace.APP_USER_CAPTCHA.name() + phone, null);
    }
}
