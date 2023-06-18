package com.bjpowernode.oa.web.action;


import com.bjpowernode.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptMargeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //获取到传进来的部门编号,
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");


        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getDBConnection();
            //查询语句
            String sql = "update dept set dname=?,loc=? where deptno=?";
            ps = conn.prepareStatement(sql);
            //给sql查询语句的第一个?赋值
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setString(3, deptno);

            count = ps.executeUpdate();
            //抛异常,关流
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, null);
        }


        if (count == 1) {
            //转发
            //request.getRequestDispatcher("/dept/list").forward(request, response);
            //重定向
            response.sendRedirect(request.getContextPath()+"/dept/list");
        } else {
           // request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }

    }
}
