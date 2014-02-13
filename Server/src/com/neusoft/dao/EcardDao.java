/*
 * ��ֵ�������ݷ�����
 */
package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.neusoft.beans.EcardBean;
import com.neusoft.utils.DBUtils;

public class EcardDao {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBUtils con = new DBUtils();
    private static EcardDao man;

    public EcardDao getInstance() {
        if (man == null) {
            man = new EcardDao();
        }
        return man;
    }

    /*
     * �������ã����ݳ�ֵ���Ų��ҳ�ֵ��
     * �����������ֵ����
     * ���ز�������ֵ��ʵ��
     */
    public EcardBean findAccountByCode(String code) {
        Connection conn = con.getConnection();
        EcardBean account = null;
        String sql = "select * from ecard where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = new EcardBean();
                account.setId(rs.getString("id"));
                account.setPwd(rs.getString("pwd"));
                account.setIsUsed(rs.getInt("isused"));
                account.setMoney(rs.getDouble("money"));
                account.setTime(rs.getString("time"));
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
     * �������ã����³�ֵ����Ϣ
     * �����������ֵ��ʵ��
     * ���ز���������ֵ�����³ɹ�Ϊtrue��ʧ��Ϊfalse
     */
    public boolean updateAccount(EcardBean account) {
        Connection conn = con.getConnection();
        String sql = "update Ecard set isused=? where id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getIsUsed());
            ps.setString(2, account.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
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
        return true;
    }
}
