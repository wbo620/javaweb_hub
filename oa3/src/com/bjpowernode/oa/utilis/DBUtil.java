package com.bjpowernode.oa.utilis;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    //声明绑定器对象
    //获取配置文件中的配置信息
   private static ResourceBundle bundle=ResourceBundle.getBundle("resources.jdbc");
   //driver属性值指定了用于与数据库通信的JDBC驱动程序的类名。
    private static String driver=bundle.getString("driver");
    //数据库地址
    private static String url= bundle.getString("url");
    //用户名
    private static String user=bundle.getString("user");
    //密码
    private static String password=bundle.getString("password");

    static {
        try {
            /**
             * 通过反射加载指定的JDBC驱动程序类。driver变量存储了属性文件中指定的驱动程序类名
             * 通过调用Class.forName()方法，可以加载并注册该驱动程序，以便在后续代码中使用。
             * */
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 进行以数据库连接
     * 返回数据库连接对象 coon
     */
    public static Connection getDBConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }
    //关流
    public static void closeDB(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        }

}
