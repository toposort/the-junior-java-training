/*
 * ͨѶ¼�����ݷ�����
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
     * �������ã���ѯָ���û���ͨѶ¼���м�¼ 
     * ����������û������������ݿ������û�����
     * ���ز������������ж�Ӧ�û�����ͨѶ¼��¼��ArrayList
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
     * �������ã���ѯָ���û���ͨѶ¼�ļ�¼�� 
     * ����������û������������ݿ������û����� 
     * ���ز�������������¼����
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
     * �������ã��޸�ָ���û���ͨѶ¼�ļ�¼ 
     * ����������û�����ԭ������ԭ���룬�������������� 
     * ���ز���������ֵ��trueΪ�޸ĳɹ���falseΪ�޸�ʧ��
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
     * �������ã����һ��ָ���û���ͨѶ¼�ļ�¼ 
     * ����������û��������������� 
     * ���ز���������ֵ��trueΪ��ӳɹ���falseΪ���ʧ��
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
