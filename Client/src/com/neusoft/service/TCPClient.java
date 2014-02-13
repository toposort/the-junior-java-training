package com.neusoft.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class TCPClient {
    private Socket s = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    public boolean isStart = true;

    /*
     * �������ã����췽��
     * �����������
     * ���ز�������
     */
    public TCPClient() {
        try {
            s = new Socket("10.25.115.162", 18888);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showMessageDialog(null, "��������û�𴲣����Ժ����ԡ���");
        } catch (IOException e) {
            isStart = false;
            JOptionPane.showMessageDialog(null, "��������û�𴲣����Ժ����ԡ���");
        }
    }

    /*
     * �������ã�TCP�ͻ��˷�������
     * ���������Ҫ���͵���Ϣ
     * ���ز�������
     */
    public void sendMsg(String str) {
        try {
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * �������ã�TCP�ͻ��˽�������
     * �����������
     * ���ز��������յ�����Ϣ
     */
    public String getMsg() {
        String msg = null;
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "��������Ͽ����ӣ������˳�");
            System.exit(0);
        }
        return msg;

    }

    /*
     * �������ã��ر�Socket
     * �����������
     * ���ز�������
     */
    public void close() {
        try {
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}