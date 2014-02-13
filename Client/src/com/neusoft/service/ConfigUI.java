/*
 * ���ý�����
 */
package com.neusoft.service;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ConfigUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private JButton jbOk = new JButton("ȷ��");
    private JButton jbCancel = new JButton("ȡ��");
    private String name;
    private JLabel jlTit = new JLabel("������Ϣ��ʾ��ʽ");
    // private CheckboxGroup g = new CheckboxGroup();
    private JRadioButton cb1 = new JRadioButton("����Ϣ������ʾ");
    private JRadioButton cb2 = new JRadioButton("����Ϣ����ʾ");
    private ButtonGroup group = new ButtonGroup();

    /*
     * �������ã����췽�� 
     * ������������������꣬��ǰ�û��� 
     * ���ز�������
     */
    public ConfigUI(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        init();
    }

    /*
     * �������ã���ʼ���������������ô�������� 
     * ����������� 
     * ���ز�������
     */
    void init() {
        this.setLayout(null);
        setTitle("�û�����");
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
        jbOk.setLocation(13, 185);
        jbCancel.setSize(60, 20);
        jbCancel.setLocation(95, 185);
        cb1.setSize(120, 30);
        cb1.setLocation(23, 80);
        cb2.setSize(120, 20);
        cb2.setLocation(23, 110);
        cb1.setOpaque(false);
        cb2.setOpaque(false);
        cb1.setSelected(true);
        group.add(cb1);
        group.add(cb2);
        jlTit.setSize(130, 30);
        jlTit.setLocation(30, 35);
        jlTit.setForeground(Color.WHITE);
        cb1.setForeground(Color.WHITE);
        cb2.setForeground(Color.WHITE);
        this.add(jlTit);
        this.add(jbOk);
        this.add(jbCancel);
        this.add(cb1);
        this.add(cb2);

        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jbOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cb1.isSelected()) {
                    MainUI.confFlag = 0;
                    File file = new File("C:\\" + name + ".ini");
                    try {
                        Writer w = new FileWriter(file);
                        String wstr = "weather = " + MainUI.weatherFlag
                                + "\r\n" + "show = " + MainUI.confFlag;
                        w.write(wstr);
                        w.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if (cb2.isSelected()) {
                    MainUI.confFlag = 1;
                    File file = new File("C:\\" + name + ".ini");
                    try {
                        Writer w = new FileWriter(file);
                        String wstr = "weather = " + MainUI.weatherFlag
                                + "\r\n" + "show = " + MainUI.confFlag;
                        w.write(wstr);
                        w.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                dispose();
            }
        });
        JPanel jp = new JPanel();
        ImageIcon im = new ImageIcon("image\\1112.PNG");
        JLabel jl = new JLabel(im);
        jp.setSize(173, 240);
        jp.setLocation(0, -5);
        jp.add(jl);
        this.add(jp);
    }

    public void showView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}