/*
 * 服务器界面类
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
    private JButton jbOpen = new JButton("开启服务器");
    private JButton jbClose = new JButton("关闭服务器");
    private JButton jbOnline = new JButton("To在线用户");
    private JButton jbAll = new JButton("To所有用户");
    private StringBuffer str;
    private JLabel displayArea = null;
    private String DEFAULT_TIME_FORMAT = "服务器时间：HH:mm:ss  当前连接数：";
    private String time;
    private int ONE_SECOND = 1000;
    static ArrayList<TCPServerUtils> al = new ArrayList<TCPServerUtils>();
    private Connection conn = null;
    private ServerSocket ss = null;
    private JLabel jlMsg = new JLabel("发送系统信息");
    private JTextArea jtMsg = new JTextArea();
    private JScrollPane jsMsg = new JScrollPane();
    private JButton jbDetail = new JButton("导出");
    private JLabel jlDetail = new JLabel("导出信息记录");
    private JLabel jlPhoneno = new JLabel("手机号码");
    private JLabel jlStart = new JLabel("起始时间");
    private JLabel jlEnd = new JLabel("结束时间");
    private JTextField jtPhoneno = new JTextField();

    private DatePicker datepick;
    private DatePicker datepick1;
    private static final String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
    private Font font = new Font("Times New Roman", Font.BOLD, 14);
    private Dimension dimension = new Dimension(110, 30);
    
    /*
     * 方法作用：构造方法 
     * 传入参数：无 
     * 返回参数：无
     */
    public ServerUI() {
        init();
        str = new StringBuffer();
    }

    /*
     * 方法作用：初始化界面参数 
     * 传入参数：无 
     * 返回参数：无
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
     * 方法作用：设置界面控件位置等参数 
     * 传入参数：无 
     * 返回参数：无
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
        jtMsg.setLineWrap(true); // 激活自动换行功能
        jtMsg.setWrapStyleWord(true); // 激活断行不断字功能
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
        displayArea.setFont(new Font("宋体", Font.BOLD, 16));
        jlMsg.setFont(new Font("宋体", Font.BOLD, 14));
        jlDetail.setFont(new Font("宋体", Font.BOLD, 14));
        //日历控件 begin
        datepick = new DatePicker(null, DefaultFormat, font, dimension);
        datepick.setBounds(165, 510, 150, 30);
        datepick.setLocale(Locale.CHINA);
        datepick.setTimePanleVisible(true);
        datepick1 = new DatePicker(null, DefaultFormat, font, dimension);
        datepick1.setBounds(165, 550, 150, 30);
        datepick1.setLocale(Locale.CHINA);
        datepick1.setTimePanleVisible(true);
        //日历控件 end
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
                                    "服务器成功开启运行！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(getThis(),
                                "服务器已经开启，请不要重复启动！");
                    }
                } catch (HeadlessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getThis(), "服务器开启失败！", "错误",
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
                        JOptionPane.showMessageDialog(getThis(), "服务器没有开启！");
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
                    JOptionPane.showMessageDialog(getThis(), "短信内容为空！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strMsg.length() > 200) {
                    JOptionPane.showMessageDialog(getThis(), "短信内容过长！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定发送信息？所有在线用户将收到本条系统信息", "提示",
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
                    JOptionPane.showMessageDialog(getThis(), "短信内容为空！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else if (strMsg.length() > 200) {
                    JOptionPane.showMessageDialog(getThis(), "短信内容过长！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int n = JOptionPane.showConfirmDialog(getThis(),
                            "确定发送信息？所有用户将收到本条系统信息", "提示",
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
                    JOptionPane.showMessageDialog(getThis(), "手机号码长度非法！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    jtPhoneno.setText("");
                } else if (pCheck(phoneno) == 1) {
                    JOptionPane.showMessageDialog(getThis(), "手机号码包含非法字符", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    jtPhoneno.setText("");
                } else if (pCheck(phoneno) == 2) {
                    ArrayList<MessageBean> al = MessageDao.getInstance()
                            .selectMsg1(phoneno, start, end);
                    HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
                    HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
                    workbook.setSheetName(0, "发送记录",
                            HSSFWorkbook.ENCODING_UTF_16);
                    HSSFRow row = sheet.createRow((short) 0);
                    HSSFCell cell;
                    cell = row.createCell((short) (0));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("收件人");
                    cell = row.createCell((short) (1));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("发送时间");
                    cell = row.createCell((short) (2));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("信息长度");
                    cell = row.createCell((short) (3));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue("发送状态");
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
                            cell.setCellValue("发送成功");
                        } else {
                            cell.setCellValue("发送失败");
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
                            JOptionPane.showMessageDialog(getThis(), "导出成功！");
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
     * 接收客户端连接TimeerTask
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
                JOptionPane.showMessageDialog(getThis(), "服务器已关闭！");
            }
        }
    }

    /*
     * 方法作用：得到当前窗体 
     * 传入参数：无 
     * 返回参数：当前窗体
     */
    private ServerUI getThis() {
        return this;
    }

    /*
     * 方法作用：服务器状态监视区字符串追加 
     * 传入参数：无 
     * 返回参数：无
     */
    public void appedText(String str) {
        this.str.append(str);
        jt.setText(this.str.toString());
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
     * 时间显示 TimerTask
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
     * 方法作用：设置连接数 
     * 传入参数：无 
     * 返回参数：无
     */
    public void setConnectNum(int num) {
        this.connectNum = num;
    }

    /*
     * 方法作用：得到连接数 
     * 传入参数：无 
     * 返回参数：无
     */
    public int getConnectNum() {
        return this.connectNum;
    }
    
    /*
     * 方法作用：检测输入的手机号是否合法 
     * 传入参数：输入的手机号 
     * 返回参数：整数，0为长度非法，1为非法字符，2为合法
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
