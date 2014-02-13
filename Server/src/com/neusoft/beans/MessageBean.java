/*
 * 服务器端存储短信实体类
 */
package com.neusoft.beans;

public class MessageBean {
    private String sed; // 发件人
    private String rec; // 收件人
    private String msg; // 短信内容
    private String time; // 发送时间
    private int isSend; // 是否已发送
    private int staus; // 短信状态

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
