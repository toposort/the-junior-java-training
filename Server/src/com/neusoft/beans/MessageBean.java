/*
 * �������˴洢����ʵ����
 */
package com.neusoft.beans;

public class MessageBean {
    private String sed; // ������
    private String rec; // �ռ���
    private String msg; // ��������
    private String time; // ����ʱ��
    private int isSend; // �Ƿ��ѷ���
    private int staus; // ����״̬

    public String getSed() {
        return sed;
    }

    public void setSed(String sed) {
        this.sed = sed;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

}
