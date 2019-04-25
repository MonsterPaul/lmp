package com.zjht.jfmall.common.web.interceptor;

import com.zjht.jfmall.common.web.Constants;
import com.zjht.jfmall.common.web.session.HttpSessionProvider;
import com.zjht.jfmall.common.web.threadvariable.MemberThread;
import com.zjht.jfmall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商城前台拦截器
 * 处理Config、和登录信息
 */
public class WebAppContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSessionProvider session;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws ServletException {
        User user = (User) session.getAttribute(request, Constants.MEMBER_SESSION_KEY);
        // 用户存在
        if (user != null) {
            // 将用户放入ThreadLocal
            MemberThread.set(user);
            // 用户认证放入request
            request.setAttribute(Constants.AUTH_KEY, user.getUsername() + user.getId());
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {
        //获取用户信息
        User user = MemberThread.get();
        //请求转发
        if (user != null && mav != null && mav.getModelMap() != null
                && mav.getViewName() != null
                && !mav.getViewName().startsWith("redirect:")) {
            mav.getModelMap().addAttribute(Constants.AUTH_KEY,
                    user.getUsername() + user.getId());
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MemberThread.remove();
    }

}
