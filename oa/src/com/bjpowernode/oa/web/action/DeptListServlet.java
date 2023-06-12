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


public class DeptListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //获取根路径
        String contextPath = request.getContextPath();


        //设置响应内容类型以及字符集,防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //往网页输出内容
        out.printf("<!DOCTYPE html>");
        out.printf("<html lang='en'>");
        out.printf("<head>");
        out.printf("    <meta charset='UTF-8'>");
        out.printf("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.printf("    <title>全部信息列表</title>");


        out.print("     <script type = 'text/javascript' >");
        out.print("             function del(dno) {");
        out.print("         if (window.confirm('亲，删了不可恢复哦！')) {");
        out.print("             document.location.href ='" + contextPath + "/dept/delete?deptno=' + dno");
        out.print("         }");
        out.print("     }");
        out.print("     </script >");


        out.printf("</head>");
        out.printf("<body>");
        out.printf("    <table border='1px' align='center'> ");
        out.printf("        <tr>");
        out.printf("            <th>序号</th>");
        out.printf("            <th>部门编号</th>");
        out.printf("            <th>部门名称</th>");
        out.printf("            <th>操作</th>");
        out.printf("        </tr>");


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getDBConnection();
            //查询语句
            String sql = "select deptno,dname,loc from dept";
            //进行查询
            ps = conn.prepareStatement(sql);
            //响应集
            rs = ps.executeQuery();
            int i = 0;
            //Key找值,循环打印
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");


                out.printf("        <tr>");
                out.printf("            <td>" + (++i) + "</td>");
                out.printf("            <td>" + deptno + "</td>");
                out.printf("            <td>" + dname + "</td>");
                out.printf("            <td>");
                out.printf("                <a href='javascript:void(0)' onclick='del(" + deptno + ")'>删除</a>");
                out.printf("                <a href='./edit.html'>修改</a>");
                out.printf("                <a href='" + contextPath + "/dept/detail?deptno=" + deptno + "'>详情</a>");
                out.printf("            </td>");
                out.printf("        </tr>");
            }
            //抛异常,关流
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }
        out.printf("    </table>");
        out.printf("    <hr>");
        out.printf("    <a href='"+contextPath+"/add.html'> 新增部门</a>");
        out.printf("</body>");
        out.printf("");
        out.printf("</html>");
    }

}
