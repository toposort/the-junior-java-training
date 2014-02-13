/*
 * ��½������
 */
package com.neusoft.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import com.sun.awt.*;
import com.neusoft.dao.MessageDao;

class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel displayArea; // ��ʾʱ��
    private String DEFAULT_TIME_FORMAT = "HH:mm:ss"; // ʱ���ʽ
    private String time;
    private final int ONE_SECOND = 1000;
    private JLabel jlUsr; // �û���
    private JTextField jtfUsr; // �û��������
    private JLabel jlPwd; // ����
    private JPasswordField jtfPwd; // ���������
    private JButton jbOk; // ��½��ť
    private JButton jbCancel; // ȡ����ť
    private TCPClient tc = null; // TCP����
    private Point origin = new Point();

    /*
     * �������ã����췽�� 
     * ���������TCP���� 
     * ���ز�������
     */
    public MyFrame(TCPClient tc) {
        this.tc = tc;
        init();
    }

    /*
     * �������ã���ʼ���������������ý�������� 
     * �����������
     * ���ز�������
     */    
    void init() {
        this.setLayout(null);
        setTitle("User Login");
        setLocation(400, 160);
        setSize(270, 470);
        setUI();
        enterPressesWhenFocused(jbOk);
        this.setResizable(false);
        this.setUndecorated(true);
        Shape shape = null;
        shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),
                26.0D, 26.0D);
        AWTUtilities.setWindowShape(this, shape);
        this.setAlwaysOnTop(true);
    }

    /*
     * �������ã����ð��س���������½��ť�¼� 
     * �����������ťJButton
     * ���ز�������
     */
    public static void enterPressesWhenFocused(JButton button) {
        button.registerKeyboardAction(button.getActionForKeyStroke(KeyStroke
                .getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
                .getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        button.registerKeyboardAction(button.getActionForKeyStroke(KeyStroke
                .getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
                .getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public static void enterPressesWhenFocused(JTextField textField,
            ActionListener actionListener) {
        textField.registerKeyboardAction(actionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        textField.registerKeyboardAction(actionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);
    }

    /*
     * �������ã����ý����Ͽؼ�λ�õȲ��� 
     * �����������
     * ���ز�������
     */
    private void setUI() {
        displayArea = new JLabel();
        displayArea.setLocation(80, 10);
        displayArea.setSize(500, 100);
        displayArea.setFont(new Font("����", Font.BOLD, 26));
        displayArea.setForeground(Color.WHITE);
        this.add(displayArea);
        configTimeArea();
        jlUsr = new JLabel("�ֻ���");
        jlUsr.setForeground(Color.WHITE);
        jlPwd = new JLabel("��    ��");
        jlPwd.setForeground(Color.WHITE);
        jtfUsr = new JTextField();
        jtfPwd = new JPasswordField();
        jbOk = new JButton(new ImageIcon("image\\��½.PNG"));
        jbCancel = new JButton("�˳�");
        jlUsr.setSize(40, 30);
        jlUsr.setLocation(35, 120);
        jlPwd.setSize(40, 30);
        jlPwd.setLocation(35, 170);
        jtfUsr.setSize(150, 30);
        jtfUsr.setLocation(80, 120);
        jtfPwd.setSize(150, 30);
        jtfPwd.setLocation(80, 170);
        jbOk.setSize(62, 62);
        jbOk.setLocation(110, 240);
        jbOk.setBackground(Color.BLACK);
        jbOk.setBorder(null);
        jbOk.setContentAreaFilled(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // ����mousePressed
                origin.x = e.getX(); // ����갴�µ�ʱ���ô��ڵ�ǰ��λ��
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // �϶�mouseDragged
                Point p = getLocation(); // ������϶�ʱ��ȡ���ڵ�ǰλ��
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                        - origin.y);
            }
        });

        jbOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usr = jtfUsr.getText();
                @SuppressWarnings("deprecation")
                String pwd = jtfPwd.getText();
                int ret = check(usr, pwd);
                if (ret == -1) {
                    JOptionPane.showMessageDialog(getThis(),
                            "�ֻ��Ż����벻��Ϊ�գ����������룡", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else if (ret == 0) {
                    JOptionPane.showMessageDialog(getThis(),
                            "�ֻ��ų��ȱ���Ϊ11λ�����������룡", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else if (ret == 1) {
                    JOptionPane
                            .showMessageDialog(getThis(), "�ֻ���ֻ�ܰ������֣����������룡",
                                    "����", JOptionPane.ERROR_MESSAGE);
                } else if (ret == 2) {
                    String str = "CMD001" + usr + pwd;
                    tc.sendMsg(str);
                    String gt = tc.getMsg();
                    // System.out.println(gt);
                    if (gt.equals("4")) {
                        JOptionPane.showMessageDialog(getThis(),
                                "�û������ߣ��벻Ҫ�ظ���¼", "����",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (gt.charAt(0) == '1') {
                        // JOptionPane.showMessageDialog(null, "��½�ɹ�");
                        String sql = "create table \""
                                + usr
                                + "\""
                                + "(receiver NVARCHAR2(12), sender   NVARCHAR2(12), message  NVARCHAR2(222) not null, type NUMBER not null,time     NVARCHAR2(22) not null,isread   NUMBER)";
                        String sql1 = "create table \"" + usr + "a\""
                                + "(name NVARCHAR2(12), phoneno NVARCHAR2(12))";
                        MessageDao.getInstance().doQuery(sql);
                        MessageDao.getInstance().doQuery(sql1);
                        int num = Integer.parseInt(gt.substring(1));
                        for (int i = 0; i < num; i++) {
                            String strr = tc.getMsg();
                            String sed = strr.substring(0, 11);
                            String time = strr.substring(11, 30);
                            String msg = strr.substring(30, strr.length());
                            MessageDao.getInstance().insertMsg(usr, sed, msg,
                                    time);
                        }
                        dispose();
                        MainUI mu = new MainUI(tc, usr, num);
                        mu.showView();
                    } else if (gt.equals("0")) {
                        JOptionPane.showMessageDialog(getThis(), "�˺Ų�����", "����",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (gt.equals("2")) {
                        JOptionPane.showMessageDialog(getThis(), "���벻��ȷ", "����",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        this.add(jbOk);
        this.add(jbCancel);
        this.add(jlUsr);
        this.add(jlPwd);
        this.add(jtfPwd);
        this.add(jtfUsr);
        ImageIcon im = new ImageIcon("image\\1111.PNG");
        JLabel jl = new JLabel(im);
        JPanel jp = new JPanel();
        jp.setSize(270, 530);
        jp.setLocation(0, -11);
        jp.add(jl);
        this.add(jp);
    }

    /*
     * �������ã�����ֻ��ź�����ĺϷ��� 
     * ����������ֻ��ţ�����
     * ���ز���������ֵ���Ϸ�Ϊtrue�����Ϸ�Ϊfalse
     */
    private int check(String usr, String pwd) {
        if (usr.length() == 0 || pwd.length() == 0) {
            return -1;
        }
        if (usr.length() != 11) {
            return 0;// �ֻ��ų��Ȳ��Ϸ�
        }
        for (int i = 0; i < usr.length(); i++) {
            if (!(usr.charAt(i) >= '0' && usr.charAt(i) <= '9')) {
                return 1;// �ֻ��Ÿ�ʽ���Ϸ�
            }
        }
        return 2;
    }

    /*
     * �������ã��õ���ǰ���� 
     * �����������
     * ���ز�������ǰ����
     */
    private MyFrame getThis() {
        return this;
    }

    /*
     * �������ã�����ʱ����ʾ�� 
     * �����������
     * ���ز�������
     */
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
    }

    /*
     * ������ʾʱ���TimerTask��
     */
    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                DEFAULT_TIME_FORMAT);

        @Override
        public void run() {
            time = dateFormatter.format(Calendar.getInstance().getTime());
            displayArea.setText(time);
        }
    }

    public void showView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

public class LoginUI {
    public static void main(String[] args) {
        TCPClient tc = new TCPClient();
        if (tc.isStart) {
            MyFrame mf = new MyFrame(tc);
            mf.showView();
        }
    }
}