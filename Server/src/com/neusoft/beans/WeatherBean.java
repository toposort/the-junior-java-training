/*
 * 天气实体类
 */
package com.neusoft.beans;

public class WeatherBean {
    private String city; // 城市
    private String weath; // 天气
    private int lowt; // 最低温度
    private int hight; // 最高温度

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeath() {
        return weath;
    }

    public void setWeath(String weath) {
        this.weath = weath;
    }

    public int getLowt() {
        return lowt;
    }

    public void setLowt(int lowt) {
        this.lowt = lowt;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

}
