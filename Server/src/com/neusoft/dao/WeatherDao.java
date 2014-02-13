/*
 * ���������ݷ�����
 */
package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.neusoft.beans.WeatherBean;
import com.neusoft.utils.DBUtils;

public class WeatherDao {

    private PreparedStatement ps;
    private ResultSet rs;
    private DBUtils con = new DBUtils();
    private static WeatherDao man;

    public WeatherDao getInstance() {
        if (man == null) {
            man = new WeatherDao();
        }
        return man;
    }

    /*
     * �������ã����ݳ��в�ѯ���� 
     * ��������������� 
     * ���ز���������ʵ��
     */
    public WeatherBean selectWeather(String city) {
        Connection conn = con.getConnection();
        WeatherBean wb = null;
        String sql = "select * from weather where city = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, city);
            rs = ps.executeQuery();
            while (rs.next()) {
                wb = new WeatherBean();
                wb.setCity(city);
                wb.setWeath(rs.getString("weath"));
                wb.setLowt(rs.getInt("lowt"));
                wb.setHight(rs.getInt("hight"));
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
        return wb;
    }

}
