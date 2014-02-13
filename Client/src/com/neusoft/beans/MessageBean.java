/*
 * 用户本地数据库短信实体类
 * 存储包括：收件箱，发件箱，草稿箱。
 * 短信类型码说明:  0：收件、1：发件、2：草稿
 * 其中收件类型记录的 isRead 字段1代表已读，0代表未读。
 */

package com.neusoft.beans;

public class MessageBean {
    private String sender; // 发件人
    private String receiver; // 收件人
    private String message; // 短信内容
    private String time; // 短信时间
    private int isRead; // 是否已读
    private int type; // 短信类型

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
