/*
 * �û��������ݿ����ʵ����
 * �洢�������ռ��䣬�����䣬�ݸ��䡣
 * ����������˵��:  0���ռ���1��������2���ݸ�
 * �����ռ����ͼ�¼�� isRead �ֶ�1�����Ѷ���0����δ����
 */

package com.neusoft.beans;

public class MessageBean {
    private String sender; // ������
    private String receiver; // �ռ���
    private String message; // ��������
    private String time; // ����ʱ��
    private int isRead; // �Ƿ��Ѷ�
    private int type; // ��������

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
