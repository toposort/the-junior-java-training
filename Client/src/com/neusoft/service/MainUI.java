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
    private JLabel displayArea = null;// ��ʾʱ��
    private String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private String time = null;
    private int ONE_SECOND = 500;
    private TCPClient tc = null;
    private JLabel weather = null; //��ʾ����
    private static Point origin = new Point();
    private JPanel jp = new JPanel();
    private JLabel jlNum;// ��ʾδ����Ϣ��
    private JLabel jl1 = new JLabel("д��Ϣ");
    private JLabel jl2 = new JLabel("�ռ���");
    private JLabel jl3 = new JLabel("������");
    private JLabel jl4 = new JLabel("�ݸ���");
    private JLabel jl5 = new JLabel("ͨѶ¼");
    private JLabel jl6 = new JLabel("�޸�����");
    private JLabel jlWeath = new JLabel("δ֪");
    private int num = 0;
    static int weatherFlag = 0; // ������־��0��δ֪��1���磬2���ꡣ
    static int confFlag = 0; // ��Ϣ��ʾ��ʽ��0������1����
    private JButton jbConfig = new JButton(new ImageIcon("image\\����.PNG"));
    //

    // д���� UI
    private JLabel jlRec = new JLabel("�ռ���");
    private JLabel jlMsg = new JLabel("��Ϣ����");
    private JLabel jlMsgCount = new JLabel("100");
    private JTextField jtRec = new JTextField();
    private JTextArea jtMsg = new JTextArea();
    private JButton jbSend = new JButton("����");
    private JButton jbReset = new JButton("����");
    private JButton jbRet = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbAlist = new JButton(new ImageIcon("image\\sms.PNG"));
    private JScrollPane jsw = null;
    //

    // �ռ��� UI
    private JLabel jlTit = new JLabel("�ռ���");
    private JTable tableRec = null;
    private DefaultTableModel defaultModel = null;
    private JScrollPane js = null;
    private JScrollPane jsr = null;
    private JButton jbrRet = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] data = null;
    private String[] adata = null;
    private int colorCount = 0;
    // //��Ϣ��ʾUI
    private JLabel jlSend = new JLabel("������");
    private JLabel jlSendTime = new JLabel("����ʱ��");
    private JLabel jlRecMsg = new JLabel("��Ϣ����");
    private JTextArea jtSend = new JTextArea();
    private JTextArea jtSendTime = new JTextArea();
    private JTextArea jtRecMsg = new JTextArea();
    private JButton jbRecRet = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec1 = new JButton("�ظ�");
    private JButton jbRec2 = new JButton("ת��");
    private JButton jbRec3 = new JButton("ɾ��");
    private JPopupMenu jpm = null;
    // ///////////////////////////
    //

    // ������ UI
    private JLabel jlTits = new JLabel("������");
    private JTable tableRecs = null;
    private DefaultTableModel defaultModels = null;
    private JScrollPane jss = null;
    private JScrollPane jsss = null;
    private JButton jbrRets = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] datas = null;
    private String[] adatas = null;
    // //��Ϣ��ʾUI
    private JLabel jlSends = new JLabel("�ռ���");
    private JLabel jlSendsTime = new JLabel("����ʱ��");
    private JLabel jlRecMsgs = new JLabel("��Ϣ����");
    private JTextArea jtSends = new JTextArea();
    private JTextArea jtSendsTime = new JTextArea();
    private JTextArea jtRecMsgs = new JTextArea();
    private JButton jbRecRets = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec2s = new JButton("ת��");
    private JButton jbRec3s = new JButton("ɾ��");
    private JPopupMenu jpms = null;
    // ///////////////////////////
    //

    // �ݸ��� UI
    private JLabel jlTitt = new JLabel("�ݸ���");
    private JTable tableRect = null;
    private DefaultTableModel defaultModelt = null;
    private JScrollPane jst = null;
    private JScrollPane jstt = null;
    private JButton jbrRett = new JButton(new ImageIcon("image\\rett.PNG"));
    private String[][] datat = null;
    // //��Ϣ��ʾUI
    private JLabel jlSendt = new JLabel("�ռ���");
    private JLabel jlRecMsgt = new JLabel("��Ϣ����");
    private JTextArea jtSendt = new JTextArea();
    private JTextArea jtRecMsgt = new JTextArea();
    private JButton jbRecRett = new JButton(new ImageIcon("image\\ret.PNG"));
    private JButton jbRec1t = new JButton("����");
    private JButton jbRec2t = new JButton("�༭");
    private JButton jbRec3t = new JButton("ɾ��");
    private JPopupMenu jpmt = null;
    // ///////////////////////////

    // ͨѶ¼UI
    private JLabel jlAdTit = new JLabel("ͨѶ¼");
    private JButton jbAdAdd = new JButton("���");
    private JButton jbAdCancel = new JButton("����");
    private DefaultTableModel defaultModelAd = null;
    private JScrollPane sAd = null;
    private String[][] dataAd = null;
    private JTable jtAd = null;
    private JPopupMenu jpmAd = null;
    //

    // �޸�����
    private JLabel jlOldPwd = new JLabel("ԭ����");
    private JLabel jlNewPwd1 = new JLabel("������");
    private JLabel jlNewPwd2 = new JLabel("ȷ��������");
    private JPasswordField jtOldPwd = new JPasswordField();
    private JPasswordField jtNewPwd1 = new JPasswordField();
    private JPasswordField jtNewPwd2 = new JPasswordField();
    private JButton jbPwdOk = new JButton("ȷ��");
    private JButton jbPwdCanc = new JButton("����");
    //

    // ������button begin
    private JButton jb1 = new JButton(new ImageIcon("image\\д����.PNG"));
    private JButton jb2 = new JButton(new ImageIcon("image\\�ռ���.PNG"));
    private JButton jb3 = new JButton(new ImageIcon("image\\������.PNG"));
    private JButton jb4 = new JButton(new ImageIcon("image\\�ݸ���.PNG"));
    private JButton jb5 = new JButton(new ImageIcon("image\\ͨѶ¼.PNG"));
    private JButton jb6 = new JButton(new ImageIcon("image\\������.PNG"));
    private JButton jbExit = new JButton(new ImageIcon("image\\�ػ�.PNG"));
    // button end

    /*
     * �������ã����췽�� 
     * ���������TCP���ӣ��û���������Ϣ�� 
     * ���ز�������
     */
    public MainUI(TCPClient tc, String name, int num) {
        this.tc = tc;
        this.name = name;
        this.num = num;
        init();
    }

    /*
     * �������ã���ʼ���������������ý��������
     * �����������
     * ���ز�������
     */
    void init() {
        this.setLayout(null);
        setTitle("���Ž���ƽ̨");
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
     * �������ã����ý����Ͽؼ���λ�õȲ��� 
     * �����������
     * ���ز�������
     */
    private void setUI() {
        
        // ��ȡ�����ļ� begin
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
        // ��ȡ�����ļ� end
        displayArea = new JLabel();
        displayArea.setLocation(10, 10);
        displayArea.setSize(500, 100);
        displayArea.setFont(new Font("����", Font.BOLD, 26));
        displayArea.setForeground(new Color(220, 105, 30));
        displayArea.setLocation(80, 60);
        this.add(displayArea);

        jlNum = new JLabel();
        jlNum.setSize(150, 100);
        jlNum.setFont(new Font("����", Font.BOLD, 12));
        jlNum.setForeground(new Color(250, 105, 100));
        jlNum.setLocation(30, 130);
        this.add(jlNum);
        configMsg();
        configTimeArea();

        // ����label ��������ɫ
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
        jlWeath.setFont(new Font("����", Font.BOLD, 22));
        jlWeath.setSize(100, 100);
        jlWeath.setLocation(199, 128);
        if (weatherFlag == 0) {
            jlWeath.setText("δ֪");
            jlWeath.setLocation(199, 128);
            weather = new JLabel(new ImageIcon("image\\δ֪.PNG"));
        } else if (weatherFlag == 1) {
            jlWeath.setText("��");
            jlWeath.setLocation(210, 128);
            weather = new JLabel(new ImageIcon("image\\��.PNG"));
        } else if (weatherFlag == 2) {
            jlWeath.setText("��");
            jlWeath.setLocation(210, 128);
            weather = new JLabel(new ImageIcon("image\\��.PNG"));
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
        jb1.setName("д��Ϣ");
        jb2.setName("�ռ���");
        jb3.setName("������");
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
            public void mousePressed(MouseEvent e) { // ����mousePressed
                origin.x = e.getX(); // ����갴�µ�ʱ���ô��ڵ�ǰ��λ��
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // �϶���mouseDragged
                Point p = getLocation(); // ������϶�ʱ��ȡ���ڵ�ǰλ��
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                        - origin.y);
            }
        });
        // д��Ϣ
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeAl();
                paintWrite();
                repaint();
            }
        });
        // �ռ���
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintReceMain();
                repaint();
            }
        });
        // ������
        jb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeAl();
                paintSendMain();
                repaint();
            }
        });
        // �ݸ���
        jb4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintTmpMain();
                repaint();
            }
        });
        // ͨѶ¼
        jb5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintAd();
                repaint();
            }
        });
        // �޸�����
        jb6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAl();
                paintPwd();
                repaint();
            }
        });
        //�˳���ť
        jbExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "ȷ��Ҫ�ػ���",
                        "��ʾ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    tc.sendMsg("CMD002");
                }
            }
        });
        //���ð�ť
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

        // д����
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
        jtMsg.setLineWrap(true); // �����Զ����й���
        jtMsg.setWrapStyleWord(true); // ������в����ֹ���
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
                    JOptionPane.showMessageDialog(getThis(), "�������ݲ���Ϊ�գ�", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strRec.length() == 0) {
                    JOptionPane.showMessageDialog(getThis(), "�ռ��˲���Ϊ�գ�", "����",
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
                                "�ռ��˺�������Ƿ��ַ���", "����",
                                JOptionPane.ERROR_MESSAGE);
                        jtRec.setText("");
                    } else if (pCheck(strRec) == 0) {
                        JOptionPane.showMessageDialog(getThis(),
                                "�ռ��˺��������", "����",
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
                            "�Ƿ񱣴�Ϊ�ݸ壿", "��ʾ", JOptionPane.YES_NO_OPTION);
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
                    JOptionPane.showMessageDialog(getThis(), "�����������ݳ������ƣ�",
                            "����", JOptionPane.ERROR_MESSAGE);
                }
            }

            public void keyPressed(KeyEvent arg0) {

            }
        });
        //

        // �ռ���
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
        jtRecMsg.setLineWrap(true); // �����Զ����й���
        jtRecMsg.setWrapStyleWord(true); // ������в����ֹ���
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
                int n = JOptionPane.showConfirmDialog(getThis(), "ȷ��ɾ���������ţ�",
                        "��ʾ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsg.getText()
                            + "' and type = 0 and time = '"
                            + jtSendTime.getText() + "'";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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

        // ������
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
        jtRecMsgs.setLineWrap(true); // �����Զ����й���
        jtRecMsgs.setWrapStyleWord(true); // ������в����ֹ���
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
        // ת��
        jbRec2s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jtMsg.setText(jtRecMsgs.getText());
                removeSendShow();
                paintWrite();
                revalidate();
                repaint();
            }
        });
        // ɾ��
        jbRec3s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "ȷ��ɾ���������ţ�",
                        "��ʾ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsgs.getText()
                            + "' and type = 1 and time = '"
                            + jtSendsTime.getText() + "'";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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

        // �ݸ���
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
        jtRecMsgt.setLineWrap(true); // �����Զ����й���
        jtRecMsgt.setWrapStyleWord(true); // ������в����ֹ���
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
        // �༭
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
        // ɾ��
        jbRec3t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int n = JOptionPane.showConfirmDialog(getThis(), "ȷ��ɾ���������ţ�",
                        "��ʾ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    String sql = "delete from " + "\"" + name + "\""
                            + "where message = '" + jtRecMsgt.getText()
                            + "' and type = 2";
                    MessageDao.getInstance().doQuery(sql);
                    JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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

        // ͨѶ¼
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
        // �޸�����
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
        jlOldPwd.setFont(new Font("����", Font.BOLD, 14));
        jlNewPwd1.setFont(new Font("����", Font.BOLD, 14));
        jlNewPwd2.setFont(new Font("����", Font.BOLD, 14));
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
                    JOptionPane.showMessageDialog(getThis(), "�����벻һ�£����������룡",
                            "����", JOptionPane.ERROR_MESSAGE);
                } else {
                    String sender = "CMD004" + name + str + "&" + str2;
                    tc.sendMsg(sender);
                }
            }
        });
    }
    
    /*
     * �������ã��Ƴ�����������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã����д��Ϣ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ�д��Ϣ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã���ӷ�������ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ���������ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã���ӷ���������������пؼ�
     * �����������
     * ���ز�������
     */
    private void paintSendMain() {
        String[] tag = { "�ռ���", "��Ϣ����" };
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
        JMenuItem jm1 = new JMenuItem("����Ϣ ");
        JMenuItem jm3 = new JMenuItem("ת����Ϣ ");
        JMenuItem jm4 = new JMenuItem("ɾ����Ϣ");
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
                            "ȷ��ɾ���������ţ�", "��ʾ", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1
                                + "' and type = 1 and time = '" + adatas[row]
                                + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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
     * �������ã��Ƴ�����������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã���Ӳݸ�����ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ��ݸ�����ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã���Ӳݸ�������������пؼ�
     * �����������
     * ���ز�������
     */
    private void paintTmpMain() {
        String[] tag = { "�ռ���", "��Ϣ����" };
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
        JMenuItem jm1 = new JMenuItem("����Ϣ ");
        JMenuItem jm3 = new JMenuItem("�༭��Ϣ ");
        JMenuItem jm4 = new JMenuItem("ɾ����Ϣ");
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
        // ��
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
        // �༭
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
        // ɾ��
        jm4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = tableRect.getSelectedRow();
                if (row != -1) {
                    String str1 = new String(datat[row][1]);
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "ȷ��ɾ���������ţ�", "��ʾ", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1 + "' and type = 2";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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
     * �������ã��Ƴ��ݸ�������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã�����ռ�����ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ��ռ�����ʾ��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ��ռ�������������пؼ�
     * �����������
     * ���ز�������
     */
    private void paintReceMain() {
        String[] tag = { "������", "��Ϣ����" };
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
        JMenuItem jm1 = new JMenuItem("����Ϣ ");
        JMenuItem jm2 = new JMenuItem("�ظ���Ϣ ");
        JMenuItem jm3 = new JMenuItem("ת����Ϣ ");
        JMenuItem jm4 = new JMenuItem("ɾ����Ϣ");
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
                            "ȷ��ɾ���������ţ�", "��ʾ", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "\""
                                + "where message = '" + str1
                                + "' and type = 0 and time = '" + adata[row]
                                + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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
     * �������ã��Ƴ��ռ�������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã����ͨѶ¼��������пؼ�
     * �����������
     * ���ز�������
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
        String[] nameAd = { "����", "�ֻ�����" };

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
        JMenuItem jm1 = new JMenuItem("�޸ļ�¼ ");
        JMenuItem jm3 = new JMenuItem("ɾ����¼ ");
        JMenuItem jm4 = new JMenuItem("���Ͷ���");
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
                            "ȷ��ɾ������ϵ�ˣ�", "��ʾ", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String sql = "delete from " + "\"" + name + "a\""
                                + "where name = '" + str1 + "' and phoneno = '"
                                + str2 + "'";
                        MessageDao.getInstance().doQuery(sql);
                        JOptionPane.showMessageDialog(getThis(), "ɾ���ɹ�");
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
     * �������ã��Ƴ�ͨѶ¼��������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã�����޸������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã��Ƴ��޸������������пؼ�
     * �����������
     * ���ز�������
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
     * �������ã���֤�ռ��˵ĺϷ���
     * ����������ռ����ֻ���
     * ���ز�����������0Ϊ�ֻ��Ź�����1Ϊ�����������ַ���2Ϊ�Ϸ���
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
     * �������ã�����ʱ����ʾ����
     * �����������
     * ���ز�������
     */
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
    }

   /*
    * ʱ����ʾ��TimerTask
    */
    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                DEFAULT_TIME_FORMAT);

        public void run() {
            time = dateFormatter.format(Calendar.getInstance().getTime());
            displayArea.setText(time);
            int count = MessageDao.getInstance().selectNum(name);
            if (count > 0) {
                jlNum.setText("���� " + count + " ��δ����Ϣ");
            } else {
                jlNum.setText("");
            }
        }
    }
    /*
     * �������ã����ڶ���
     * �����������
     * ���ز�������
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
     * �������ã����ü�ʱ���ն���
     * �����������
     * ���ز�������
     */
    private void configMsg() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new GetMsg(), new Date(), 2000);
    }

    /*
     * ��ʱ���ն��ŵ�TimerTask
     */
    protected class GetMsg extends TimerTask {
        public void run() {
            String str = tc.getMsg();
            if (str.charAt(5) == '3') {
                MessageDao.getInstance().insertMsg(name,
                        str.substring(6, 17).trim(),
                        str.substring(17, str.length()), "");
                if (str.substring(6, 17).trim().equals("002")) {
                    if (str.substring(17, str.length()).indexOf("��") != -1) {
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
                        jlWeath.setText("��");
                        jlWeath.setLocation(210, 128);
                        weather = new JLabel(new ImageIcon("image\\��.PNG"));
                        weather.setSize(234, 160);
                        weather.setLocation(18, 40);
                        add(weather);
                        add(jp);
                        weather.setVisible(true);
                        jp.setVisible(true);
                        repaint();
                    } else if(str.substring(17, str.length()).indexOf("��") != -1) {
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
                        jlWeath.setText("��");
                        jlWeath.setLocation(210, 128);
                        weather = new JLabel(new ImageIcon("image\\��.PNG"));
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
                    JOptionPane.showMessageDialog(getThis(), "�յ�����Ϣ");
                }
            } else if (str.charAt(5) == '4') {
                if (str.charAt(6) == '1') {
                    JOptionPane.showMessageDialog(getThis(), "�޸ĳɹ��������µ�¼");
                    tc.sendMsg("CMD002");
                } else if (str.charAt(6) == '0') {
                    JOptionPane.showMessageDialog(getThis(), "�޸�ʧ�ܣ�ԭ�������",
                            "����", JOptionPane.ERROR_MESSAGE);
                }
            } else if (str.charAt(5) == '2') {
                tc.close();
                System.exit(0);
            } else if (str.charAt(5) == '5') {
                char ch = str.charAt(6);
                if (ch == '0') {
                    JOptionPane.showMessageDialog(getThis(), "���ŷ���ʧ�ܣ������û������ڣ�",
                            "����", JOptionPane.ERROR_MESSAGE);
                } else if (ch == '1') {
                    JOptionPane.showMessageDialog(getThis(), "���ŷ���ʧ�ܣ��������㣡",
                            "����", JOptionPane.ERROR_MESSAGE);
                } else if (ch == '2') {
                    JOptionPane.showMessageDialog(getThis(), "���ŷ��ͳɹ���");
                }
            }
        }
    }
    
    /*
     * �������ã��õ���ǰ����
     * �����������
     * ���ز�������ǰ����
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
            JOptionPane.showMessageDialog(this, "�յ� " + num + " ������Ϣ��");
        }
    }
}