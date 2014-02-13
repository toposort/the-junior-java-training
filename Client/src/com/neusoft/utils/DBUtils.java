package com.neusoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBUtils {
    private String username; // 数据库用户名
    private String password; // 数据库密码
    private String url; // 数据库URL
    private String databaseName; // 数据库名
    private String serverName; // 数据库IP地址
    private int portNumber; // 数据库端口号
    private Connection con = null;

    /*
     * 方法作用：构造方法，配置数据库参数 
     * 传入参数：无 
     * 返回参数：无
     */
    public DBUtils() {
        username = "scott"; 
        password = "123456";
        url = "jdbc:oracle:thin:@";
        databaseName = "oracle";
        serverName = "10.25.115.162";
        portNumber = 1521;
    }

    /*
     * 方法作用：得到数据库地址 
     * 传入参数：无 
     * 返回参数：数据库地址字符串
     */
    public String getConnectionURL() {
        return url + serverName + ":" + portNumber + ":" + databaseName;
    }

    /*
     * 方法作用：得到数据库连接 
     * 传入参数：无 
     * 返回参数：数据库连接
     */
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(getConnectionURL(), username,
                    password);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库连接失败，请稍后重试。");
        }
        return con;
    }

    /*
     * 方法作用：关闭 ResultSet 
     * 传入参数：无 
     * 返回参数：无
     */
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 方法作用：关闭 Statement 
     * 传入参数：无 
     * 返回参数：无
     */
    public void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 方法作用：关闭Connection 
     * 传入参数：无 
     * 返回参数：无
     */
    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 方法作用：关闭所有资源 
     * 传入参数：无 
     * 返回参数：无
     */
    public void close(ResultSet rs, Statement st, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}