package com.zjht.jfmall.common.web.interceptor;


import com.zjht.jfmall.common.web.Constants;
import com.zjht.jfmall.common.web.annotation.Secured;
import com.zjht.jfmall.common.web.threadvariable.MemberThread;
import com.zjht.jfmall.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前台安全拦截器
 *
 * @author
 */
public class WebappSecureInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(Constants.REQUEST_LOGIN_URL_KEY, loginUrl);

        //如果类上有注解Secured说明整个类的请求都需要校验，如果类上没有注解再判断方法上有没有
        Secured secured = handler.getClass().getAnnotation(Secured.class);
        if (secured == null) {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                secured = handlerMethod.getMethod().getAnnotation(Secured.class);
            }
        }
        if (secured != null) {
            if (MemberThread.get() == null) {
                redirectToLogin(request, response);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = MemberThread.get();
        if (user != null && modelAndView != null) {
            modelAndView.addObject(Constants.MEMBER, user);
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(loginUrl);
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUrl);
    }

    private String loginUrl;

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}
