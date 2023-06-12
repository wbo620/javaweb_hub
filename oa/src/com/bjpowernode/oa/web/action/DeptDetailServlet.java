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

public class DeptDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应内容类型以及字符集,防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //获取到传进来的部门编号,
        String no = request.getParameter("deptno");

        out.print("<!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门详情</title>");
        out.print("</head>");
        out.print("");
        out.print("<body>");
        out.print("");
        out.print("    <h1> 部门详情</h1>");
        out.print("    <hr>");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getDBConnection();
            //查询语句
            String sql = "select deptno,dname,loc from dept where deptno=?";

            //进行查询
            ps = conn.prepareStatement(sql);
            //给sql查询语句的第一个?赋值
            ps.setString(1,no);
            //响应集
            rs = ps.executeQuery();

            if(rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("   部门编号:"+no+" <br>");
                out.print("   部门名称:"+dname+"<br>");
                out.print("   部门位置:"+loc+"<br>");
            }

            //抛异常,关流
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }
        out.print("    <input type='button' value='后退'onclick='window.history.back()' />");
        out.print("");
        out.print("</body>");
        out.print("");
        out.print("</html>");
    }
}

