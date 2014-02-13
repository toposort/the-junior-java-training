/*
 *	本地存储短信的数据访问类 
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
     * 方法作用：查询指定用户的收件箱信息 
     * 传入参数：用户名 
     * 返回参数：包含所有对应用户收件箱信息的ArrayList
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
     * 方法作用：查询指定用户的收件箱信息数目 
     * 传入参数：用户名 
     * 返回参数：整数，信息数
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
     * 方法作用：查询指定用户的收件箱未读信息数目 
     * 传入参数：用户名 
     * 返回参数：整数，未读信息数
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
     * 方法作用：查询指定用户的发件箱信息
     *  传入参数：用户名 
     *  返回参数：包含所有对应用户发件箱信息的ArrayList
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
     * 方法作用：查询指定用户的收件箱信息数 
     * 传入参数：用户名
     * 返回参数：整数，发件箱信息数
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
     * 方法作用：查询指定用户的草稿箱信息 
     * 传入参数：用户名 
     * 返回参数：包含所有对应用户草稿箱信息的ArrayList
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
     * 方法作用：查询指定用户的草稿箱信息数目 
     * 传入参数：用户名 
     * 返回参数：整数，草稿箱信息数
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
     * 方法作用：写入新收件信息 
     * 传入参数：用户名，发件人，信息内容，收件时间 
     * 返回参数：无
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
     * 方法作用：写入新发件信息 
     * 传入参数：用户名，收件人，信息内容 
     * 返回参数：无
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
     * 方法作用：写入新草稿信息 
     * 传入参数：用户名，收件人，信息内容 
     * 返回参数：无
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
     * 方法作用：执行指定SQL语句 
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
     * 方法作用：获取当前时间 
     * 传入参数：无 
     * 返回参数：指定格式的时间字符串
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
