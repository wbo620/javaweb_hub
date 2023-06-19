package com.bjpowernode.oa.web.action;


import com.bjpowernode.oa.utils.DBUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/dept/add", "/dept/edit", "/dept/delete", "/dept/marge", "/dept/list", "/dept/detail"})
public class DeptServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        ServletContext servletContext = request.getServletContext();
        if ("/dept/add".equals(servletPath)){
            doAdd(request, response);
        }else if ("/dept/edit".equals(servletPath)){
            doEdit(request, response);
        }else if ("/dept/delete".equals(servletPath)){
            doDel(request,response);
        } else if ("/dept/marge".equals(servletPath)) {
            doMarge(request,response);
        }else if("/dept/list".equals(servletPath)){
            doList(request,response);
        }else if ("/dept/detail".equals(servletPath)){
            deDetail(request,response);
        }

    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取到传进来的部门编号,
        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getDBConnection();
            //取消自动提交,开启事物
            conn.setAutoCommit(false);

            //查询语句
            String sql = "delete from dept where deptno=?";
            //进行查询
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);

            //返回值:是影响了多少条数据
            count = ps.executeUpdate();

            //提交事物
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
        } finally {
            DBUtil.closeDB(conn, ps, null);
        }

        if (count == 1) {
            //转发
            //request.getRequestDispatcher("/dept/list").forward(request, response);
            //重定向
            response.sendRedirect(request.getContextPath()+"/dept/list");
        } else {
            //request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }
    }

    private void doMarge(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                out.printf("                <a href='" + contextPath + "/dept/edit?deptno=" + deptno + "'>修改</a>");
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
        out.printf("    <a href='" + contextPath + "/add.html'> 新增部门</a>");
        out.printf("</body>");
        out.printf("");
        out.printf("</html>");
    }

    private void deDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
