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

public class DeptEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String contextPath = request.getContextPath();

        String deptno = request.getParameter("deptno");


        out.print("        <!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.print("    <title>修改部门</title>");
        out.print("</head>");
        out.print("");
        out.print("<body>");
        out.print("    <h1>修改部门</h1>");
        out.print("    <hr>");
        out.print("    <form action='"+contextPath+"/dept/marge' method='post'>");


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=0;

        try {
            conn = DBUtil.getDBConnection();
            //查询语句
            String sql = "select dname,loc from dept where deptno=?";

            //进行查询
            ps = conn.prepareStatement(sql);
            //给sql查询语句的第一个?赋值
            ps.setString(1, deptno);
            //响应集
            rs = ps.executeQuery();

            if (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print(" 部门编号<input type='text' name='deptno' value='" + deptno + "' readonly><br>");
                out.print(" 部门名称<input type='text' name='dname' value='" + dname + "'><br>");
                out.print(" 部门位置<input type='text' name='loc' value='" + loc + "'><br>");
            }

            //抛异常,关流
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }
        out.print("    <input type='submit' value='修改'>");
        out.print("    </form>");
        out.print("</body>");
        out.print("</html>");


    }
}
