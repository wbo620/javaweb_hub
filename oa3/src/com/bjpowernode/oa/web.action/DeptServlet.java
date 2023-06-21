package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.utilis.DBUtil;
import com.bjpowernode.oa.bean.Dept;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/add", "/dept/edit", "/dept/delete", "/dept/marge", "/dept/list", "/dept/detail"})

public class DeptServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/dept/add".equals(servletPath)) {
            doAdd(request, response);
        } else if ("/dept/edit".equals(servletPath)) {
            doEdit(request, response);
        } else if ("/dept/delete".equals(servletPath)) {
            doDel(request, response);
        } else if ("/dept/marge".equals(servletPath)) {
            doMarge(request, response);
        } else if ("/dept/list".equals(servletPath)) {
            doList(request, response);
        } else if ("/dept/detail".equals(servletPath)) {
            doDetail(request, response);
        }
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
        } finally {
            DBUtil.closeDB(conn, ps, null);
        }

        if (count == 1) {
            //转发
            //request.getRequestDispatcher("/dept/list").forward(request, response);
            //重定向
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }

    }

    private void doMarge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
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
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应内容类型以及字符集,防止中文乱码
        response.setContentType("text/html;charset=UTF-8");

        List<Dept> depts = new ArrayList<Dept>();

        //获取到传进来的部门编号,
        String deptno = request.getParameter("deptno");

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
            ps.setString(1, deptno);
            //响应集
            rs = ps.executeQuery();

            if (rs.next()) {


                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                depts.add(dept);
            }
            //抛异常,关流
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }
        request.setAttribute("deptList", depts);
        request.getRequestDispatcher("/detail.jsp").forward(request, response);

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
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            //request.getRequestDispatcher("/error.html").forward(request, response);
            response.sendRedirect("/error.html");
        }
    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Dept> depts = new ArrayList<>();
        response.setCharacterEncoding("UTF-8");

        String deptno = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

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

                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                depts.add(dept);

            }
            //抛异常,关流
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }

        //将集合装入
        request.setAttribute("deptList", depts);
        ////用转发机制,保证在同一个servlet域中
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Dept> depts = new ArrayList<>();

        //设置响应内容类型以及字符集,防止中文乱码
        response.setContentType("text/html;charset=UTF-8");

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
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                depts.add(dept);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeDB(conn, ps, rs);
        }
        //将集合装入
        request.setAttribute("deptList", depts);
        //用转发机制,保证在同一个servlet域中
        request.getRequestDispatcher("/list.jsp").forward(request, response);

    }

}

