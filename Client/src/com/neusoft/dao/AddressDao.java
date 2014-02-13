/*
 * 通讯录的数据访问类
 */

package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.neusoft.beans.AddressBean;
import com.neusoft.utils.DBUtils;

public class AddressDao {
    private DBUtils con = new DBUtils();
    private static AddressDao ad;
    private PreparedStatement ps;

    public static AddressDao getInstance() {
        if (ad == null) {
            ad = new AddressDao();
        }
        return ad;
    }

    /*
     * 方法作用：查询指定用户的通讯录所有记录 
     * 传入参数：用户名，查找数据库名：用户名。
     * 返回参数：包含所有对应用户名的通讯录记录的ArrayList
     */
    public ArrayList<AddressBean> selectAd(String usr) {
        String sql = "select * from \"" + usr + "a\" order by name";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<AddressBean> al = new ArrayList<AddressBean>();
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                AddressBean ab = new AddressBean();
                ab.setName(rs.getString("name"));
                ab.setPhoneno(rs.getString("phoneno"));
                al.add(ab);
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

    /*
     * 方法作用：查询指定用户的通讯录的记录数 
     * 传入参数：用户名，查找数据库名：用户名。 
     * 返回参数：整数，记录数。
     */
    public int selectAdNum(String usr) {
        int cnt = 0;
        String sql = "select count(*) from \"" + usr + "a\"";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cnt = rs.getInt(1);
            }
            return cnt;
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
        return cnt;
    }

    /*
     * 方法作用：修改指定用户的通讯录的记录 
     * 传入参数：用户名，原姓名，原密码，新姓名，新密码 
     * 返回参数：布尔值，true为修改成功，false为修改失败
     */
    public boolean updateAd(String usr, String nam, String pho, String name,
            String phoneno) {
        String sql = "update \"" + usr + "a\" set name = '" + name
                + "', phoneno = '" + phoneno + "' where name = '" + nam
                + "' and phoneno = '" + pho + "'";
        Connection conn = null;
        Statement st = null;
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
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
    }

    /*
     * 方法作用：添加一条指定用户的通讯录的记录 
     * 传入参数：用户名，姓名，密码 
     * 返回参数：布尔值，true为添加成功，false为添加失败
     */
    public boolean insertAd(String usr, String name, String phoneno) {
        String sql = "insert into \"" + usr + "a\" " + "values(?,?)";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, phoneno);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
