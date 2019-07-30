package Operation;

import DataBase.DataCheck;
import Message.Nuture;
import Message.SpotMessage;
import Use.UserFrame;
import Utils.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class New {
    private double XPOS;
    private double YPOS;
    private UserFrame.beijing bored;
    private ArrayList<SpotMessage> arrayList = null;

    public New(double XPOS, double YPOS, ArrayList<SpotMessage> arrayList, UserFrame.beijing bored) {
        this.bored = bored;
        this.arrayList = arrayList;
        this.XPOS = XPOS;
        this.YPOS = YPOS;
    }

    public void init() {
        JFrame jFrame = new JFrame();

        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel jLabel = new JLabel(background);
        jLabel.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
        int check = JOptionPane.showConfirmDialog(jFrame, "确认在鼠标点击处增加建筑?");
        if (check == JOptionPane.YES_OPTION) {
            //JFrame jFrame = new JFrame();
            jFrame.setBounds(0, 0, 300, 430);
            jFrame.setLayout(null);
            JLabel jLabel1 = new JLabel("建筑名称:");
            jFrame.add(jLabel1);
            jLabel1.setBounds(100, 10, 100, 30);
            JTextField jf1 = new JTextField(20);
            jFrame.add(jf1);
            jf1.setBounds(100, 40, 100, 30);
            jf1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (jf1.getText().length() <= 8 ) {

                    }
                    else {
                        e.consume();
                    }
                }
            });

            JLabel jLabel2 = new JLabel("联系电话:");
            jLabel2.setBounds(100, 80, 100, 30);
            jFrame.add(jLabel2);
            JTextField jf2 = new JTextField(20);
            jFrame.add(jf2);
            jf2.setBounds(100, 110, 100, 30);
            jf2.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (jf2.getText().length() <= 20 ) {

                    }
                    else {
                        e.consume();
                    }
                }
            });
            jf2.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    int keyChar = e.getKeyChar();
                    if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                        e.consume();
                    }
                }
            });

            JLabel jLabel4 = new JLabel("建筑属性:");
            jLabel4.setBounds(100, 150, 100, 30);
            jFrame.add(jLabel4);
            Vector<String> vector = new Vector<>();
            ArrayList<String> list = new Nuture().out();
            for (String item : list) {
                vector.addElement(item);
            }
            JComboBox<String> jComboBox = new JComboBox<>(vector);
            jFrame.add(jComboBox);
            jComboBox.setBounds(100, 180, 100, 30);
//            JTextField jf4 = new JTextField(20);
//            jFrame.add(jf4);
//            jf4.setBounds(10, 180, 100, 30);

            JLabel jLabel3 = new JLabel("建筑介绍:");
            jLabel3.setBounds(100, 240, 100, 30);
            jFrame.add(jLabel3);
            JTextField jf3 = new JTextField(20);
            jFrame.add(jf3);
            jf3.setBounds(100, 270, 100, 30);
            jf3.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (jf3.getText().length() <= 8 ) {

                    }
                    else {
                        e.consume();
                    }
                }
            });

            JButton jButton = new JButton("确定");
            jFrame.add(jButton);
            jButton.setBounds(100, 350, 100, 30);
            jFrame.setLocationRelativeTo(jFrame.getOwner());
            jFrame.setVisible(true);
            jButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jf1.getText().length() <= 8&&jf2.getText().length() <= 20 && jf3.getText().length() <=8) {
                        if (!new DataCheck().checkSpot(jf1.getText())) {
                            try {
                                Connection conn = DbUtils.getConnection();
                                try (PreparedStatement preparedStatement = conn.prepareStatement(
                                        "insert into spot values (?,?,?,?,?,?)"
                                )) {
                                    preparedStatement.setDouble(1, XPOS);
                                    preparedStatement.setDouble(2, YPOS);
                                    preparedStatement.setString(3, jf2.getText());
                                    preparedStatement.setString(4, jComboBox.getSelectedItem().toString());
                                    preparedStatement.setString(5, jf1.getText());
                                    preparedStatement.setString(6, jf3.getText());
                                    SpotMessage spotMessage = new SpotMessage();
                                    spotMessage.setX(XPOS);
                                    spotMessage.setY(YPOS);
                                    spotMessage.setName(jf1.getText());
                                    spotMessage.setPhonenumber(jf2.getText());
                                    spotMessage.setNuture(jComboBox.getSelectedItem().toString());
                                    spotMessage.setIntroduction(jf3.getText());
                                    try {
                                        int rs = preparedStatement.executeUpdate();
                                        if (rs == 1) {
                                            JOptionPane.showMessageDialog(jFrame, "添加成功!");
                                            arrayList.add(spotMessage);
                                            bored.repaint();
                                            jFrame.setVisible(false);
                                            //jFrame.setVisible(false);
                                        }
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                conn.close();
                                //preparedStatement.setString(3,spotMessage.getIntroduction());
                            }  catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(jFrame,"该建筑已经存在,请重新输入");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(jFrame,"输入不合法,请重新输入");
                    }
                }
            });
        }
    }
}
