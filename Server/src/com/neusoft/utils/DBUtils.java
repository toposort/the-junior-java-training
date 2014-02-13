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
        username = "scott"; // ���ݿ��û���
        password = "123456"; // ���ݿ�����
        url = "jdbc:oracle:thin:@"; // ���ݿ�URL
        databaseName = "oracle"; // ���ݿ���
        serverName = "10.25.115.162"; // ���ݿ�IP��ַ
        portNumber = 1521; // ���ݿ�˿ں�
    }

    public String getConnectionURL() {
        return url + serverName + ":" + portNumber + ":" + databaseName;
    }

    /*
     * �������ã��õ�Connection����
     * �����������
     * ���ز�����Connection����
     */
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(getConnectionURL(), username,
                    password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ����Ժ����ԡ�");
        }
        return con;
    }

    /*
     * �������ã��ر�ResultSet
     * ���������ResultSet 
     * ���ز�������
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
     * �������ã��ر�Statement
     * ���������Statement
     * ���ز�������
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
     * �������ã��ر�Connection
     * ���������Connection
     * ���ز�������
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
     * �������ã��ر�ResultSet��Statement��Connection
     * ���������ResultSet��Statement��Connection
     * ���ز�������
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