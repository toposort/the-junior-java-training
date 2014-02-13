/*
 *	���ش洢���ŵ����ݷ����� 
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
    private static MessageDao md = null;
    private PreparedStatement ps = null;

    public static MessageDao getInstance() {
        if (md == null) {
            md = new MessageDao();
        }
        return md;
    }

    /*
     * �������ã���ѯָ���û����ռ�����Ϣ 
     * ����������û��� 
     * ���ز������������ж�Ӧ�û��ռ�����Ϣ��ArrayList
     */
    public ArrayList<MessageBean> selectMsg(String usr) {
        String sql = "select * from \"" + usr + "\""
                + " where type=0 order by isread asc, time desc";
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
                mb.setMessage(rs.getString("message"));
                mb.setReceiver(rs.getString("receiver"));
                mb.setSender(rs.getString("sender"));
                mb.setIsRead(rs.getInt("isread"));
                mb.setType(rs.getInt("type"));
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
     * �������ã���ѯָ���û����ռ�����Ϣ��Ŀ 
     * ����������û��� 
     * ���ز�������������Ϣ��
     */
    public int selectMsgNum(String usr) {
        int cnt = 0;
        String sql = "select count(*) from \"" + usr + "\"" + " where type=0";
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
     * �������ã���ѯָ���û����ռ���δ����Ϣ��Ŀ 
     * ����������û��� 
     * ���ز�����������δ����Ϣ��
     */
    public int selectNum(String usr) {
        int cnt = 0;
        String sql = "select count(*) from \"" + usr + "\""
                + " where type = 0 and isread = 0";
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
     * �������ã���ѯָ���û��ķ�������Ϣ
     *  ����������û��� 
     *  ���ز������������ж�Ӧ�û���������Ϣ��ArrayList
     */
    public ArrayList<MessageBean> selectMsgSend(String usr) {
        String sql = "select * from \"" + usr + "\""
                + " where type=1 order by time desc";
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
                mb.setMessage(rs.getString("message"));
                mb.setReceiver(rs.getString("receiver"));
                mb.setSender(rs.getString("sender"));
                mb.setIsRead(rs.getInt("isread"));
                mb.setType(rs.getInt("type"));
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
     * �������ã���ѯָ���û����ռ�����Ϣ�� 
     * ����������û���
     * ���ز�������������������Ϣ��
     */
    public int selectMsgNumSend(String usr) {
        int cnt = 0;
        String sql = "select count(*) from \"" + usr + "\"" + " where type=1";
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
     * �������ã���ѯָ���û��Ĳݸ�����Ϣ 
     * ����������û��� 
     * ���ز������������ж�Ӧ�û��ݸ�����Ϣ��ArrayList
     */
    public ArrayList<MessageBean> selectMsgTmp(String usr) {
        String sql = "select * from \"" + usr + "\""
                + " where type = 2 order by time desc";
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
                mb.setMessage(rs.getString("message"));
                mb.setReceiver(rs.getString("receiver"));
                mb.setSender(rs.getString("sender"));
                mb.setIsRead(rs.getInt("isread"));
                mb.setType(rs.getInt("type"));
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
     * �������ã���ѯָ���û��Ĳݸ�����Ϣ��Ŀ 
     * ����������û��� 
     * ���ز������������ݸ�����Ϣ��
     */
    public int selectMsgNumTmp(String usr) {
        int cnt = 0;
        String sql = "select count(*) from \"" + usr + "\"" + " where type=2";
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
     * �������ã�д�����ռ���Ϣ 
     * ����������û����������ˣ���Ϣ���ݣ��ռ�ʱ�� 
     * ���ز�������
     */
    public void insertMsg(String usr, String sender, String msg, String time) {
        if (time.equals("")) {
            time = getCurrenttime();
        }
        String sql = "insert into \"" + usr + "\" " + "values(?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usr);
            ps.setString(2, sender);
            ps.setString(3, msg);
            ps.setInt(4, 0);
            ps.setString(5, time);
            ps.setInt(6, 0);
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
     * �������ã�д���·�����Ϣ 
     * ����������û������ռ��ˣ���Ϣ���� 
     * ���ز�������
     */
    public void insertMsgSend(String usr, String sender, String msg) {
        String time = getCurrenttime();
        String sql = "insert into \"" + usr + "\" " + "values(?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sender);
            ps.setString(2, usr);
            ps.setString(3, msg);
            ps.setInt(4, 1);
            ps.setString(5, time);
            ps.setInt(6, 0);
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
     * �������ã�д���²ݸ���Ϣ 
     * ����������û������ռ��ˣ���Ϣ���� 
     * ���ز�������
     */
    public void insertMsgTmp(String usr, String sender, String msg) {
        String time = getCurrenttime();
        String sql = "insert into \"" + usr + "\" " + "values(?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sender);
            ps.setString(2, usr);
            ps.setString(3, msg);
            ps.setInt(4, 2);
            ps.setString(5, time);
            ps.setInt(6, 0);
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
     * �������ã�ִ��ָ��SQL��� 
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
     * �������ã���ȡ��ǰʱ�� 
     * ����������� 
     * ���ز�����ָ����ʽ��ʱ���ַ���
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
