/*
 * ���ͨѶ¼������
 */
package com.neusoft.service;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neusoft.dao.AddressDao;

public class AAdd extends JFrame {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private JButton jbOk = new JButton("ȷ��");
    private JButton jbCancel = new JButton("ȡ��");
    private JLabel jlName = new JLabel("����");
    private JLabel jlPhoneNo = new JLabel("�ֻ�����");
    private JLabel jlTit = new JLabel("�����ϵ��");
    private JTextField jtName = new JTextField();
    private JTextField jtPhoneNo = new JTextField();
    private MainUI mu = null;
    private String name = null;

    /*
     * �������ã����췽��
     * ������������������꣬�����壬��ǰ�û���
     * ���ز�������
     */
    public AAdd(int x, int y, MainUI mu, String name) {
        this.x = x;
        this.y = y;
        this.mu = mu;
        this.name = name;
        init();
    }

    /*
     * �������ã���ʼ�����壬���ô��������
     * �����������
     * ���ز�������
     */
    void init() {
        this.setLayout(null);
        setTitle("��Ӽ�¼");
        setLocation(x, y);
        setSize(170, 235);
        setUI();
        this.setResizable(false);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
    }

    /*
     * �������ã����ý���ؼ�λ�õȲ���
     * �����������
     * ���ز�������
     */
    private void setUI() {
        jbOk.setSize(60, 20);
        jbOk.setLocation(15, 175);
        jbCancel.setSize(60, 20);
        jbCancel.setLocation(92, 175);
        jlName.setSize(30, 20);
        jlName.setLocation(15, 70);
        jlTit.setSize(100, 35);
        jlTit.setLocation(55, 15);
        jtName.setSize(85, 18);
        jtName.setLocation(75, 70);
        jlPhoneNo.setSize(60, 20);
        jlPhoneNo.setLocation(15, 115);
        jtPhoneNo.setSize(85, 18);
        jtPhoneNo.setLocation(75, 115);

        jlName.setForeground(Color.WHITE);
        jlPhoneNo.setForeground(Color.WHITE);
        jlTit.setForeground(Color.WHITE);
        this.add(jlPhoneNo);
        this.add(jtPhoneNo);
        this.add(jtName);
        this.add(jlTit);
        this.add(jlName);
        this.add(jbOk);
        this.add(jbCancel);
        JPanel jp = new JPanel();
        ImageIcon im = new ImageIcon("image\\1112.PNG");
        JLabel jl = new JLabel(im);
        jp.setSize(173, 240);
        jp.setLocation(0, -5);
        jp.add(jl);
        this.add(jp);
        jbOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s1 = jtName.getText();
                String s2 = jtPhoneNo.getText();
                if (check(s1, s2)) {
                    boolean ret = AddressDao.getInstance().insertAd(name, s1,
                            s2);
                    if (ret) {
                        JOptionPane.showMessageDialog(null, "��ӳɹ���");
                        mu.removeAd();
                        mu.paintAd();
                        mu.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "���ʧ�ܣ�");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "��������������ֻ��Ÿ�ʽ����");
                }
            }
        });
        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /*
     * �������ã�����������ֻ��ŵĺϷ��ԡ� 
     * ����������������ֻ���
     * ���ز���������ֵ���Ϸ�Ϊtrue�����Ϸ�Ϊfalse
     */
    private boolean check(String name, String phoneno) {
        int lenn = name.length();
        int lenp = phoneno.length();
        if (lenn > 10 || lenp > 11 || lenn == 0 || lenp == 0) {
            return false;
        }
        for (int i = 0; i < lenp; i++) {
            if (!(phoneno.charAt(i) >= '0' && phoneno.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }
    public void showView() {
        setVisible(true);
    }
}
