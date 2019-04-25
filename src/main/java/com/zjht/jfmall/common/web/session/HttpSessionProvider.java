package com.zjht.jfmall.common.web.session;


import com.zjht.jfmall.common.web.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * HttpSession提供类
 * 
 * @author lijunjie
 * 
 */
public class HttpSessionProvider implements ISessionProvider {
	@Override
	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
    }

    @Override
	public void setAttribute(HttpServletRequest request,
                             HttpServletResponse response, String name, Serializable value){
        HttpSession httpsession = request.getSession();
        httpsession.setAttribute(name, value);
    }

	@Override
	public String getSessionId(HttpServletRequest request,
                               HttpServletResponse response) {
		return request.getSession().getId();
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			/**
			 * 退出只是将session中用户移除，不将整个session置为非法，caozk-20151120
			 */
			session.removeAttribute(Constants.MEMBER_SESSION_KEY);
//			session.invalidate();
		}
	}
}
