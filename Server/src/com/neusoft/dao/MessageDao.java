/*
 * ���ݿ���Ϣ�����ݷ�����
 */
package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.neusoft.beans.MessageBean;
import com.neusoft.utils.DBUtils;

public class MessageDao {

    private DBUtils con = new DBUtils();
    private static MessageDao md;
    private PreparedStatement ps;

    public static MessageDao getInstance() {
        if (md == null) {
            md = new MessageDao();
        }
        return md;
    }

    /*
     * �������ã���ѯδ������Ϣ��
     * ����������ֻ���
     * ���ز��������ֻ�������δ������Ϣ��
     */
    public int selectMsgNum(String usr) {
        int cnt = 0;
        String sql = "select count(*) from message where rec = " + usr
                + " and issend = 0";
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
     * �������ã��޸���Ϣ
     * ����������ֻ��ţ�����ʱ��
     * ���ز���������ֵ���޸ĳɹ�Ϊtrue��ʧ��Ϊfalse
     */
    public boolean updateMsg(String usr, String time) {
        String sql = "update message set issend = 1, status = 2 where rec = '"
                + usr + "' and time = '" + time + "'";
        Connection conn = null;
        Statement st = null;
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
     * �������ã��޸���Ϣ
     * ����������ֻ��ţ���Ϣ����
     * ���ز���������ֵ���޸ĳɹ�Ϊtrue��ʧ��Ϊfalse
     */
    public boolean updateMsg1(String usr, String msg) {
        String sql = "update message set issend = 1, status = 2 where rec = '"
                + usr + "' and msg = '" + msg + "'";
        Connection conn = null;
        Statement st = null;
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
     * �������ã�д����Ϣ
     * ��������������ˣ��ռ��ˣ���Ϣ���ݣ���Ϣ״̬
     * ���ز�������
     */
    public void insertMsg(String sender, String receiver, String message,
            int issend, int status) {
        String time = getCurrenttime();
        String sql = "insert into message values(?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, message);
            ps.setString(4, time);
            ps.setInt(5, issend);
            ps.setInt(6, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    /*
     * �������ã���ѯδ������Ϣ
     * ����������ֻ���
     * ���ز��������ֻ�������δ������Ϣ��ArrayList
     */
    public ArrayList<MessageBean> selectMsg(String usr) {
        String sql = "select * from message where rec = " + usr
                + " and issend = 0";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<MessageBean> al = new ArrayList<MessageBean>();
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                MessageBean mb = new MessageBean();
                mb.setMsg(rs.getString("msg"));
                mb.setRec(rs.getString("rec"));
                mb.setSed(rs.getString("sed"));
                mb.setIsSend(rs.getInt("issend"));
                mb.setStaus(rs.getInt("status"));
                mb.setTime(rs.getString("time"));
                al.add(mb);
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
     * �������ã���ѯָ��ʱ�����Ϣ
     * ����������ֻ��ţ���ʼʱ�䣬����ʱ��
     * ���ز�������ѯ�����ArrayList
     */
    public ArrayList<MessageBean> selectMsg1(String phoneno, String start,
            String end) {
        String sql = "select * from message where sed = '" + phoneno
                + "' and time < '" + end + "' and time > '" + start
                + "' order by time";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<MessageBean> al = new ArrayList<MessageBean>();
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                MessageBean mb = new MessageBean();
                mb.setMsg(rs.getString("msg"));
                mb.setRec(rs.getString("rec"));
                mb.setSed(rs.getString("sed"));
                mb.setIsSend(rs.getInt("issend"));
                mb.setStaus(rs.getInt("status"));
                mb.setTime(rs.getString("time"));
                al.add(mb);
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
     * �������ã�ִ���ض�SQL���
     * ���������SQL���
     * ���ز�������
     */
    public void doQuery(String sql) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = con.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
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
     * �������ã��õ���ǰʱ��
     * �����������
     * ���ز�������ǰʱ����ַ���
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
