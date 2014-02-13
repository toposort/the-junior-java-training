package com.neusoft.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.neusoft.beans.CustomerBean;
import com.neusoft.beans.MessageBean;
import com.neusoft.beans.WeatherBean;
import com.neusoft.dao.CustomerDao;
import com.neusoft.dao.MessageDao;
import com.neusoft.dao.WeatherDao;
import com.neusoft.service.ServerUI;

public class TCPServerUtils implements Runnable {
    private Socket s;
    private ServerUI su = null;
    private String name = null;
    // ////
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    // ////
    private ArrayList<TCPServerUtils> al = null;
    private CustomerUtils cu = CustomerUtils.getInstance();
    private MessageDao md = MessageDao.getInstance();

    /*
     * �������ã����췽��
     * ���������Socket��ServerUI��ArrayList<TCPServerUtils>
     * ���ز�������
     */
    public TCPServerUtils(Socket s, ServerUI su, ArrayList<TCPServerUtils> al) {
        this.s = s;
        this.su = su;
        this.al = al;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            while (true) {
                String str = dis.readUTF();
                int cmdType = cmdCheck(str);
                if (cmdType == 1) {
                    String usr = str.substring(6, 17);
                    String pwd = str.substring(17, str.length());
                    int tmp = cu.accountLogin(usr, pwd);
                    if (tmp == 0) {
                        dos.writeUTF(String.valueOf(tmp));
                        dos.flush();
                    } else if (tmp == 1) {
                        if (threadCheck(usr) != null) {
                            tmp = 4;
                            dos.writeUTF(String.valueOf(tmp));
                            dos.flush();
                        } else {
                            Thread.currentThread().setName(usr);
                            int num = md.selectMsgNum(usr);
                            setName(usr);
                            dos.writeUTF(String.valueOf(tmp) + num);
                            dos.flush();
                            su.appedText(getCurrenttime() + " " + usr
                                    + " ��¼�ɹ���\r\n\r");
                            al.add(this);
                            ArrayList<MessageBean> al = md.selectMsg(usr);
                            for (int i = 0; i < al.size(); i++) {
                                String sed = al.get(i).getSed();
                                String time = al.get(i).getTime();
                                String msg = al.get(i).getMsg();
                                String strr = sed + time + msg;
                                dos.writeUTF(strr);
                                dos.flush();
                                md.updateMsg(usr, time);
                            }
                        }
                    } else if (tmp == 2) {
                        dos.writeUTF(String.valueOf(tmp));
                        dos.flush();
                    }

                } else if (cmdType == 2) {
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getName().equals(name)) {
                            al.remove(i);
                            break;
                        }
                    }
                    dos.writeUTF("CMD0021");
                    dos.flush();
                    su.appedText(getCurrenttime() + " " + name + " ���߳ɹ���\r\n\r");
                    return;
                } else if (cmdType == 3) {
                    String src = str.substring(6, 17);
                    String dst = str.substring(17, 28);
                    String msg = str.substring(28, str.length());
                    int rett = cu.sendMessage(src, dst);
                    if (rett == 0) {
                        md.insertMsg(src, dst, msg, 1, 0);
                        dos.writeUTF("CMD0050");
                        dos.flush();
                    } else if (rett == 1) {
                        md.insertMsg(src, dst, msg, 1, 1);
                        dos.writeUTF("CMD0051");
                        dos.flush();
                    } else if (rett == 2) {
                        Thread temp = threadCheck(dst);
                        if (temp != null) {// Ŀ�ĵ�ַ����
                            md.insertMsg(src, dst, msg, 1, 2);
                            for (int i = 0; i < al.size(); i++) {
                                if (al.get(i).getName().equals(dst)) {
                                    TCPServerUtils tmp = al.get(i);
                                    tmp.sendMessage("CMD003" + src + msg);
                                    break;
                                }
                            }
                        } else {
                            md.insertMsg(src, dst, msg, 0, -1);// �������ݿ�
                        }
                        dos.writeUTF("CMD0052");
                        dos.flush();
                    } else if (rett == 3) {
                        dos.writeUTF("CMD0052");
                        dos.flush();
                        if (msg.equalsIgnoreCase("YE")) {
                            CustomerDao cd = CustomerDao.getInstance();
                            CustomerBean acc = cd.findAccountByCode(src);
                            double money = acc.getMoney();
                            if (money >= 20) {
                                dos.writeUTF("CMD003" + dst + "�𾴵Ŀͻ������ã���ֹ��"
                                        + getTime() + "�����Ļ������Ϊ" + money + "Ԫ��");
                                dos.flush();
                            } else {
                                dos.writeUTF("CMD003" + dst + "�𾴵Ŀͻ������ã���ֹ��"
                                        + getTime() + "�����Ļ������Ϊ" + money + "Ԫ��"
                                        + "������ʱ���ѣ���������ʱ��ͣ����Ӱ����������ʹ�á�");
                                dos.flush();
                            }
                        } else {
                            dos.writeUTF("CMD003" + dst + "�𾴵Ŀͻ������ã����ṩ��ȷ��ָ�"
                                    + "��ѯ����뷢�͡�YE����");
                            dos.flush();
                        }
                    } else if (rett == 4) {
                        dos.writeUTF("CMD0052");
                        dos.flush();
                        WeatherDao wd = new WeatherDao().getInstance();
                        WeatherBean wb = wd.selectWeather(msg.trim());
                        if (wb == null) {
                            dos.writeUTF("CMD003" + dst
                                    + "��ѯʧ�ܣ��뷢����ȷ�Ĳ�ѯ���У����ѯ�Ͳ��������뷢�͡��Ͳ�����");
                            dos.flush();
                        } else {
                            dos.writeUTF("CMD003" + dst + getTime() + "��������"
                                    + wb.getWeath() + "������¶ȣ�" + wb.getLowt()
                                    + "�棬����¶ȣ�" + wb.getHight() + "�档");
                            dos.flush();
                        }
                    } else if (rett == 5) {
                        dos.writeUTF("CMD0052");
                        dos.flush();
                        int res = cu.addMoney(src, msg);
                        if (res == 0) {
                            dos.writeUTF("CMD003" + dst + "��ֵʧ�ܣ���ֵ�����Ż��������");
                            dos.flush();
                        } else if (res == 1) {
                            dos.writeUTF("CMD003" + dst + "��ֵʧ�ܣ���ֵ���ѱ�ʹ�á�");
                            dos.flush();
                        } else if (res == 2) {
                            dos.writeUTF("CMD003" + dst + "��ֵʧ�ܣ���ֵ���ѳ�����Ч�ڡ�");
                            dos.flush();
                        } else if (res == 3) {
                            CustomerDao cd = CustomerDao.getInstance();
                            CustomerBean acc = cd.findAccountByCode(src);
                            double money = acc.getMoney();
                            dos.writeUTF("CMD003" + dst + "��ֵ�ɹ��������˻����Ϊ "
                                    + money + "Ԫ��");
                            dos.flush();
                        }
                    }
                } else if (cmdType == 4) {
                    int loc = str.indexOf("&");
                    String usr = str.substring(6, 17);
                    String pwd1 = str.substring(17, loc);
                    String pwd2 = str.substring(loc + 1);
                    System.out.println(usr + " " + pwd1 + " " + pwd2);
                    boolean flag = cu.changePassword(usr, pwd1, pwd2);
                    System.out.println(flag);
                    if (flag == true) {
                        dos.writeUTF("CMD0041");
                        dos.flush();
                    } else {
                        dos.writeUTF("CMD0040");
                        dos.flush();
                    }
                }
            }
        } catch (IOException e) {
             e.printStackTrace();
            if (name != null) {
                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).getName().equals(name)) {
                        al.remove(i);
                        break;
                    }
                }
                su.appedText(getCurrenttime() + " " + name + " �쳣�˳���\r\n\r");
            }
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /*
     * �������ã��ͷ���Դ
     * �����������
     * ���ز�������
     */
    public void closeAl() {
        try {
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
        }
    }

    /*
     * �������ã����ͻ��˷���Ϣ
     * �����������Ϣ����
     * ���ز�������
     */
    public void sendMessage(String str) {
        try {
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * �������ã�ָ�������ж�
     * ���������ָ��
     * ���ز�����1��½ 2 �˳� 3������ 0 ����
     */
    private int cmdCheck(String str) {
        int ret = 0;
        if (str.charAt(5) == '1') {
            ret = 1;
        } else if (str.charAt(5) == '2') {
            ret = 2;
        } else if (str.charAt(5) == '3') {
            ret = 3;
        } else if (str.charAt(5) == '4') {
            ret = 4;
        }
        return ret;
    }

    /*
     * �������ã����ϵͳ��ǰʱ��
     * �����������
     * ���ز�����ָ����ʽ�ַ���
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /*
     * �������ã����ϵͳ��ǰʱ��
     * �����������
     * ���ز�����ָ����ʽ�ַ���
     */
    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��HHʱ");
        return df.format(new Date());
    }

    /*
     * �������ã�TCP�����жϣ��жϵ�ǰ�����Ƿ�alive
     * ����������û���
     * ���ز�������ӦThread
     */
    private Thread threadCheck(String str) {
        int n = Thread.activeCount();// �õ��߳���
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            if (thread.getName().equals(str)) {
                if (thread.isAlive())
                    return thread;
            }
        }
        return null;
    }

    /*
     * �������ã��õ�������
     * �����������
     * ���ز�����������
     */
    public static int threadCheck() {
        int num = 0;
        int n = Thread.activeCount();// �õ��߳���
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            if (thread.getName().charAt(0) == '1') {
                ++num;
            }
        }
        return num;
    }
}
