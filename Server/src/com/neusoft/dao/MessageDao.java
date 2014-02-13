/*
 * 数据库信息的数据访问类
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
     * 方法作用：查询未发送信息数
     * 传入参数：手机号
     * 返回参数：该手机号所有未接收信息数
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
     * 方法作用：修改信息
     * 传入参数：手机号，发送时间
     * 返回参数：布尔值，修改成功为true，失败为false
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
     * 方法作用：修改信息
     * 传入参数：手机号，信息内容
     * 返回参数：布尔值，修改成功为true，失败为false
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
     * 方法作用：写入信息
     * 传入参数：发件人，收件人，信息内容，信息状态
     * 返回参数：无
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
     * 方法作用：查询未发送信息
     * 传入参数：手机号
     * 返回参数：该手机号所有未发送信息的ArrayList
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
     * 方法作用：查询指定时间段信息
     * 传入参数：手机号，开始时间，结束时间
     * 返回参数：查询结果的ArrayList
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
     * 方法作用：执行特定SQL语句
     * 传入参数：SQL语句
     * 返回参数：无
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
     * 方法作用：得到当前时间
     * 传入参数：无
     * 返回参数：当前时间的字符串
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
