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
     * 方法作用：构造方法
     * 传入参数：无
     * 返回参数：无
     */
    public TCPClient() {
        try {
            s = new Socket("10.25.115.162", 18888);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            isStart = false;
            JOptionPane.showMessageDialog(null, "服务器还没起床，请稍后再试。。");
        } catch (IOException e) {
            isStart = false;
            JOptionPane.showMessageDialog(null, "服务器还没起床，请稍后再试。。");
        }
    }

    /*
     * 方法作用：TCP客户端发送数据
     * 传入参数：要发送的信息
     * 返回参数：无
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
     * 方法作用：TCP客户端接收数据
     * 传入参数：无
     * 返回参数：接收到的信息
     */
    public String getMsg() {
        String msg = null;
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "与服务器断开连接，程序退出");
            System.exit(0);
        }
        return msg;

    }

    /*
     * 方法作用：关闭Socket
     * 传入参数：无
     * 返回参数：无
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