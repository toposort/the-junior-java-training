/*
 * 用户信息实体类
 */
package com.neusoft.beans;

import java.text.DecimalFormat;

public class CustomerBean {
    private String phoneno; // 手机号
    private String password; // 密码
    private String name; // 姓名
    private double money; // 余额

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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
