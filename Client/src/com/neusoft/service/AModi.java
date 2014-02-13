/*
 * 修改通讯录界面类
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

public class AModi extends JFrame {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private JButton jbOk = new JButton("确定");
    private JButton jbCancel = new JButton("取消");
    private JLabel jlName = new JLabel("姓名");
    private JLabel jlPhoneNo = new JLabel("手机号码");
    private JLabel jlTit = new JLabel("修改联系人");
    private JTextField jtName = new JTextField();
    private JTextField jtPhoneNo = new JTextField();
    private String name;
    private String str1; // 原姓名
    private String str2; // 原手机号
    private MainUI mu = null;

    /*
     * 方法作用：构造方法
     * 传入参数：父窗体坐标，父窗体，当前用户名，姓名，原姓名，原手机号
     * 返回参数：无
     */
    public AModi(int x, int y, MainUI mu, String name, String str1, String str2) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.str1 = str1;
        this.str2 = str2;
        this.mu = mu;
        init();
    }

    /*
     * 方法作用：初始化方法，用于设置窗体参数等
     * 传入参数：无
     * 返回参数：无
     */
    void init() {
        this.setLayout(null);
        setTitle("修改记录");
        setLocation(x, y);
        setSize(170, 235);
        setUI();
        this.setResizable(false);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
    }

    /*
     * 方法作用：设置界面控件位置等参数
     * 传入参数：无
     * 返回参数：无
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
        jtName.setText(str1);
        jtPhoneNo.setText(str2);
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
                    boolean ret = AddressDao.getInstance().updateAd(name, str1,
                            str2, s1, s2);
                    if (ret) {
                        JOptionPane.showMessageDialog(null, "更新成功！");
                        mu.removeAd();
                        mu.paintAd();
                        mu.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "更新失败！");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "您输入的姓名或手机号格式错误！");
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
     * 方法作用：检查姓名和手机号合法性
     * 传入参数：姓名，手机号
     * 返回参数：布尔值，合法为true，不合法为false
     */
    private boolean check(String name, String phoneno) {
        if (name.length() > 10 || phoneno.length() > 11) {
            return false;
        }
        for (int i = 0; i < phoneno.length(); i++) {
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
