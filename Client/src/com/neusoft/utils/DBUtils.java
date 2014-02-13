package com.neusoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBUtils {
    private String username; // ���ݿ��û���
    private String password; // ���ݿ�����
    private String url; // ���ݿ�URL
    private String databaseName; // ���ݿ���
    private String serverName; // ���ݿ�IP��ַ
    private int portNumber; // ���ݿ�˿ں�
    private Connection con = null;

    /*
     * �������ã����췽�����������ݿ���� 
     * ����������� 
     * ���ز�������
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
     * �������ã��õ����ݿ��ַ 
     * ����������� 
     * ���ز��������ݿ��ַ�ַ���
     */
    public String getConnectionURL() {
        return url + serverName + ":" + portNumber + ":" + databaseName;
    }

    /*
     * �������ã��õ����ݿ����� 
     * ����������� 
     * ���ز��������ݿ�����
     */
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(getConnectionURL(), username,
                    password);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ����Ժ����ԡ�");
        }
        return con;
    }

    /*
     * �������ã��ر� ResultSet 
     * ����������� 
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
     * �������ã��ر� Statement 
     * ����������� 
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
     * ����������� 
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
     * �������ã��ر�������Դ 
     * ����������� 
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