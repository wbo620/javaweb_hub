package com.bjpowernode.oa.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author hallen
 */

public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resq, FilterChain filterChain)
            throws IOException, ServletException {

        /* *
         * 什么情况下不能拦截？
         *目前写的路径是：/*表示所有的请求均栏截
         *用户访问index.jsp的时候不能拦截
         *用户已经登录了，这个需要放行，不能拦截。
         *用户要去登录，这个也不能拦截。
         *WelcomeServlet也不能拦截。
         * */

        //向下强转型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resq;
        //获取请求路径
        String servletPath = request.getServletPath();
        //只获取当前session对象,没有返回null
        HttpSession session = request.getSession(false);

        //如果session为空并且在session中的数据不为空,则表明是同一个会话
        if (
                "/index.jsp".equals(servletPath) || "/welcome".equals(servletPath) ||
                        "/user/login".equals(servletPath) || "/user/exit".equals(servletPath) ||
                        "/errorLogin.jsp".equals(servletPath) || "/error.jsp".equals(servletPath) ||
                        ((session != null) && (session.getAttribute("username") != null))) {
            filterChain.doFilter(request, response);

        } else {
            //未创建会话,就需要跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }


    }
}
