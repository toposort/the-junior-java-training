/*
 * ������������
 */
package com.neusoft.service;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neusoft.beans.CustomerBean;
import com.neusoft.beans.MessageBean;
import com.neusoft.dao.CustomerDao;
import com.neusoft.dao.MessageDao;
import com.neusoft.utils.DBUtils;
import com.neusoft.utils.TCPServerUtils;

import com.eltima.components.ui.DatePicker;

public class ServerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private int connectNum = 0;
    private JTextArea jt = new JTextArea();
    private JButton jbOpen = new JButton("����������");
    private JButton jbClose = new JButton("�رշ�����");
    private JButton jbOnline = new JButton("To�����û�");
    private JButton jbAll = new JButton("To�����û�");
    private StringBuffer str;
    private JLabel displayArea = null;
    private String DEFAULT_TIME_FORMAT = "������ʱ�䣺HH:mm:ss  ��ǰ��������";
    private String time;
    private int ONE_SECOND = 1000;
    static ArrayList<TCPServerUtils> al = new ArrayList<TCPServerUtils>();
    private Connection conn = null;
    private ServerSocket ss = null;
    private JLabel jlMsg = new JLabel("����ϵͳ��Ϣ");
    private JTextArea jtMsg = new JTextArea();
    private JScrollPane jsMsg = new JScrollPane();
    private JButton jbDetail = new JButton("����");
    private JLabel jlDetail = new JLabel("������Ϣ��¼");
    private JLabel jlPhoneno = new JLabel("�ֻ�����");
    private JLabel jlStart = new JLabel("��ʼʱ��");
    private JLabel jlEnd = new JLabel("����ʱ��");
    private JTextField jtPhoneno = new JTextField();

    private DatePicker datepick;
    private DatePicker datepick1;
    private static final String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
    private Font font = new Font("Times New Roman", Font.BOLD, 14);
    private Dimension dimension = new Dimension(110, 30);
    
    /*
     * �������ã����췽�� 
     * ����������� 
     * ���ز�������
     */
    public ServerUI() {
        init();
        str = new StringBuffer();
    }

    /*
     * �������ã���ʼ��������� 
     * ����������� 
     * ���ز�������
     */
    void init() {
        this.setLayout(null);
        setTitle("Server Manger");
        setLocation(220, 60);
        setSize(1000, 670);
        setUI();
        this.setResizable(false);
    }

    /*
     * �������ã����ý���ؼ�λ�õȲ��� 
     * ����������� 
     * ���ز�������
     */
    private void setUI() {
        jbOpen.setSize(100, 50);
        jbOpen.setLocation(570, 95);
        jbClose.setSize(100, 50);
        jbClose.setLocation(720, 95);
        jbOnline.setSize(100, 50);
        jbOnline.setLocation(570, 535);
        jbAll.setSize(100, 50);
        jbAll.setLocation(720, 535);
        jlMsg.setSize(100, 30);
        jlMsg.setLocation(490, 170);
        jtMsg.setLineWrap(true); // �����Զ����й���
        jtMsg.setWrapStyleWord(true); // ������в����ֹ���
        jlDetail.setSize(100, 30);
        jlDetail.setLocation(100, 430);
        jlPhoneno.setSize(100, 30);
        jlPhoneno.setLocation(100, 470);
        jlStart.setSize(100, 30);
        jlStart.setLocation(100, 510);
        jlEnd.setSize(100, 30);
        jlEnd.setLocation(100, 550);
        jtPhoneno.setSize(150, 30);
        jtPhoneno.setLocation(166, 470);
        jtPhoneno.setFont(font);
        jbDetail.setSize(60, 60);
        jbDetail.setLocation(340, 490);
        displayArea = new JLabel();
        displayArea.setLocation(100, 0);
        displayArea.setSize(500, 100);
        displayArea.setFont(new Font("����", Font.BOLD, 16));
        jlMsg.setFont(new Font("����", Font.BOLD, 14));
        jlDetail.setFont(new Font("����", Font.BOLD, 14));
        //�����ؼ� begin
        datepick = new DatePicker(null, DefaultFormat, font, dimension);
        datepick.setBounds(165, 510, 150, 30);
        datepick.setLocale(Locale.CHINA);
        datepick.setTimePanleVisible(true);
        datepick1 = new DatePicker(null, DefaultFormat, font, dimension);
        datepick1.setBounds(165, 550, 150, 30);
        datepick1.setLocale(Locale.CHINA);
        datepick1.setTimePanleVisible(true);
        //�����ؼ� end
        this.add(displayArea);
        configTimeArea();
        jt.setEditable(false);
        JScrollPane scrollpane = new JScrollPane(jt);
        scrollpane.setSize(310, 330);
        scrollpane.setLocation(100, 95);
        jbOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (ss == null || ss.isClosed()) {
                        conn = new DBUtils().getConnection();
                        if (conn != null) {
                            ss = new ServerSocket(18888);
                            getCon gc = new getCon();
                            Thread tc = new Thread(gc);
                            tc.start();
                            JOptionPane.showMessageDialog(getThis(),
                                    "�������ɹ��������У�");
                        }
                    } else {
                        JOptionPane.showMessageDialog(getThis(),
                                "�������Ѿ��������벻Ҫ�ظ�������");
                    }
                } catch (HeadlessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getThis(), "����������ʧ�ܣ�", "����",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        jbClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (ss == null || ss.isClosed()) {
                        JOptionPane.showMessageDialog(getThis(), "������û�п�����");
                        return;
                    }
                    ss.close();
                    for (int i = 0; i < al.size(); i++) {
                        TCPServerUtils temp = al.get(i);
                        temp.closeAl();
                    }
                } catch (IOException e) {

                }
            }
        });
        jbOnline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String strMsg = jtMsg.getText();
                if (strMsg.length() == 0) {
                    JOptionPane.showMessageDialog(getThis(), "��������Ϊ�գ�", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strMsg.length() > 200) {
                    JOptionPane.showMessageDialog(getThis(), "�������ݹ�����", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "ȷ��������Ϣ�����������û����յ�����ϵͳ��Ϣ", "��ʾ",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        @SuppressWarnings("static-access")
                        MessageDao md = new MessageDao().getInstance();
                        String src = "10086      ";
                        for (int i = 0; i < al.size(); i++) {
                            TCPServerUtils temp = al.get(i);
                            String dst = temp.getName();
                            temp.sendMessage("CMD003" + src + strMsg);
                            md.insertMsg(src, dst, strMsg, 1, 2);
                        }
                        jtMsg.setText("");
                    }
                }
            }
        });
        jbAll.addActionListener(new ActionListener() {
            @SuppressWarnings("static-access")
            public void actionPerformed(ActionEvent arg0) {
                String strMsg = jtMsg.getText();
                if (strMsg.length() == 0) {
                    JOptionPane.showMessageDialog(getThis(), "��������Ϊ�գ�", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strMsg.length() > 200) {
                    JOptionPane.showMessageDialog(getThis(), "�������ݹ�����", "����",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "ȷ��������Ϣ�������û����յ�����ϵͳ��Ϣ", "��ʾ",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        MessageDao md = new MessageDao().getInstance();
                        CustomerDao cd = new CustomerDao().getInstance();
                        ArrayList<CustomerBean> alc = cd.selectCustom();
                        String src = "10086      ";
                        for (int i = 0; i < alc.size(); i++) {
                            CustomerBean cb = alc.get(i);
                            String dst = cb.getPhoneno();
                            md.insertMsg(src, dst, strMsg, 0, -1);
                        }
                        for (int i = 0; i < al.size(); i++) {
                            TCPServerUtils temp = al.get(i);
                            String dst = temp.getName();
                            temp.sendMessage("CMD003" + src + strMsg);
                            md.updateMsg1(dst, strMsg);
                        }
                        jtMsg.setText("");
                    }
                }
            }
        });
        jbDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String phoneno = jtPhoneno.getText();
                String start = datepick.getText();
                String end = datepick1.getText();
                System.out.println(phoneno);
                if (pCheck(phoneno) == 0) {
                    JOptionPane.showMessageDialog(getThis(), "�ֻ����볤�ȷǷ���", "����",
                            JOptionPane.ERROR_MESSAGE);
                    jtPhoneno.setText("");
                } else if (pCheck(phoneno) == 1) {
                    JOptionPane.showMessageDialog(getThis(), "�ֻ���������Ƿ��ַ�", "����",
                            JOptionPane.ERROR_MESSAGE);
                    jtPhoneno.setText("");
                } else if (pCheck(phoneno) == 2) {
                    ArrayList<MessageBean> al = MessageDao.getInstance()
                            .selectMsg1(phoneno, start, end);
                    HSSFWorkbook workbook = new HSSFWorkbook(); // ��������������
                    HSSFSheet sheet = workbook.createSheet(); // �������������
                    workbook.setSheetName(0, "���ͼ�¼",
                            HSSFWorkbook.ENCODING_UTF_16);
                    HSSFRow row = sheet.createRow((short) 0);
                    HSSFCell cell;
                    cell = row.createCell((short) (0));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("�ռ���");
                    cell = row.createCell((short) (1));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("����ʱ��");
                    cell = row.createCell((short) (2));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("��Ϣ����");
                    cell = row.createCell((short) (3));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("����״̬");
                    int iRow = 1;
                    for (int i = 0; i < al.size(); i++) {
                        row = sheet.createRow((short) iRow);
                        cell = row.createCell((short) (0));
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell.setCellValue(al.get(i).getRec());
                        cell = row.createCell((short) (1));
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell.setCellValue(al.get(i).getTime());
                        cell = row.createCell((short) (2));
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell.setCellValue(al.get(i).getMsg().length());
                        cell = row.createCell((short) (3));
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        if (al.get(i).getStaus() == 2) {
                            cell.setCellValue("���ͳɹ�");
                        } else {
                            cell.setCellValue("����ʧ��");
                        }
                        iRow++;
                    }
                    JFileChooser jf = new JFileChooser();
                    jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG
                            | JFileChooser.DIRECTORIES_ONLY);
                    jf.showDialog(null, null);
                    File fi = jf.getSelectedFile();
                    if (fi != null) {
                        String xlsName = fi.getAbsolutePath() + "\\" + phoneno
                                + "result.xls";
                        try {
                            FileOutputStream fOut = new FileOutputStream(
                                    xlsName);
                            workbook.write(fOut);
                            fOut.flush();
                            fOut.close();
                            JOptionPane.showMessageDialog(getThis(), "�����ɹ���");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        jtPhoneno.setText("");
                    }
                }
            }
        });
        jsMsg = new JScrollPane(jtMsg);
        jsMsg.setSize(400, 310);
        jsMsg.setLocation(490, 210);
        this.add(jsMsg);
        this.add(jlMsg);
        this.add(jlDetail);
        this.add(jlPhoneno);
        this.add(jlStart);
        this.add(jlEnd);
        this.add(jtPhoneno);
        this.add(datepick);
        this.add(datepick1);
        this.add(scrollpane);
        this.add(jbOpen);
        this.add(jbClose);
        this.add(jbOnline);
        this.add(jbDetail);
        this.add(jbAll);
    }

    /*
     * ���տͻ�������TimeerTask
     */
    class getCon implements Runnable {
        public void run() {
            try {
                while (true) {
                    Socket s = ss.accept();
                    TCPServerUtils ser = new TCPServerUtils(s, getThis(), al);
                    Thread t = new Thread(ser);
                    t.start();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(getThis(), "�������ѹرգ�");
            }
        }
    }

    /*
     * �������ã��õ���ǰ���� 
     * ����������� 
     * ���ز�������ǰ����
     */
    private ServerUI getThis() {
        return this;
    }

    /*
     * �������ã�������״̬�������ַ���׷�� 
     * ����������� 
     * ���ز�������
     */
    public void appedText(String str) {
        this.str.append(str);
        jt.setText(this.str.toString());
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
     * ʱ����ʾ TimerTask
     */
    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                DEFAULT_TIME_FORMAT);

        public void run() {
            int num = TCPServerUtils.threadCheck();
            if (num == 0)
                al.clear();
            time = dateFormatter.format(Calendar.getInstance().getTime());
            displayArea.setText(time + num);
        }
    }

    public void showView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
     * �������ã����������� 
     * ����������� 
     * ���ز�������
     */
    public void setConnectNum(int num) {
        this.connectNum = num;
    }

    /*
     * �������ã��õ������� 
     * ����������� 
     * ���ز�������
     */
    public int getConnectNum() {
        return this.connectNum;
    }
    
    /*
     * �������ã����������ֻ����Ƿ�Ϸ� 
     * ���������������ֻ��� 
     * ���ز�����������0Ϊ���ȷǷ���1Ϊ�Ƿ��ַ���2Ϊ�Ϸ�
     */
    private int pCheck(String usr) {
        if(usr.length() != 11){
            return 0;
        }
        for(int i = 0; i < usr.length(); i++){
            if(!(usr.charAt(i)>='0' && usr.charAt(i)<='9')){
                return 1;
            }
        }
        return 2;
    }

    public static void main(String[] args) {
        ServerUI su = new ServerUI();
        su.showView();
    }
}
