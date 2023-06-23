package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.utilis.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取cookie
        Cookie[] cookies = request.getCookies();
        boolean success = false;
        String username = null;
        String password = null;
        //判断cookie是否为空,
        //不为空,对数组进行遍历,查找用户名和密码,并取出
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("username".equals(name)) {
                    username = cookie.getValue();
                } else if ("password".equals(name)) {
                    password = cookie.getValue();
                }
            }
            //如果用户名和密码都不为空,则进行跟数据库连接判断用户名和密码是否正确
            if (username != null && password != null) {

                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    //连接数据库,验证用户名密码是否正确
                    conn = DBUtil.getDBConnection();

                    String sql = "select username,password from t_user where username=? and password=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, username);
                    ps.setString(2, password);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        //登陆成功
                        success = true;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    DBUtil.closeDB(conn, ps, rs);
                }

            }
            if (success) {
                //获取session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                //成功,跳转到列表页面
                response.sendRedirect(request.getContextPath() + "/dept/list");
            } else {
                //失败,跳转到登录界面
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

    }
}
