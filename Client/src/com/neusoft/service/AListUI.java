/*
 * ͨѶ¼������
 */
package com.neusoft.service;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.neusoft.beans.AddressBean;
import com.neusoft.dao.AddressDao;

public class AListUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private JButton jbOk = new JButton("ȷ��");
    private JButton jbCancel = new JButton("ȡ��");
    private DefaultTableModel defaultModel = null;
    private JScrollPane s;
    private String[][] data = null;
    private JTable jtAlist = null;
    private JTextField jt = null;
    private String name;

    /*
     * �������ã����췽�� 
     * ������������������꣬�������ֻ�����򣬵�ǰ�û��� 
     * ���ز�������
     */
    public AListUI(int x, int y, JTextField jt, String name) {
        this.x = x;
        this.y = y;
        this.jt = jt;
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
        setTitle("ͨѶ¼");
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
        jbOk.setLocation(13, 195);
        jbCancel.setSize(60, 20);
        jbCancel.setLocation(95, 195);
        this.add(jbOk);
        this.add(jbCancel);
        int cnt = 0, num = 0;
        num = AddressDao.getInstance().selectAdNum(this.name);
        data = new String[num][2];
        ArrayList<AddressBean> al = AddressDao.getInstance()
                .selectAd(this.name);
        for (int i = 0; i < al.size(); i++) {
            data[cnt][0] = al.get(i).getName();
            data[cnt++][1] = al.get(i).getPhoneno();
        }
        String[] names = { "����", "�ֻ�����" };
        defaultModel = new DefaultTableModel(data, names) {
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
        jtAlist = new JTable(defaultModel);
        TableColumn firsetColumn = jtAlist.getColumnModel().getColumn(0);
        firsetColumn.setMaxWidth(49);
        firsetColumn.setMinWidth(49);
        for (int i = 0; i < jtAlist.getColumnCount(); i++) {
            jtAlist.getColumn(jtAlist.getColumnName(i)).setCellRenderer(tcr);
        }
        s = new JScrollPane(jtAlist);
        s.setSize(150, 170);
        s.setLocation(10, 13);
        this.add(s);
        JPanel jp = new JPanel();
        ImageIcon im = new ImageIcon("image\\1112.PNG");
        JLabel jl = new JLabel(im);
        jp.setSize(173, 240);
        jp.setLocation(0, -5);
        jp.add(jl);
        this.add(jp);
        jtAlist.getTableHeader().setReorderingAllowed(false);
        jtAlist.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    if (arg0.getClickCount() == 2) {
                        int row = jtAlist.getSelectedRow();
                        if (row != -1) {
                            jt.setText(data[row][1]);
                            dispose();
                        }
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent arg0) {
                
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
        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jbOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = jtAlist.getSelectedRow();
                if (row != -1) {
                    jt.setText(data[row][1]);
                    dispose();
                }
            }
        });
    }

    public void showView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}