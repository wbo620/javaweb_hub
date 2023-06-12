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

public class DeptDelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            request.getRequestDispatcher("/dept/list").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.html").forward(request, response);
        }
    }
}