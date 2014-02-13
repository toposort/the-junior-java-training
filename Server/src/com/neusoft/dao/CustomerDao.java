/*
 * 客户信息的数据访问类
 */
package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.neusoft.beans.CustomerBean;
import com.neusoft.utils.DBUtils;

public class CustomerDao {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBUtils con = new DBUtils();
    private static CustomerDao man;

    public static CustomerDao getInstance() {
        if (man == null) {
            man = new CustomerDao();
        }
        return man;
    }

    /*
     * 方法作用：根据手机号查找客户信息 
     * 传入参数：手机号
     *  返回参数：客户实体
     */
    public CustomerBean findAccountByCode(String code) {
        Connection conn = con.getConnection();
        CustomerBean account = null;
        String sql = "select * from customer where phoneno=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = new CustomerBean();
                account.setPhoneno(rs.getString("phoneno"));
                account.setName(rs.getString("name"));
                account.setPassword(rs.getString("password"));
                account.setMoney(rs.getDouble("money"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    /*
     * 方法作用：根据客户姓名查找客户 
     * 传入参数：客户姓名 
     * 返回参数：客户实体
     */
    public CustomerBean findAccountByName(String name) {
        Connection conn = con.getConnection();
        CustomerBean account = null;
        String sql = "select * from customer where name=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = new CustomerBean();
                account.setPhoneno(rs.getString("phoneno"));
                account.setName(rs.getString("name"));
                account.setPassword(rs.getString("password"));
                account.setMoney(rs.getDouble("money"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    /*
     * 方法作用：更新客户信息 
     * 传入参数：客户实体 
     * 返回参数：布尔值，成功为true，失败为false
     */
    public boolean updateAccount(CustomerBean account) {
        Connection conn = con.getConnection();
        String sql = "update customer set password=?,money=?,name=? where phoneno=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getPassword());
            ps.setDouble(2, account.getMoney());
            ps.setString(3, account.getName());
            ps.setString(4, account.getPhoneno());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /*
     * 方法作用：查询所有用户 
     * 传入参数：无 
     * 返回参数：包含所有客户实体的ArrayList
     */
    public ArrayList<CustomerBean> selectCustom() {
        String sql = "select * from customer";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<CustomerBean> al = new ArrayList<CustomerBean>();
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                CustomerBean cb = new CustomerBean();
                cb.setName(rs.getString("name"));
                cb.setMoney(rs.getDouble("money"));
                cb.setPassword(rs.getString("password"));
                cb.setPhoneno(rs.getString("phoneno"));
                al.add(cb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return al;
    }
}
