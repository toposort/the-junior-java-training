/*
 * ����ʵ����
 */
package com.neusoft.beans;

public class WeatherBean {
    private String city; // ����
    private String weath; // ����
    private int lowt; // ����¶�
    private int hight; // ����¶�

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
