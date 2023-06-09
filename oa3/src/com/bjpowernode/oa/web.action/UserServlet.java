package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.User;
import com.bjpowernode.oa.utilis.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login", "/user/exit"})

public class UserServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/user/login".equals(servletPath)) {
            doLogin(request, response);
        } else if ("/user/exit".equals(servletPath)) {
            doExit(request, response);
        }

    }

    private void doExit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获取session对象,销毁session
        HttpSession session = request.getSession(false);

        if (session != null) {

            //从session中删除user对象
            session.removeAttribute("user");

            //手动销毁session对象
            session.invalidate();

            //销毁cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if ("username".equals(name)||"password".equals(name)) {
                        //如果找到对应的cookie就销毁掉
                        cookie.setMaxAge(0);
                        //设置cookie的路径
                        //删除cookie的时候要注意路径问题
                        cookie.setPath(request.getContextPath());
                        //响应cookie给浏览器,将之前的cookie给覆盖掉
                        response.addCookie(cookie);
                    }
                }
            }
            //跳转到登录页面
            response.sendRedirect(request.getContextPath());
        }
    }


    protected void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取输入的用户名跟密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean success = false;
        try {
            //连接数据库
            conn = DBUtil.getDBConnection();
            //查询语句
            String sql = "select * from t_user where username=? and password=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            //结果集
            rs = ps.executeQuery();
            //如果有结果,给变量变为true
            if (rs.next()) {
                success = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }


        //判断,登陆成功跳转到列表界面
        if (success) {
            //登陆成功,创建session对象,并把当前用户名存入session中
            HttpSession session = request.getSession();
            //绑定用户名到session中
            //session.setAttribute("username", username);

            User user = new User();
            session.setAttribute("user", user);

            //登陆成功,并勾选了十天内免登录
            String f = request.getParameter("f");
            if ("1".equals(f)) {
                //创建cookie,保存选择十天内免登录的用户名和密码
                Cookie cookie1 = new Cookie("username", username);
                Cookie cookie2 = new Cookie("password", password);

                //设置cookie的超时时间,设为10天
                cookie1.setMaxAge(60 * 60 * 24 * 10);
                cookie2.setMaxAge(60 * 60 * 24 * 10);
                //设置cookie的Path (只要访问这个路径,就携带这两个cookie)
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                //响应cookie给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }

            //跳转到列表界面
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            //失败,跳转到失败提示界面
            response.sendRedirect(request.getContextPath() + "/errorLogin.jsp");
        }

    }
}
