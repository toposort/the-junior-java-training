/*
 * 天气的数据访问类
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
     * 方法作用：根据城市查询天气 
     * 传入参数：城市名 
     * 返回参数：天气实体
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
