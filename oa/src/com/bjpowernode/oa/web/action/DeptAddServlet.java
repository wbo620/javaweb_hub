package com.bjpowernode.oa.web.action;


import com.bjpowernode.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptAddServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应内容类型以及字符集,防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;


        try {
            conn = DBUtil.getDBConnection();
            conn.setAutoCommit(false);

            String sql = "insert into dept values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            ps.setString(2, dname);
            ps.setString(3, loc);

            count = ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }finally {
            DBUtil.closeDB(conn,ps,null);
        }

        if (count == 1) {
            request.getRequestDispatcher("/dept/list").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.html").forward(request, response);
        }

    }


}
