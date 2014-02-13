/*
 * 登陆界面类
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
    private JLabel displayArea; // 显示时间
    private String DEFAULT_TIME_FORMAT = "HH:mm:ss"; // 时间格式
    private String time;
    private final int ONE_SECOND = 1000;
    private JLabel jlUsr; // 用户名
    private JTextField jtfUsr; // 用户名输入框
    private JLabel jlPwd; // 密码
    private JPasswordField jtfPwd; // 密码输入框
    private JButton jbOk; // 登陆按钮
    private JButton jbCancel; // 取消按钮
    private TCPClient tc = null; // TCP连接
    private Point origin = new Point();

    /*
     * 方法作用：构造方法 
     * 传入参数：TCP连接 
     * 返回参数：无
     */
    public MyFrame(TCPClient tc) {
        this.tc = tc;
        init();
    }

    /*
     * 方法作用：初始化方法，用于设置界面参数等 
     * 传入参数：无
     * 返回参数：无
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
     * 方法作用：设置按回车键触发登陆按钮事件 
     * 传入参数：按钮JButton
     * 返回参数：无
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
     * 方法作用：设置界面上控件位置等参数 
     * 传入参数：无
     * 返回参数：无
     */
    private void setUI() {
        displayArea = new JLabel();
        displayArea.setLocation(80, 10);
        displayArea.setSize(500, 100);
        displayArea.setFont(new Font("宋体", Font.BOLD, 26));
        displayArea.setForeground(Color.WHITE);
        this.add(displayArea);
        configTimeArea();
        jlUsr = new JLabel("手机号");
        jlUsr.setForeground(Color.WHITE);
        jlPwd = new JLabel("密    码");
        jlPwd.setForeground(Color.WHITE);
        jtfUsr = new JTextField();
        jtfPwd = new JPasswordField();
        jbOk = new JButton(new ImageIcon("image\\登陆.PNG"));
        jbCancel = new JButton("退出");
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
            public void mousePressed(MouseEvent e) { // 按下mousePressed
                origin.x = e.getX(); // 当鼠标按下的时候获得窗口当前的位置
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // 拖动mouseDragged
                Point p = getLocation(); // 当鼠标拖动时获取窗口当前位置
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
                            "手机号或密码不得为空，请重新输入！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else if (ret == 0) {
                    JOptionPane.showMessageDialog(getThis(),
                            "手机号长度必须为11位，请重新输入！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else if (ret == 1) {
                    JOptionPane
                            .showMessageDialog(getThis(), "手机号只能包含数字，请重新输入！",
                                    "错误", JOptionPane.ERROR_MESSAGE);
                } else if (ret == 2) {
                    String str = "CMD001" + usr + pwd;
                    tc.sendMsg(str);
                    String gt = tc.getMsg();
                    // System.out.println(gt);
                    if (gt.equals("4")) {
                        JOptionPane.showMessageDialog(getThis(),
                                "用户已在线，请不要重复登录", "错误",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (gt.charAt(0) == '1') {
                        // JOptionPane.showMessageDialog(null, "登陆成功");
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
                        JOptionPane.showMessageDialog(getThis(), "账号不存在", "错误",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (gt.equals("2")) {
                        JOptionPane.showMessageDialog(getThis(), "密码不正确", "错误",
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
     * 方法作用：检查手机号合密码的合法性 
     * 传入参数：手机号，密码
     * 返回参数：布尔值，合法为true，不合法为false
     */
    private int check(String usr, String pwd) {
        if (usr.length() == 0 || pwd.length() == 0) {
            return -1;
        }
        if (usr.length() != 11) {
            return 0;// 手机号长度不合法
        }
        for (int i = 0; i < usr.length(); i++) {
            if (!(usr.charAt(i) >= '0' && usr.charAt(i) <= '9')) {
                return 1;// 手机号格式不合法
            }
        }
        return 2;
    }

    /*
     * 方法作用：得到当前窗体 
     * 传入参数：无
     * 返回参数：当前窗体
     */
    private MyFrame getThis() {
        return this;
    }

    /*
     * 方法作用：配置时间显示区 
     * 传入参数：无
     * 返回参数：无
     */
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
    }

    /*
     * 定义显示时间的TimerTask类
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