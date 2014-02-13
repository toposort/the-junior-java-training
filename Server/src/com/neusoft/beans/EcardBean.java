/*
 * 充值卡实体类
 */
package com.neusoft.beans;

import java.text.DecimalFormat;

public class EcardBean {
    private String id; // 充值卡号
    private String pwd; // 充值卡密码
    private int isUsed; // 充值卡是否被使用
    private String time; // 充值卡到期时间
    private double money; // 充值卡面值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        DecimalFormat roun = new DecimalFormat("###0.00");
        String str = roun.format(money);
        return Double.parseDouble(str);
    }

    public void setMoney(double money) {
        DecimalFormat roun = new DecimalFormat("###0.00");
        String str = roun.format(money);
        this.money = Double.parseDouble(str);
    }
}
