/**
 * <p>实现restapi权限控制过滤器<p>
 *
 * create  2016-5-15<br>
 * @author  cedar<br>
 * @version $Revision$ $Date$
 * @since   1.0
 */

package com.zjht.jfmall.exception;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 过滤器
 */
public class AccessControlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);


 
    @Override
	public void init(FilterConfig filterConfig) throws ServletException {
     }

    public void destroy() {

    }

    // 按权限实现Api过滤功能
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // CORS: Cross-Origin Resource Sharing
        // 指定允许其他域名访问
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept,Authorization");
//      response.setHeader("Access-Control-Allow-Credentials","true");
        // response.addHeader("Access-Control-Max-Age", "1728000");
        chain.doFilter(req, resp);
    }


}
