package com.neusoft.service;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.neusoft.beans.AddressBean;
import com.neusoft.beans.MessageBean;
import com.neusoft.dao.AddressDao;
import com.neusoft.dao.MessageDao;
import com.sun.awt.AWTUtilities;

public class MainUI extends JFrame {
    //
    private static final long serialVersionUID = 1L;
    private String name = null;
    private JLabel displayArea = null;// 显示时间
    private String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private String time = null;
    private int ONE_SECOND = 500;
    private TCPClient tc = null;
    private JLabel weather = null; //显示天气
    private static Point origin = new Point();
    private JPanel jp = new JPanel();
    private JLabel jlNum;// 显示未读信息数
    private JLabel jl1 = new JLabel("写信息");
    private JLabel jl2 = new JLabel("收件箱");
    private JLabel jl3 = new JLabel("发件箱");
    private JLabel jl4 = new JLabel("草稿箱");
    private JLabel jl5 = new JLabel("通讯录");
    private JLabel jl6 = new JLabel("修改密码");
    private JLabel jlWeath = new JLabel("未知");
    private int num = 0;
    static int weatherFlag = 0; // 天气标志，0：未知，1：晴，2：雨。
    static int confFlag = 0; // 消息提示方式，0：弹框，1：震动
    private JButton jbConfig = new JButton(new ImageIcon("image\\设置.PNG"));
    //

    // 写短信 UI
    private JLabel jlRec = new JLabel("收件人");
    private JLabel jlMsg = new JLabel("消息内容");
    private JLabel jlMsgCount = new JLabel("100");
    private JTextField jtRec = new JTextField();
    private JTextArea jtMsg = new JTextArea();
    private JButton jbSend = new JButton("发送");
    private JButton jbReset = new JButton("重置");
    private JButton jbRet = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbAlist = new JButton(new ImageIcon("image\\sms.PNG"));
    private JScrollPane jsw = null;
    //

    // 收件箱 UI
    private JLabel jlTit = new JLabel("收件箱");
    private JTable tableRec = null;
    private DefaultTableModel defaultModel = null;
    private JScrollPane js = null;
    private JScrollPane jsr = null;
    private JButton jbrRet = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] data = null;
    private String[] adata = null;
    private int colorCount = 0;
    // //信息显示UI
    private JLabel jlSend = new JLabel("发件人");
    private JLabel jlSendTime = new JLabel("接收时间");
    private JLabel jlRecMsg = new JLabel("消息内容");
    private JTextArea jtSend = new JTextArea();
    private JTextArea jtSendTime = new JTextArea();
    private JTextArea jtRecMsg = new JTextArea();
    private JButton jbRecRet = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec1 = new JButton("回复");
    private JButton jbRec2 = new JButton("转发");
    private JButton jbRec3 = new JButton("删除");
    private JPopupMenu jpm = null;
    // ///////////////////////////
    //

    // 发件箱 UI
    private JLabel jlTits = new JLabel("发件箱");
    private JTable tableRecs = null;
    private DefaultTableModel defaultModels = null;
    private JScrollPane jss = null;
    private JScrollPane jsss = null;
    private JButton jbrRets = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] datas = null;
    private String[] adatas = null;
    // //信息显示UI
    private JLabel jlSends = new JLabel("收件人");
    private JLabel jlSendsTime = new JLabel("发送时间");
    private JLabel jlRecMsgs = new JLabel("消息内容");
    private JTextArea jtSends = new JTextArea();
    private JTextArea jtSendsTime = new JTextArea();
    private JTextArea jtRecMsgs = new JTextArea();
    private JButton jbRecRets = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec2s = new JButton("转发");
    private JButton jbRec3s = new JButton("删除");
    private JPopupMenu jpms = null;
    // ///////////////////////////
    //

    // 草稿箱 UI
    private JLabel jlTitt = new JLabel("草稿箱");
    private JTable tableRect = null;
    private DefaultTableModel defaultModelt = null;
    private JScrollPane jst = null;
    private JScrollPane jstt = null;
    private JButton jbrRett = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] datat = null;
    // //信息显示UI
    private JLabel jlSendt = new JLabel("收件人");
    private JLabel jlRecMsgt = new JLabel("消息内容");
    private JTextArea jtSendt = new JTextArea();
    private JTextArea jtRecMsgt = new JTextArea();
    private JButton jbRecRett = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec1t = new JButton("发送");
    private JButton jbRec2t = new JButton("编辑");
    private JButton jbRec3t = new JButton("删除");
    private JPopupMenu jpmt = null;
    // ///////////////////////////

    // 通讯录UI
    private JLabel jlAdTit = new JLabel("通讯录");
    private JButton jbAdAdd = new JButton("添加");
    private JButton jbAdCancel = new JButton("返回");
    private DefaultTableModel defaultModelAd = null;
    private JScrollPane sAd = null;
    private String[][] dataAd = null;
    private JTable jtAd = null;
    private JPopupMenu jpmAd = null;
    //

    // 修改密码
    private JLabel jlOldPwd = new JLabel("原密码");
    private JLabel jlNewPwd1 = new JLabel("新密码");
    private JLabel jlNewPwd2 = new JLabel("确认新密码");
    private JPasswordField jtOldPwd = new JPasswordField();
    private JPasswordField jtNewPwd1 = new JPasswordField();
    private JPasswordField jtNewPwd2 = new JPasswordField();
    private JButton jbPwdOk = new JButton("确认");
    private JButton jbPwdCanc = new JButton("返回");
    //

    // 主界面button begin
    private JButton jb1 = new JButton(new ImageIcon("image\\写短信.PNG"));
    private JButton jb2 = new JButton(new ImageIcon("image\\收件箱.PNG"));
    private JButton jb3 = new JButton(new ImageIcon("image\\发件箱.PNG"));
    private JButton jb4 = new JButton(new ImageIcon("image\\草稿箱.PNG"));
    private JButton jb5 = new JButton(new ImageIcon("image\\通讯录.PNG"));
    private JButton jb6 = new JButton(new ImageIcon("image\\改密码.PNG"));
    private JButton jbExit = new JButton(new ImageIcon("image\\关机.PNG"));
    // button end

    /*
     * 方法作用：构造方法 
     * 传入参数：TCP连接，用户名，新信息数 
     * 返回参数：无
     */
    public MainUI(TCPClient tc, String name, int num) {
        this.tc = tc;
        this.name = name;
        this.num = num;
        init();
    }

    /*
     * 方法作用：初始化方法，用于设置界面参数等
     * 传入参数：无
     * 返回参数：无
     */
    void init() {
        this.setLayout(null);
        setTitle("短信交易平台");
        setLocation(400, 160);
        setSize(270, 470);
        setUI();
        this.setResizable(false);
        this.setUndecorated(true);
        Shape shape = null;
        shape = new RoundRectangle2D.Double(0, 0, 270, 470, 26.0D, 26.0D);
        AWTUtilities.setWindowShape(this, shape);
        this.setAlwaysOnTop(true);
    }
    
    /*
     * 方法作用：设置界面上控件的位置等参数 
     * 传入参数：无
     * 返回参数：无
     */
    private void setUI() {
        
        // 读取配置文件 begin
        File file = new File("C:\\" + name + ".ini");
        if (file.exists()) {
            try {
                Reader r = new FileReader(file);
                int b = 0;
                StringBuffer sb = new StringBuffer();
                while ((b = r.read()) != -1) {
                    char ch = (char) b;
                    sb.append(ch);
                }
                String str = sb.toString();
                String[] conf = str.split("\r\n");
                boolean flagTemp = false;
                for (int i = 0; i < conf.length; i++) {
                    String[] temp = conf[i].split("=");
                    temp[0] = temp[0].trim();
                    temp[1] = temp[1].trim();
                    if (temp[0].equalsIgnoreCase("weather")) {
                        weatherFlag = Integer.parseInt(temp[1]);
                        flagTemp = true;
                    } else if (temp[0].equalsIgnoreCase("show")) {
                        confFlag = Integer.parseInt(temp[1]);
                        flagTemp = true;
                    }
                }
                if (flagTemp == false) {
                    Writer w = new FileWriter(file);
                    String wstr = "weather = 0"+ "\r\n" + "show = 0";
                    w.write(wstr);
                    w.close();
                }
                System.out.println("weanther = "+weatherFlag);
                System.out.println("show = "+confFlag);
                r.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
                Writer w = new FileWriter(file);
                String wstr = "weather = 0"+ "\r\n" + "show = 0";
                w.write(wstr);
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 读取配置文件 end
        displayArea = new JLabel();
        displayArea.setLocation(10, 10);
        displayArea.setSize(500, 100);
        displayArea.setFont(new Font("宋体", Font.BOLD, 26));
        displayArea.setForeground(new Color(220, 105, 30));
        displayArea.setLocation(80, 60);
        this.add(displayArea);

        jlNum = new JLabel();
        jlNum.setSize(150, 100);
        jlNum.setFont(new Font("宋体", Font.BOLD, 12));
        jlNum.setForeground(new Color(250, 105, 100));
        jlNum.setLocation(30, 130);
        this.add(jlNum);
        configMsg();
        configTimeArea();

        // 设置label 的字体颜色
        jlRec.setForeground(Color.WHITE);
        jlMsg.setForeground(Color.WHITE);
        jlMsgCount.setForeground(Color.WHITE);
        jlTit.setForeground(Color.WHITE);
        jlSend.setForeground(Color.WHITE);
        jlSendTime.setForeground(Color.WHITE);
        jlRecMsg.setForeground(Color.WHITE);
        jlTits.setForeground(Color.WHITE);
        jlSends.setForeground(Color.WHITE);
        jlSendsTime.setForeground(Color.WHITE);
        jlRecMsgs.setForeground(Color.WHITE);
        jlTitt.setForeground(Color.WHITE);
        jlSendt.setForeground(Color.WHITE);
        jlRecMsgt.setForeground(Color.WHITE);
        jlOldPwd.setForeground(Color.WHITE);
        jlNewPwd1.setForeground(Color.WHITE);
        jlNewPwd2.setForeground(Color.WHITE);
        jlAdTit.setForeground(Color.WHITE);
        jl1.setForeground(Color.WHITE);
        jl2.setForeground(Color.WHITE);
        jl3.setForeground(Color.WHITE);
        jl4.setForeground(Color.WHITE);
        jl5.setForeground(Color.WHITE);
        jl6.setForeground(Color.WHITE);
        jlWeath.setForeground(Color.WHITE);
        // /
        jl1.setSize(60, 30);
        jl1.setLocation(27, 281);
        jl2.setSize(60, 30);
        jl2.setLocation(117, 281);
        jl3.setSize(60, 30);
        jl3.setLocation(202, 281);
        jl4.setSize(60, 30);
        jl4.setLocation(27, 370);
        jl5.setSize(60, 30);
        jl5.setLocation(117, 370);
        jl6.setSize(60, 30);
        jl6.setLocation(196, 370);
        jlWeath.setFont(new Font("宋体", Font.BOLD, 22));
        jlWeath.setSize(100, 100);
        jlWeath.setLocation(199, 128);
        if (weatherFlag == 0) {
            jlWeath.setText("未知");
            jlWeath.setLocation(199, 128);
            weather = new JLabel(new ImageIcon("image\\未知.PNG"));
        } else if (weatherFlag == 1) {
            jlWeath.setText("晴");
            jlWeath.setLocation(210, 128);
            weather = new JLabel(new ImageIcon("image\\晴.PNG"));
        } else if (weatherFlag == 2) {
            jlWeath.setText("雨");
            jlWeath.setLocation(210, 128);
            weather = new JLabel(new ImageIcon("image\\雨.PNG"));
        }
        weather.setSize(234, 160);
        weather.setLocation(18, 40);
        weather.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.add(jlWeath);
        this.add(weather);
        // button begin
        jb1.setSize(60, 60);
        jb1.setLocation(18, 225);
        jb2.setSize(60, 60);
        jb2.setLocation(106, 225);
        jb3.setSize(60, 60);
        jb3.setLocation(192, 225);
        jb4.setSize(60, 60);
        jb4.setLocation(18, 315);
        jb5.setSize(60, 60);
        jb5.setLocation(106, 315);
        jb6.setSize(60, 60);
        jb6.setLocation(192, 315);
        jb1.setBackground(Color.BLACK);
        jb1.setBorder(null);
        jb1.setContentAreaFilled(false);
        jb2.setBackground(Color.BLACK);
        jb2.setBorder(null);
        jb2.setContentAreaFilled(false);
        jb3.setBackground(Color.BLACK);
        jb3.setBorder(null);
        jb3.setContentAreaFilled(false);
        jb4.setBackground(Color.BLACK);
        jb4.setBorder(null);
        jb4.setContentAreaFilled(false);
        jb5.setBackground(Color.BLACK);
        jb5.setBorder(null);
        jb5.setContentAreaFilled(false);
        jb6.setBackground(Color.BLACK);
        jb6.setBorder(null);
        jb6.setContentAreaFilled(false);
        jbExit.setSize(60, 60);
        jbExit.setLocation(107, 399);
        jbConfig.setSize(25, 25);
        jbConfig.setLocation(228, 7);
        jbExit.setBackground(Color.BLACK);
        jbExit.setBorder(null);
        jbExit.setContentAreaFilled(false);
        jbAlist.setBorder(null);
        jbAlist.setContentAreaFilled(false);
        jbConfig.setBorder(null);
        jbConfig.setContentAreaFilled(false);
        jb1.setName("写信息");
        jb2.setName("收件箱");
        jb3.setName("发件箱");
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(jb5);
        this.add(jb6);
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        
        this.add(jbExit);
        this.add(jbConfig);
        // button end

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // 按下mousePressed
                origin.x = e.getX(); // 当鼠标按下的时候获得窗口当前的位置
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // 拖动（mouseDragged
                Point p = getLocation(); // 当鼠标拖动时获取窗口当前位置
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                        - origin.y);
            }
        });
        // 写信息
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeAl();
                paintWrite();
                repaint();
            }
        });
        // 收件箱
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintReceMain();
                repaint();
            }
        });
        // 发件箱
        jb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeAl();
                paintSendMain();
                repaint();
            }
        });
        // 草稿箱
        jb4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintTmpMain();
                repaint();
            }
        });
        // 通讯录
        jb5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintAd();
                repaint();
            }
        });
        // 修改密码
        jb6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintPwd();
                repaint();
            }
        });
        //退出按钮
        jbExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "确定要关机？",
                        "提示", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    tc.sendMsg("CMD002");
                }
            }
        });
        //设置按钮
        jbConfig.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point p = getLocation();
                new ConfigUI(p.x + 60, p.y + 100, name).showView();
            }
        });
        ImageIcon im = new ImageIcon("image\\1111.PNG");
        JLabel jl = new JLabel(im);
        jp.setSize(270, 530);
        jp.setLocation(0, -11);
        jp.add(jl);
        this.add(jp);

        // 写短信
        jlRec.setSize(40, 20);
        jlRec.setLocation(30, 40);
        jtRec.setSize(103, 20);
        jtRec.setLocation(90, 40);
        jlMsg.setSize(100, 20);
        jlMsg.setLocation(30, 70);
        jlMsgCount.setSize(100, 20);
        jlMsgCount.setLocation(203, 72);
        jtMsg.setSize(200, 270);
        jtMsg.setLocation(30, 102);
        jtMsg.setLineWrap(true); // 激活自动换行功能
        jtMsg.setWrapStyleWord(true); // 激活断行不断字功能
        jsw = new JScrollPane(jtMsg);
        jsw.setSize(200, 270);
        jsw.setLocation(30, 102);
        jbRet.setSize(25, 25);
        jbRet.setLocation(27, 8);
        jbRet.setBorder(null);
        jbRet.setContentAreaFilled(false);
        jbSend.setSize(60, 30);
        jbSend.setLocation(50, 400);
        jbReset.setSize(60, 30);
        jbReset.setLocation(150, 400);
        jbAlist.setSize(20, 18);
        jbAlist.setLocation(200, 43);
        jbAlist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                Point p = getLocation();
                new AListUI(p.x + 240, p.y + 50, jtRec, name).showView();
            }
        });
        jbSend.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String strRec = jtRec.getText();
                String strMsg = jtMsg.getText();
                if (strMsg.length() == 0) {
                    JOptionPane.showMessageDialog(getThis(), "短信内容不得为空！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strRec.length() == 0) {
                    JOptionPane.showMessageDialog(getThis(), "收件人不得为空！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (pCheck(strRec) == 2) {
                        int len = strRec.length();
                        for (int i = 0; i < 11 - len; i++) {
                            strRec = strRec + " ";
                        }
                        MessageDao.getInstance().insertMsgSend(name,
                                jtRec.getText(), jtMsg.getText());
                        String msg = "CMD003" + name + strRec + jtMsg.getText();
                        tc.sendMsg(msg);
                        jtRec.setText("");
                        jtMsg.setText("");
                        removeWrite();
                        addAll();
                    } else if (pCheck(strRec) == 1) {
                        JOptionPane.showMessageDialog(getThis(),
                                "收件人号码包含非法字符！", "错误",
                                JOptionPane.ERROR_MESSAGE);
                        jtRec.setText("");
                    } else if (pCheck(strRec) == 0) {
                        JOptionPane.showMessageDialog(getThis(),
                                "收件人号码过长！", "错误",
                                JOptionPane.ERROR_MESSAGE);
                        jtRec.setText("");
                    }
                }
            }
        });
        jbReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                jtMsg.setText("");
            }
        });
        jbRet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!jtMsg.getText().equals("")) {
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "是否保存为草稿？", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        MessageDao.getInstance().insertMsgTmp(name,
                                jtRec.getText(), jtMsg.getText());
                    }
                }
                jtRec.setText("");
                jtMsg.setText("");
                removeWrite();
                addAll();
            }
        });
        jtMsg.addKeyListener(new KeyListener() {
            String strMsg = null;

            public void keyTyped(KeyEvent arg0) {

            }

            public void keyReleased(KeyEvent arg0) {
                int count = 100 - jtMsg.getText().length();
                jlMsgCount.setText(String.valueOf(count));
                if (count >= 0) {
                    strMsg = jtMsg.getText();
                } else {
                    jlMsgCount.setText(String.valueOf(0));
                    jtMsg.setText(strMsg);
                    JOptionPane.showMessageDialog(getThis(), "超出短信内容长度限制！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }

            public void keyPressed(KeyEvent arg0) {

            }
        });
        //

        // 收件箱
        jlTit.setSize(40, 20);
        jlTit.setLocation(115, 20);
        jbrRet.setSize(30, 30);
        jbrRet.setLocation(120, 415);
        jbrRet.setBorder(null);
        jbrRet.setContentAreaFilled(false);
        // ////////////////////////
        jlSend.setSize(40, 20);
        jlSend.setLocation(30, 40);
        jlSendTime.setSize(100, 20);
        jlSendTime.setLocation(30, 70);
        jtSend.setSize(125, 20);
        jtSend.setLocation(105, 40);
        jtSend.setEditable(false);
        jtSendTime.setSize(125, 20);
        jtSendTime.setLocation(105, 70);
        jtSendTime.setEditable(false);
        jlRecMsg.setSize(100, 20);
        jlRecMsg.setLocation(30, 100);
        jtRecMsg.setSize(200, 240);
        jtRecMsg.setLocation(30, 132);
        jtRecMsg.setEditable(false);
        jtRecMsg.setLineWrap(true); // 激活自动换行功能
        jtRecMsg.setWrapStyleWord(true); // 激活断行不断字功能
        jsr = new JScrollPane(jtRecMsg);
        jsr.setSize(200, 240);
        jsr.setLocation(30, 132);
        jtRecMsg.setDragEnabled(true);
        jbRecRet.setSize(25, 25);
        jbRecRet.setLocation(27, 8);
        jbRecRet.setBorder(null);
        jbRecRet.setContentAreaFilled(false);
        jbRec1.setSize(60, 20);
        jbRec1.setLocation(30, 400);
        jbRec2.setSize(60, 20);
        jbRec2.setLocation(100, 400);
        jbRec3.setSize(60, 20);
        jbRec3.setLocation(170, 400);
        jbRecRet.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                removeReceShow();
                paintReceMain();
                revalidate();
                repaint();
            }
        });
        jbRec1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                jtRec.setText(jtSend.getText());
                removeReceShow();
                paintWrite();
                revalidate();
                repaint();
            }
        });
        jbRec2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                jtMsg.setText(jtRecMsg.getText());
                removeReceShow();
                paintWrite();
                revalidate();
                repaint();
            }
        });
        jbRec3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "确定删除此条短信？",
                        "提示", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsg.getText()
                            + "' and type = 0 and time = '"
                            + jtSendTime.getText() + "'";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "删除成功");
                    removeReceShow();
                    paintReceMain();
                    repaint();
                }
            }
        });
        tableRec = new JTable(defaultModel);
        tableRec.setPreferredScrollableViewportSize(new Dimension(400, 80));
        js = new JScrollPane(tableRec);
        js.setSize(220, 330);
        js.setLocation(25, 65);
        //

        // 发件箱
        jlTits.setSize(40, 20);
        jlTits.setLocation(115, 20);
        jbrRets.setSize(30, 30);
        jbrRets.setLocation(120, 415);
        jbrRets.setBorder(null);
        jbrRets.setContentAreaFilled(false);
        // ////////////////////////
        jlSends.setSize(40, 20);
        jlSends.setLocation(30, 40);
        jlSendsTime.setSize(100, 20);
        jlSendsTime.setLocation(30, 70);
        jtSends.setSize(125, 20);
        jtSends.setLocation(105, 40);
        jtSends.setEditable(false);
        jtSendsTime.setSize(125, 20);
        jtSendsTime.setLocation(105, 70);
        jtSendsTime.setEditable(false);
        jlRecMsgs.setSize(100, 20);
        jlRecMsgs.setLocation(30, 100);
        jtRecMsgs.setSize(200, 240);
        jtRecMsgs.setLocation(30, 132);
        jtRecMsgs.setEditable(false);
        jtRecMsgs.setLineWrap(true); // 激活自动换行功能
        jtRecMsgs.setWrapStyleWord(true); // 激活断行不断字功能
        jsss = new JScrollPane(jtRecMsgs);
        jsss.setSize(200, 240);
        jsss.setLocation(30, 132);
        jbRecRets.setSize(25, 25);
        jbRecRets.setLocation(27, 8);
        jbRecRets.setBorder(null);
        jbRecRets.setContentAreaFilled(false);
        jbRec2s.setSize(60, 30);
        jbRec2s.setLocation(50, 400);
        jbRec3s.setSize(60, 30);
        jbRec3s.setLocation(150, 400);
        jbRecRets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeSendShow();
                paintSendMain();
                revalidate();
                repaint();
            }
        });
        // 转发
        jbRec2s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jtMsg.setText(jtRecMsgs.getText());
                removeSendShow();
                paintWrite();
                revalidate();
                repaint();
            }
        });
        // 删除
        jbRec3s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "确定删除此条短信？",
                        "提示", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsgs.getText()
                            + "' and type = 1 and time = '"
                            + jtSendsTime.getText() + "'";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "删除成功");
                    removeSendShow();
                    paintSendMain();
                    repaint();
                }
            }
        });
        tableRecs = new JTable(defaultModel);
        tableRecs.setPreferredScrollableViewportSize(new Dimension(400, 80));
        jss = new JScrollPane(tableRec);
        jss.setSize(220, 330);
        jss.setLocation(25, 65);
        //

        // 草稿箱
        jlTitt.setSize(40, 20);
        jlTitt.setLocation(115, 20);
        jbrRett.setSize(30, 30);
        jbrRett.setLocation(120, 415);
        jbrRett.setBorder(null);
        jbrRett.setContentAreaFilled(false);
        // ////////////////////////
        jlSendt.setSize(40, 20);
        jlSendt.setLocation(30, 40);
        jtSendt.setSize(125, 20);
        jtSendt.setLocation(105, 40);
        jtSendt.setEditable(false);
        jlRecMsgt.setSize(100, 20);
        jlRecMsgt.setLocation(30, 70);
        jtRecMsgt.setSize(200, 270);
        jtRecMsgt.setLocation(30, 102);
        jtRecMsgt.setEditable(false);
        jtRecMsgt.setLineWrap(true); // 激活自动换行功能
        jtRecMsgt.setWrapStyleWord(true); // 激活断行不断字功能
        jstt = new JScrollPane(jtRecMsgt);
        jstt.setSize(200, 270);
        jstt.setLocation(30, 102);
        jbRecRett.setSize(25, 25);
        jbRecRett.setLocation(27, 8);
        jbRecRett.setBorder(null);
        jbRecRett.setContentAreaFilled(false);
        jbRec2t.setSize(60, 30);
        jbRec2t.setLocation(50, 400);
        jbRec3t.setSize(60, 30);
        jbRec3t.setLocation(150, 400);
        jbRecRett.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeTmpShow();
                paintTmpMain();
                revalidate();
                repaint();
            }
        });
        // 编辑
        jbRec2t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                jtMsg.setText(jtRecMsgt.getText());
                jtRec.setText(jtSendt.getText());
                removeTmpShow();
                paintWrite();
                revalidate();
                repaint();
            }
        });
        // 删除
        jbRec3t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "确定删除此条短信？",
                        "提示", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsgt.getText()
                            + "' and type = 2";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "删除成功");
                    removeTmpShow();
                    paintTmpMain();
                    repaint();
                }
            }
        });
        tableRect = new JTable(defaultModel);
        tableRect.setPreferredScrollableViewportSize(new Dimension(400, 80));
        jst = new JScrollPane(tableRec);
        jst.setSize(220, 330);
        jst.setLocation(25, 65);
        //

        // 通讯录
        jlAdTit.setSize(40, 20);
        jlAdTit.setLocation(115, 20);
        jbAdAdd.setSize(60, 30);
        jbAdAdd.setLocation(60, 420);
        jbAdCancel.setSize(60, 30);
        jbAdCancel.setLocation(150, 420);
        jbAdAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Point p = getLocation();
                new AAdd(p.x + 60, p.y + 100, getThis(), name).showView();
            }
        });
        jbAdCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                removeAd();
                addAll();
            }
        });
        //

        //
        // 修改密码
        jlOldPwd.setSize(100, 25);
        jlOldPwd.setLocation(110, 105);
        jtOldPwd.setSize(145, 25);
        jtOldPwd.setLocation(60, 135);
        jlNewPwd1.setSize(100, 25);
        jlNewPwd1.setLocation(110, 165);
        jtNewPwd1.setSize(145, 25);
        jtNewPwd1.setLocation(60, 195);
        jlNewPwd2.setSize(100, 25);
        jlNewPwd2.setLocation(97, 225);
        jtNewPwd2.setSize(145, 25);
        jtNewPwd2.setLocation(60, 255);
        jlOldPwd.setFont(new Font("宋体", Font.BOLD, 14));
        jlNewPwd1.setFont(new Font("宋体", Font.BOLD, 14));
        jlNewPwd2.setFont(new Font("宋体", Font.BOLD, 14));
        jbPwdOk.setSize(60, 30);
        jbPwdOk.setLocation(60, 310);
        jbPwdCanc.setSize(60, 30);
        jbPwdCanc.setLocation(140, 310);
        jbPwdCanc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removePwd();
                addAll();
            }
        });

        jbPwdOk.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent arg0) {
                String str = jtOldPwd.getText();
                String str1 = jtNewPwd1.getText();
                String str2 = jtNewPwd2.getText();
                System.out.println(str1 + " " + str2);
                if (!str1.equals(str2)) {
                    JOptionPane.showMessageDialog(getThis(), "新密码不一致，请重新输入！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    String sender = "CMD004" + name + str + "&" + str2;
                    tc.sendMsg(sender);
                }
            }
        });
    }
    
    /*
     * 方法作用：移除主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeAl() {
        jb1.setVisible(false);
        jb2.setVisible(false);
        jb3.setVisible(false);
        jb4.setVisible(false);
        jb5.setVisible(false);
        weather.setVisible(false);
        jp.setVisible(false);
        jb6.setVisible(false);
        jlNum.setVisible(false);
        displayArea.setVisible(false);
        jbExit.setVisible(false);
        jl1.setVisible(false);
        jl2.setVisible(false);
        jl3.setVisible(false);
        jl4.setVisible(false);
        jl5.setVisible(false);
        jl6.setVisible(false);
        jlWeath.setVisible(false);
        this.remove(jb1);
        this.remove(jb2);
        this.remove(jb3);
        this.remove(jb4);
        this.remove(jb5);
        this.remove(jb6);
        this.remove(jl1);
        this.remove(jl2);
        this.remove(jl3);
        this.remove(jl4);
        this.remove(jl5);
        this.remove(jl6);
        this.remove(displayArea);
        this.remove(jlNum);
        this.remove(weather);
        this.remove(jbExit);
        this.remove(jlWeath);
        this.remove(jp);
    }

    /*
     * 方法作用：添加主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void addAll() {
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(jb5);
        this.add(jb6);
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jbExit);
        this.add(jlWeath);
        this.add(jlNum);
        this.add(displayArea);
        this.add(weather);
        this.add(jp);

        jb1.setVisible(true);
        jb2.setVisible(true);
        jb3.setVisible(true);
        jb4.setVisible(true);
        jb5.setVisible(true);
        jb6.setVisible(true);
        jl1.setVisible(true);
        jl2.setVisible(true);
        jl3.setVisible(true);
        jl4.setVisible(true);
        jl5.setVisible(true);
        jl6.setVisible(true);
        jbExit.setVisible(true);
        jbExit.setVisible(true);
        jlNum.setVisible(true);
        displayArea.setVisible(true);
        jlWeath.setVisible(true);
        weather.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：添加写信息界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintWrite() {
        this.add(jlRec);
        this.add(jtRec);
        this.add(jlMsg);
        this.add(jlMsgCount);
        jlMsgCount.setText("100");
        this.add(jsw);
        this.add(jbRet);
        this.add(jbSend);
        this.add(jbReset);
        this.add(jbAlist);
        this.add(jp);
        jlRec.setVisible(true);
        jtRec.setVisible(true);
        jlMsg.setVisible(true);
        jlMsgCount.setVisible(true);
        jsw.setVisible(true);
        jbRet.setVisible(true);
        jbSend.setVisible(true);
        jbReset.setVisible(true);
        jbAlist.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除写信息界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeWrite() {
        jlRec.setVisible(false);
        jtRec.setVisible(false);
        jlMsg.setVisible(false);
        jlMsgCount.setVisible(false);
        jsw.setVisible(false);
        jbRet.setVisible(false);
        jbSend.setVisible(false);
        jbReset.setVisible(false);
        jbAlist.setVisible(false);
        jp.setVisible(false);
        this.add(jlRec);
        this.add(jtRec);
        this.add(jlMsg);
        this.add(jlMsgCount);
        this.add(jsw);
        this.add(jbRet);
        this.add(jbSend);
        this.add(jbReset);
        this.add(jbAlist);
        this.remove(jp);
    }

    /*
     * 方法作用：添加发件箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintSendShow() {
        this.add(jlSends);
        this.add(jtSends);
        this.add(jlSendsTime);
        this.add(jtSendsTime);
        this.add(jlRecMsgs);
        this.add(jsss);
        this.add(jbRec2s);
        this.add(jbRec3s);
        this.add(jbRecRets);
        this.add(jp);
        jlSends.setVisible(true);
        jtSends.setVisible(true);
        jlSendsTime.setVisible(true);
        jtSendsTime.setVisible(true);
        jlRecMsgs.setVisible(true);
        jsss.setVisible(true);
        jbRec2s.setVisible(true);
        jbRec3s.setVisible(true);
        jbRecRets.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除发件箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeSendShow() {
        jlSends.setVisible(false);
        jtSends.setVisible(false);
        jlSendsTime.setVisible(false);
        jtSendsTime.setVisible(false);
        jlRecMsgs.setVisible(false);
        jsss.setVisible(false);
        jbRecRets.setVisible(false);
        jbRec2s.setVisible(false);
        jbRec3s.setVisible(false);
        jp.setVisible(false);

        this.remove(jlSends);
        this.remove(jtSends);
        this.remove(jlSendsTime);
        this.remove(jtSendsTime);
        this.remove(jlRecMsgs);
        // this.remove(jtRecMsgs);
        this.remove(jsss);
        this.remove(jbRecRets);
        this.remove(jbRec2s);
        this.remove(jbRec3s);
        this.remove(jp);

    }

    /*
     * 方法作用：添加发件箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintSendMain() {
        String[] tag = { "收件人", "消息内容" };
        int cnt = 0, num = 0;
        num = MessageDao.getInstance().selectMsgNumSend(this.name);
        datas = new String[num][2];
        adatas = new String[num];
        ArrayList<MessageBean> al = MessageDao.getInstance().selectMsgSend(
                this.name);
        for (int i = 0; i < al.size(); i++) {
            adatas[cnt] = al.get(i).getTime();
            datas[cnt][0] = al.get(i).getReceiver();
            datas[cnt++][1] = al.get(i).getMessage();
        }
        defaultModels = new DefaultTableModel(datas, tag) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jbrRets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeSendMain();
                addAll();
                revalidate();
                repaint();
            }
        });
        tableRecs = new JTable(defaultModels);
        tableRecs.setPreferredScrollableViewportSize(new Dimension(400, 80));
        TableColumn firsetColumn = tableRecs.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(88);
        firsetColumn.setMaxWidth(88);
        firsetColumn.setMinWidth(88);
        jss = new JScrollPane(tableRecs);
        jss.setSize(220, 330);
        jss.setLocation(25, 65);
        this.add(jlTits);
        this.add(jss);
        this.add(jbrRets);
        jlTits.setVisible(true);
        jss.setVisible(true);
        jbrRets.setVisible(true);
        jpms = new JPopupMenu();
        jpms.setSize(10, 70);
        JMenuItem jm1 = new JMenuItem("打开信息 ");
        JMenuItem jm3 = new JMenuItem("转发信息 ");
        JMenuItem jm4 = new JMenuItem("删除信息");
        jpms.add(jm1);
        jpms.addSeparator();
        jpms.add(jm3);
        jpms.addSeparator();
        jpms.add(jm4);
        tableRecs.setComponentPopupMenu(jpms);
        tableRecs.setSelectionBackground(new Color(240, 255, 255));
        tableRecs.getTableHeader().setReorderingAllowed(false);
        tableRecs.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    if (arg0.getClickCount() == 2) {
                        int row = tableRecs.getSelectedRow();
                        if (row != -1) {
                            String str1 = new String(datas[row][0]);
                            String str2 = new String(datas[row][1]);
                            String str3 = new String(adatas[row]);
                            jtSendsTime.setText(str3);
                            jtSends.setText(str1);
                            jtRecMsgs.setText(str2);
                            removeSendMain();
                            paintSendShow();
                            revalidate();
                            repaint();
                        }
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableRecs.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    tableRecs.setRowSelectionInterval(row, row);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = tableRecs.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    tableRecs.setRowSelectionInterval(row, row);
                }
            }

        });
        jm1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRecs.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datas[row][0]);
                    String str2 = new String(datas[row][1]);
                    String str3 = new String(adatas[row]);
                    jtSendsTime.setText(str3);
                    jtSends.setText(str1);
                    jtRecMsgs.setText(str2);
                    removeSendMain();
                    paintSendShow();
                    revalidate();
                    repaint();
                }

            }
        });
        jm3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRecs.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datas[row][1]);
                    jtMsg.setText(str1);
                    removeSendMain();
                    paintWrite();
                    revalidate();
                    repaint();
                }

            }
        });
        jm4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRecs.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datas[row][1]);
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定删除此条短信？", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1
                                + "' and type = 1 and time = '" + adatas[row]
                                + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "删除成功");
                        removeSendMain();
                        paintSendMain();
                        revalidate();
                        repaint();
                    }
                }

            }
        });
        this.add(jp);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除发件箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeSendMain() {
        jlTits.setVisible(false);
        jss.setVisible(false);
        jbrRets.setVisible(false);
        tableRecs.setVisible(false);
        jp.setVisible(false);
        this.remove(jlTits);
        this.remove(jss);
        this.remove(jbrRets);
        this.remove(tableRecs);
        this.remove(jp);
    }

    /*
     * 方法作用：添加草稿箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintTmpShow() {
        this.add(jlSendt);
        this.add(jtSendt);
        this.add(jlRecMsgt);
        this.add(jstt);
        this.add(jbRec1t);
        this.add(jbRec2t);
        this.add(jbRec3t);
        this.add(jbRecRett);
        this.add(jp);
        jlSendt.setVisible(true);
        jtSendt.setVisible(true);
        jlRecMsgt.setVisible(true);
        jstt.setVisible(true);
        jbRec1t.setVisible(true);
        jbRec2t.setVisible(true);
        jbRec3t.setVisible(true);
        jbRecRett.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除草稿箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeTmpShow() {
        jlSendt.setVisible(false);
        jtSendt.setVisible(false);
        jlRecMsgt.setVisible(false);
        jstt.setVisible(false);
        jbRecRett.setVisible(false);
        jbRec1t.setVisible(false);
        jbRec2t.setVisible(false);
        jbRec3t.setVisible(false);
        jp.setVisible(false);

        this.remove(jlSendt);
        this.remove(jtSendt);
        this.remove(jlRecMsgt);
        this.remove(jstt);
        this.remove(jbRecRett);
        this.remove(jbRec1t);
        this.remove(jbRec2t);
        this.remove(jbRec3t);
        this.remove(jp);

    }

    /*
     * 方法作用：添加草稿箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintTmpMain() {
        String[] tag = { "收件人", "消息内容" };
        int cnt = 0, num = 0;
        num = MessageDao.getInstance().selectMsgNumTmp(this.name);
        datat = new String[num][2];
        ArrayList<MessageBean> al = MessageDao.getInstance().selectMsgTmp(
                this.name);
        for (int i = 0; i < al.size(); i++) {
            datat[cnt][0] = al.get(i).getReceiver();
            datat[cnt++][1] = al.get(i).getMessage();
        }
        defaultModelt = new DefaultTableModel(datat, tag) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jbrRett.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeTmpMain();
                addAll();
                revalidate();
                repaint();
            }
        });
        tableRect = new JTable(defaultModelt);
        tableRect.setPreferredScrollableViewportSize(new Dimension(400, 80));
        TableColumn firsetColumn = tableRect.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(88);
        firsetColumn.setMaxWidth(88);
        firsetColumn.setMinWidth(88);
        jst = new JScrollPane(tableRect);
        jst.setSize(220, 330);
        jst.setLocation(25, 65);
        this.add(jlTitt);
        this.add(jst);
        this.add(jbrRett);
        jlTitt.setVisible(true);
        jst.setVisible(true);
        jbrRett.setVisible(true);
        jpmt = new JPopupMenu();
        jpmt.setSize(10, 70);
        JMenuItem jm1 = new JMenuItem("打开信息 ");
        JMenuItem jm3 = new JMenuItem("编辑信息 ");
        JMenuItem jm4 = new JMenuItem("删除信息");
        jpmt.add(jm1);
        jpmt.addSeparator();
        jpmt.add(jm3);
        jpmt.addSeparator();
        jpmt.add(jm4);
        tableRect.getTableHeader().setReorderingAllowed(false);
        tableRect.setComponentPopupMenu(jpmt);
        tableRect.setSelectionBackground(new Color(240, 255, 255));
        tableRect.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    if (arg0.getClickCount() == 2) {
                        int row = tableRect.getSelectedRow();
                        if (row != -1) {
                            String str1 = new String(datat[row][0]);
                            String str2 = new String(datat[row][1]);
                            jtSendt.setText(str1);
                            jtRecMsgt.setText(str2);
                            removeTmpMain();
                            paintTmpShow();
                            revalidate();
                            repaint();
                        }
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                int row = tableRect.rowAtPoint(arg0.getPoint());
                if (row >= 0) {
                    tableRect.setRowSelectionInterval(row, row);
                }
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                int row = tableRect.rowAtPoint(arg0.getPoint());
                if (row >= 0) {
                    tableRect.setRowSelectionInterval(row, row);
                }
            }
        });
        // 打开
        jm1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRect.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datat[row][0]);
                    String str2 = new String(datat[row][1]);
                    jtSendt.setText(str1);
                    jtRecMsgt.setText(str2);
                    removeTmpMain();
                    paintTmpShow();
                    revalidate();
                    repaint();
                }

            }
        });
        // 编辑
        jm3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRect.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datat[row][0]);
                    String str2 = new String(datat[row][1]);
                    jtRec.setText(str1);
                    jtMsg.setText(str2);
                    removeTmpMain();
                    paintWrite();
                    revalidate();
                    repaint();
                }

            }
        });
        // 删除
        jm4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRect.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datat[row][1]);
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定删除此条短信？", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1 + "' and type = 2";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "删除成功");
                        removeTmpMain();
                        paintTmpMain();
                        revalidate();
                        repaint();
                    }
                }

            }
        });
        this.add(jp);
        jp.setVisible(true);
    }
    
    /*
     * 方法作用：移除草稿箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeTmpMain() {
        jlTitt.setVisible(false);
        jst.setVisible(false);
        jbrRett.setVisible(false);
        tableRect.setVisible(false);
        jp.setVisible(false);
        this.remove(jlTitt);
        this.remove(jst);
        this.remove(jbrRett);
        this.remove(tableRect);
        this.remove(jp);
    }

    /*
     * 方法作用：添加收件箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintReceShow() {
        this.add(jlSend);
        this.add(jtSend);
        this.add(jlSendTime);
        this.add(jtSendTime);
        this.add(jlRecMsg);
        this.add(jsr);
        this.add(jbRec1);
        this.add(jbRec2);
        this.add(jbRec3);
        this.add(jbRecRet);
        this.add(jp);
        jlSend.setVisible(true);
        jtSend.setVisible(true);
        jlSendTime.setVisible(true);
        jtSendTime.setVisible(true);
        jlRecMsg.setVisible(true);
        jsr.setVisible(true);
        jbRec1.setVisible(true);
        jbRec2.setVisible(true);
        jbRec3.setVisible(true);
        jbRecRet.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除收件箱显示界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeReceShow() {
        jlSend.setVisible(false);
        jtSend.setVisible(false);
        jlSendTime.setVisible(false);
        jtSendTime.setVisible(false);
        jlRecMsg.setVisible(false);
        jsr.setVisible(false);
        jbRecRet.setVisible(false);
        jbRec1.setVisible(false);
        jbRec2.setVisible(false);
        jbRec3.setVisible(false);
        jp.setVisible(false);

        this.remove(jlSend);
        this.remove(jtSend);
        this.remove(jlSendTime);
        this.remove(jtSendTime);
        this.remove(jlRecMsg);
        this.remove(jsr);
        this.remove(jbRecRet);
        this.remove(jbRec1);
        this.remove(jbRec2);
        this.remove(jbRec3);
        this.remove(jp);
    }

    /*
     * 方法作用：移除收件箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintReceMain() {
        String[] tag = { "发件人", "消息内容" };
        int cnt = 0, num = 0;
        num = MessageDao.getInstance().selectMsgNum(this.name);
        data = new String[num][2];
        adata = new String[num];
        colorCount = 0;
        ArrayList<MessageBean> al = MessageDao.getInstance().selectMsg(
                this.name);
        for (int i = 0; i < al.size(); i++) {
            adata[cnt] = al.get(i).getTime();
            data[cnt][0] = al.get(i).getSender();
            data[cnt++][1] = al.get(i).getMessage();
            if (al.get(i).getIsRead() == 0)
                colorCount++;
        }
        defaultModel = new DefaultTableModel(data, tag) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if (row < colorCount)
                    setBackground(new Color(245, 222, 179));
                else
                    setBackground(Color.white);
                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
            }
        };

        jbrRet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeReceMain();
                addAll();
                revalidate();
                repaint();
            }
        });
        tableRec = new JTable(defaultModel);
        tableRec.setPreferredScrollableViewportSize(new Dimension(400, 80));
        for (int i = 0; i < tableRec.getColumnCount(); i++) {
            tableRec.getColumn(tableRec.getColumnName(i)).setCellRenderer(tcr);
        }
        TableColumn firsetColumn = tableRec.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(88);
        firsetColumn.setMaxWidth(88);
        firsetColumn.setMinWidth(88);
        js = new JScrollPane(tableRec);
        js.setSize(220, 330);
        js.setLocation(25, 65);
        this.add(jlTit);
        this.add(js);
        this.add(jbrRet);
        jlTit.setVisible(true);
        js.setVisible(true);
        jbrRet.setVisible(true);
        jpm = new JPopupMenu();
        jpm.setSize(10, 70);
        JMenuItem jm1 = new JMenuItem("打开信息 ");
        JMenuItem jm2 = new JMenuItem("回复信息 ");
        JMenuItem jm3 = new JMenuItem("转发信息 ");
        JMenuItem jm4 = new JMenuItem("删除信息");
        jpm.add(jm1);
        jpm.addSeparator();
        jpm.add(jm2);
        jpm.addSeparator();
        jpm.add(jm3);
        jpm.addSeparator();
        jpm.add(jm4);
        tableRec.setComponentPopupMenu(jpm);
        tableRec.getTableHeader().setReorderingAllowed(false);
        tableRec.setSelectionBackground(new Color(240, 255, 255));
        tableRec.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent arg0) {
                int row2 = tableRec.rowAtPoint(arg0.getPoint());
                if (row2 >= 0) {
                    tableRec.setRowSelectionInterval(row2, row2);
                }
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    if (arg0.getClickCount() == 2) {
                        int row = tableRec.getSelectedRow();
                        if (row != -1) {
                            String str1 = new String(data[row][0]);
                            String str2 = new String(data[row][1]);
                            String str3 = new String(adata[row]);
                            String sql = "update " + "\"" + name + "\""
                                    + "set isread = 1 where message = '" + str2
                                    + "' and time = '" + adata[row] + "'";
                            MessageDao.getInstance().doQuery(sql);
                            jtSend.setText(str1);
                            jtSendTime.setText(str3);
                            jtRecMsg.setText(str2);
                            removeReceMain();
                            paintReceShow();
                            revalidate();
                            repaint();
                        }
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent arg0) {
                int row = tableRec.rowAtPoint(arg0.getPoint());
                if (row >= 0) {
                    tableRec.setRowSelectionInterval(row, row);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent arg0) {
                
            }
            
            @Override
            public void mouseEntered(MouseEvent arg0) {
                
            }
            
            @Override
            public void mouseClicked(MouseEvent arg0) {
                
            }
        });
        jm1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRec.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(data[row][0]);
                    String str2 = new String(data[row][1]);
                    String str3 = new String(adata[row]);
                    String sql = "update " + "\"" + name + "\""
                            + "set isread = 1 where message = '" + str2
                            + "' and time = '" + adata[row] + "'";
                    MessageDao.getInstance().doQuery(sql);
                    jtSend.setText(str1);
                    jtSendTime.setText(str3);
                    jtRecMsg.setText(str2);
                    removeReceMain();
                    paintReceShow();
                    revalidate();
                    repaint();
                }
            }
        });

        jm2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRec.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(data[row][0]);
                    jtRec.setText(str1);
                    removeReceMain();
                    paintWrite();
                    revalidate();
                    repaint();
                }

            }
        });

        jm3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRec.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(data[row][1]);
                    jtMsg.setText(str1);
                    removeReceMain();
                    paintWrite();
                    revalidate();
                    repaint();
                }

            }
        });
        jm4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRec.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(data[row][1]);
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定删除此条短信？", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1
                                + "' and type = 0 and time = '" + adata[row]
                                + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "删除成功");
                        removeReceMain();
                        paintReceMain();
                        repaint();
                    }
                }
            }
        });
        this.add(jp);
        jp.setVisible(true);
    }
    
    /*
     * 方法作用：移除收件箱主界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removeReceMain() {
        jlTit.setVisible(false);
        js.setVisible(false);
        jbrRet.setVisible(false);
        tableRec.setVisible(false);
        jp.setVisible(false);
        this.remove(jlTit);
        this.remove(js);
        this.remove(jbrRet);
        this.remove(tableRec);
        this.remove(jp);
    }

    /*
     * 方法作用：添加通讯录界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    public void paintAd() {
        this.add(jbAdAdd);
        this.add(jbAdCancel);
        this.add(jlAdTit);
        jbAdAdd.setVisible(true);
        jbAdCancel.setVisible(true);
        jlAdTit.setVisible(true);
        int cnt = 0, num = 0;
        num = AddressDao.getInstance().selectAdNum(this.name);
        dataAd = new String[num][2];
        ArrayList<AddressBean> al = AddressDao.getInstance()
                .selectAd(this.name);
        for (int i = 0; i < al.size(); i++) {
            dataAd[cnt][0] = al.get(i).getName();
            dataAd[cnt++][1] = al.get(i).getPhoneno();
        }
        String[] nameAd = { "姓名", "手机号码" };

        defaultModelAd = new DefaultTableModel(dataAd, nameAd) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if (row % 2 == 0)
                    setBackground(Color.white);
                else if (row % 2 == 1)
                    setBackground(new Color(255, 248, 220));
                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
            }
        };
        jtAd = new JTable(defaultModelAd);
        jtAd.setSize(220, 330);
        jtAd.setLocation(25, 65);
        TableColumn firsetColumn = jtAd.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(88);
        firsetColumn.setMaxWidth(88);
        firsetColumn.setMinWidth(88);
        for (int i = 0; i < jtAd.getColumnCount(); i++) {
            jtAd.getColumn(jtAd.getColumnName(i)).setCellRenderer(tcr);
        }
        sAd = new JScrollPane(jtAd);
        sAd.setSize(220, 330);
        sAd.setLocation(25, 65);

        jpmAd = new JPopupMenu();
        jpmAd.setSize(10, 70);
        JMenuItem jm1 = new JMenuItem("修改记录 ");
        JMenuItem jm3 = new JMenuItem("删除记录 ");
        JMenuItem jm4 = new JMenuItem("发送短信");
        jpmAd.add(jm1);
        jpmAd.addSeparator();
        jpmAd.add(jm3);
        jpmAd.addSeparator();
        jpmAd.add(jm4);
        jtAd.setComponentPopupMenu(jpmAd);
        jtAd.getTableHeader().setReorderingAllowed(false);
        jtAd.setSelectionBackground(new Color(240, 255, 255));
        jtAd.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent arg0) {
                int row = jtAd.rowAtPoint(arg0.getPoint());
                if (row >= 0) {
                    jtAd.setRowSelectionInterval(row, row);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent arg0) {
                int row = jtAd.rowAtPoint(arg0.getPoint());
                if (row >= 0) {
                    jtAd.setRowSelectionInterval(row, row);
                }                
            }
            
            @Override
            public void mouseExited(MouseEvent arg0) {
                
            }
            
            @Override
            public void mouseEntered(MouseEvent arg0) {
                
            }
            
            @Override
            public void mouseClicked(MouseEvent arg0) {
                
            }
        });
        jm1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = jtAd.getSelectedRow();
                if (row != -1) {
                    String str1 = dataAd[row][0];
                    String str2 = dataAd[row][1];
                    Point p = getLocation();
                    new AModi(p.x + 60, p.y + 100, getThis(), name, str1, str2)
                            .showView();
                }

            }
        });

        jm3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int row = jtAd.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(dataAd[row][0]);
                    String str2 = new String(dataAd[row][1]);
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定删除此联系人？", "提示", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "a\""
                                + "where name = '" + str1 + "' and phoneno = '"
                                + str2 + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "删除成功");
                        removeAd();
                        paintAd();
                        repaint();
                    }
                }

            }
        });

        jm4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = jtAd.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(dataAd[row][1]);
                    jtRec.setText(str1);
                    removeAd();
                    paintWrite();
                    revalidate();
                    repaint();
                }

            }
        });

        this.add(sAd);
        this.add(jp);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除通讯录界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    public void removeAd() {
        this.remove(jbAdAdd);
        this.remove(jbAdCancel);
        this.remove(jlAdTit);
        this.remove(sAd);
        this.remove(jp);
        jbAdAdd.setVisible(false);
        jbAdCancel.setVisible(false);
        jlAdTit.setVisible(false);
        sAd.setVisible(false);
        jp.setVisible(false);
    }

    /*
     * 方法作用：添加修改密码界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void paintPwd() {
        this.add(jlOldPwd);
        this.add(jtOldPwd);
        this.add(jlNewPwd1);
        this.add(jtNewPwd1);
        this.add(jlNewPwd2);
        this.add(jtNewPwd2);
        this.add(jbPwdOk);
        this.add(jbPwdCanc);
        this.add(jp);

        jlOldPwd.setVisible(true);
        jlNewPwd1.setVisible(true);
        jlNewPwd2.setVisible(true);
        jtOldPwd.setVisible(true);
        jtNewPwd1.setVisible(true);
        jtNewPwd2.setVisible(true);
        jbPwdOk.setVisible(true);
        jbPwdCanc.setVisible(true);
        jp.setVisible(true);
    }

    /*
     * 方法作用：移除修改密码界面的所有控件
     * 传入参数：无
     * 返回参数：无
     */
    private void removePwd() {
        jlOldPwd.setVisible(false);
        jlNewPwd1.setVisible(false);
        jlNewPwd2.setVisible(false);
        jtOldPwd.setVisible(false);
        jtNewPwd1.setVisible(false);
        jtNewPwd2.setVisible(false);
        jbPwdOk.setVisible(false);
        jbPwdCanc.setVisible(false);
        jp.setVisible(false);

        this.remove(jlOldPwd);
        this.remove(jtOldPwd);
        this.remove(jlNewPwd1);
        this.remove(jtNewPwd1);
        this.remove(jlNewPwd2);
        this.remove(jtNewPwd2);
        this.remove(jbPwdOk);
        this.remove(jbPwdCanc);
        this.remove(jp);
    }

    /*
     * 方法作用：验证收件人的合法性
     * 传入参数：收件人手机号
     * 返回参数：整数，0为手机号过长，1为包含非数字字符，2为合法。
     */
    private int pCheck(String str) {
        if (str.length() > 11)
            return 0;
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                return 1;
            }
        }
        return 2;
    }

    /*
     * 方法作用：配置时间显示区域
     * 传入参数：无
     * 返回参数：无
     */
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
    }

   /*
    * 时间显示的TimerTask
    */
    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                DEFAULT_TIME_FORMAT);

        public void run() {
            time = dateFormatter.format(Calendar.getInstance().getTime());
            displayArea.setText(time);
            int count = MessageDao.getInstance().selectNum(name);
            if (count > 0) {
                jlNum.setText("您有 " + count + " 条未读信息");
            } else {
                jlNum.setText("");
            }
        }
    }
    /*
     * 方法作用：窗口抖动
     * 传入参数：无
     * 返回参数：无
     */
    private void shake() {
        try {
            for (int i = 0; i < 10; i++) {
                Point p = this.getLocation();
                setLocation(p.x + 5, p.y);
                Thread.sleep(20);
                setLocation(p.x + 5, p.y + 5);
                Thread.sleep(20);
                setLocation(p.x, p.y + 5);
                Thread.sleep(20);
                setLocation(p.x, p.y);
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /*
     * 方法作用：配置即时接收短信
     * 传入参数：无
     * 返回参数：无
     */
    private void configMsg() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new GetMsg(), new Date(), 2000);
    }

    /*
     * 即时接收短信的TimerTask
     */
    protected class GetMsg extends TimerTask {
        public void run() {
            String str = tc.getMsg();
            if (str.charAt(5) == '3') {
                MessageDao.getInstance().insertMsg(name,
                        str.substring(6, 17).trim(),
                        str.substring(17, str.length()), "");
                if (str.substring(6, 17).trim().equals("002")) {
                    if (str.substring(17, str.length()).indexOf("雨") != -1) {
                        weather.setVisible(false);
                        jp.setVisible(false);
                        remove(weather);
                        remove(jp);
                        File file = new File("C:\\" + name + ".ini");
                        try {
                            Writer w = new FileWriter(file);
                            String wstr = "weather = 2";
                            w.write(wstr);
                            w.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        jlWeath.setText("雨");
                        jlWeath.setLocation(210, 128);
                        weather = new JLabel(new ImageIcon("image\\雨.PNG"));
                        weather.setSize(234, 160);
                        weather.setLocation(18, 40);
                        add(weather);
                        add(jp);
                        weather.setVisible(true);
                        jp.setVisible(true);
                        repaint();
                    } else if(str.substring(17, str.length()).indexOf("晴") != -1) {
                        weather.setVisible(false);
                        jp.setVisible(false);
                        remove(weather);
                        remove(jp);
                        File file = new File("C:\\" + name + ".ini");
                        try {
                            Writer w = new FileWriter(file);
                            String wstr = "weather = 1";
                            w.write(wstr);
                            w.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        jlWeath.setText("晴");
                        jlWeath.setLocation(210, 128);
                        weather = new JLabel(new ImageIcon("image\\晴.PNG"));
                        weather.setSize(234, 160);
                        weather.setLocation(18, 40);
                        add(weather);
                        add(jp);
                        weather.setVisible(true);
                        jp.setVisible(true);
                        repaint();
                    }
                }
                if (confFlag == 1) {
                    shake();
                } else {
                    JOptionPane.showMessageDialog(getThis(), "收到新消息");
                }
            } else if (str.charAt(5) == '4') {
                if (str.charAt(6) == '1') {
                    JOptionPane.showMessageDialog(getThis(), "修改成功，请重新登录");
                    tc.sendMsg("CMD002");
                } else if (str.charAt(6) == '0') {
                    JOptionPane.showMessageDialog(getThis(), "修改失败，原密码错误！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else if (str.charAt(5) == '2') {
                tc.close();
                System.exit(0);
            } else if (str.charAt(5) == '5') {
                char ch = str.charAt(6);
                if (ch == '0') {
                    JOptionPane.showMessageDialog(getThis(), "短信发送失败，接收用户不存在！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                } else if (ch == '1') {
                    JOptionPane.showMessageDialog(getThis(), "短信发送失败，您的余额不足！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                } else if (ch == '2') {
                    JOptionPane.showMessageDialog(getThis(), "短信发送成功！");
                }
            }
        }
    }
    
    /*
     * 方法作用：得到当前窗体
     * 传入参数：无
     * 返回参数：当前窗体
     */
    private MainUI getThis() {
        return this;
    }

    public void showView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        if (num != 0) {
            if (confFlag == 1) {
                shake();
            }
            JOptionPane.showMessageDialog(this, "收到 " + num + " 条新消息！");
        }
    }
}