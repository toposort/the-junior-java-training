/*
 * ��ֵ��ʵ����
 */
package com.neusoft.beans;

import java.text.DecimalFormat;

public class EcardBean {
    private String id; // ��ֵ����
    private String pwd; // ��ֵ������
    private int isUsed; // ��ֵ���Ƿ�ʹ��
    private String time; // ��ֵ������ʱ��
    private double money; // ��ֵ����ֵ

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
