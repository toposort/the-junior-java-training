package com.neusoft.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.beans.CustomerBean;
import com.neusoft.beans.EcardBean;
import com.neusoft.dao.CustomerDao;
import com.neusoft.dao.EcardDao;

public class CustomerUtils {
    private CustomerDao cd = CustomerDao.getInstance();
    @SuppressWarnings("unused")
    private CustomerBean account = null;
    private static CustomerUtils instance;

    private CustomerUtils() {
    }

    public static CustomerUtils getInstance() {
        if (instance == null) {
            instance = new CustomerUtils();
        }
        return instance;
    }
    
    /*
     * �������ã������ֻ��Ų����û��Ƿ����
     * ���������������ֻ��� 
     * ���ز���������ֵ��trueΪ���ڣ�falseΪ������
     */
    public boolean findAccountByCode(String code) {
        CustomerBean acc = cd.findAccountByCode(code);
        if (acc == null) {
            return false;
        }
        return true;
    }

    /*
     * �������ã��ͻ���½���
     * ���������������ֻ��� 
     * ���ز�����������0Ϊ�˺Ų����ڣ�1Ϊ��¼�ɹ���2Ϊ���벻��ȷ
     */
    public int accountLogin(String code, String password) {
        CustomerBean acc = cd.findAccountByCode(code);
        if (acc == null) {
            return 0;// �˺Ų�����
        }
        boolean boo = acc.getPassword().equals(password);
        if (boo) {
            this.account = acc;
            return 1;// ��½�ɹ�
        }
        return 2;// ���벻��ȷ
    }

    /*
     * �������ã��ͻ��޸�����
     * ���������������ֻ��� 
     * ���ز���������ֵ��trueΪ�޸ĳɹ���falseΪԭ���벻��ȷ
     */
    public boolean changePassword(String usr, String oldpass, String newpass) {
        CustomerBean acc = cd.findAccountByCode(usr);
        if (!oldpass.equalsIgnoreCase(acc.getPassword())) {
            return false;// ����ľ����벻��ȷ
        }
        acc.setPassword(newpass);
        cd.updateAccount(acc);
        return true;
    }

    /*
     * �������ã��ͻ����Ͷ��ż��
     * ����������������ֻ��ţ��ռ����ֻ���
     * ���ز�����CMD-003- 0 ����ʧ��-�û������� �۷� 1����ʧ��-���� ���۷� 2 ���ͳɹ� �۷�
     */
    public int sendMessage(String usr, String rece) {
        CustomerBean acc = cd.findAccountByCode(usr);
        double current = acc.getMoney();
        if (current > 0.1) {
            acc.setMoney(current - 0.1);
            cd.updateAccount(acc);
            if (rece.equals("001        ")) {// �����
                return 3;
            }
            if (rece.equals("002        ")) {// ������
                return 4;
            }
            if (rece.equals("003        ")) {// �仰��
                return 5;
            }
            CustomerBean acc1 = cd.findAccountByCode(rece);
            if (acc1 == null) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    /*
     * �������ã��ͻ���ֵ����
     * ����������ֻ��ţ���ֵ����
     * ���ز�����������ע��
     */
    public int addMoney(String usr, String msg) {
        if (msg.length() < 16) {
            return 0;
        }
        String id = msg.substring(0, 10);
        String pwd = msg.substring(10, msg.length());
        EcardDao ed = new EcardDao().getInstance();
        EcardBean eb = ed.findAccountByCode(id);
        if (eb == null || !pwd.equals(eb.getPwd())) {
            return 0;// ���ܴ���
        }
        if (eb.getIsUsed() == 1) {
            return 1;// �ѱ�ʹ��
        }
        if (eb.getTime().compareTo(getCurrenttime()) < 0) {
            return 2;// ����
        }
        CustomerBean acc = cd.findAccountByCode(usr);
        acc.setMoney(acc.getMoney() + eb.getMoney());
        cd.updateAccount(acc);
        eb.setIsUsed(1);
        ed.updateAccount(eb);
        return 3;

    }

    /*
     * �������ã����ϵͳ��ǰʱ��
     * �����������
     * ���ز�������
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
}
