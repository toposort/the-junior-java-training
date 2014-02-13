package com.neusoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBUtils {
    private String username;
    private String password;
    private String url;
    private String databaseName;
    private String serverName;
    private int portNumber;
    private Connection con = null;

    public DBUtils() {
        username = "scott"; // 数据库用户名
        password = "123456"; // 数据库密码
        url = "jdbc:oracle:thin:@"; // 数据库URL
        databaseName = "oracle"; // 数据库名
        serverName = "10.25.115.162"; // 数据库IP地址
        portNumber = 1521; // 数据库端口号
    }

    public String getConnectionURL() {
        return url + serverName + ":" + portNumber + ":" + databaseName;
    }

    /*
     * 方法作用：得到Connection连接
     * 传入参数：无
     * 返回参数：Connection连接
     */
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(getConnectionURL(), username,
                    password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库连接失败，请稍后重试。");
        }
        return con;
    }

    /*
     * 方法作用：关闭ResultSet
     * 传入参数：ResultSet 
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
     * 方法作用：关闭Statement
     * 传入参数：Statement
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
     * 传入参数：Connection
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
     * 方法作用：关闭ResultSet、Statement、Connection
     * 传入参数：ResultSet、Statement、Connection
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