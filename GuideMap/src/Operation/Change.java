package Operation;

import Message.Nuture;
import Message.RoadMessage;
import Message.SpotMessage;
import Utils.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Change {
    private SpotMessage spotMessage;
    private JPanel bored;
    private ArrayList<SpotMessage> arrayList = new ArrayList<>();
    private ArrayList<RoadMessage> roadMessageArrayList = new ArrayList<>();

    public Change(SpotMessage spotMessage, ArrayList<SpotMessage> arrayList, ArrayList<RoadMessage> roadMessageArrayList,
                  JPanel bored) {
        this.bored = bored;
        this.arrayList = arrayList;
        this.spotMessage = spotMessage;
        this.roadMessageArrayList = roadMessageArrayList;
    }

    public void init() {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(0, 0, 300, 400);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel jLabel = new JLabel(background);
        jLabel.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
        jFrame.setLayout(null);
        JLabel jLabel1 = new JLabel("建筑名称:");
        jFrame.add(jLabel1);
        jLabel1.setBounds(100, 10, 100, 30);
        JTextField jf1 = new JTextField(20);
        jFrame.add(jf1);
        jf1.setBounds(100, 40, 100, 30);

        JLabel jLabel2 = new JLabel("联系电话:");
        jLabel2.setBounds(100, 80, 100, 30);
        jFrame.add(jLabel2);
        JTextField jf2 = new JTextField(20);
        jFrame.add(jf2);
        jf2.setBounds(100, 110, 100, 30);

        JLabel jLabel3 = new JLabel("建筑介绍:");
        jLabel3.setBounds(100, 150, 100, 30);
        jFrame.add(jLabel3);
        JTextField jf3 = new JTextField(20);
        jFrame.add(jf3);
        jf3.setBounds(100, 190, 100, 30);
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
        JLabel jLabel4 = new JLabel("建筑属性");
        jLabel4.setBounds(100, 230, 100, 30);
        jFrame.add(jLabel4);
        Vector<String> vector = new Vector<>();
        ArrayList<String> list = new Nuture().out();
        for (String item : list) {
            vector.addElement(item);
        }
        JComboBox<String> jComboBox = new JComboBox<>(vector);
        jFrame.add(jComboBox);
        jComboBox.setBounds(100, 260, 100, 30);
        String name = spotMessage.getName();
        jf1.setText(spotMessage.getName());
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
        jf2.setText(spotMessage.getPhonenumber());
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
        jf3.setText(spotMessage.getIntroduction());
        jComboBox.setSelectedItem(spotMessage.getNuture());
        JButton jButton = new JButton("确定");
        jFrame.getRootPane().setDefaultButton(jButton);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newname = jf1.getText();
                try {
                    Connection conn = DbUtils.getConnection();
                    conn.setAutoCommit(false);
                    try (PreparedStatement preparedStatement = conn.prepareStatement("update road set start = ? where start = ?")) {
                        preparedStatement.setString(1, newname);
                        preparedStatement.setString(2, name);
                        int rs = preparedStatement.executeUpdate();
                    } catch (Exception e2) {
                        conn.rollback();
                        e2.printStackTrace();
                    }
                    try (PreparedStatement preparedStatement = conn.prepareStatement("update road set end = ? where end = ?")) {
                        preparedStatement.setString(1, newname);
                        preparedStatement.setString(2, name);
                        int rs = preparedStatement.executeUpdate();
                        if (rs != 0) {
                            System.out.println("1");
                        }

                    } catch (Exception e3) {
                        conn.rollback();
                        e3.printStackTrace();
                    }
                    try (PreparedStatement preparedStatement = conn.prepareStatement(
                            "update spot set name = ?,introduction = ?,phonenumber = ?,nature = ? where name = ?"
                    )) {
                        preparedStatement.setString(1, newname);
                        preparedStatement.setString(2, jf3.getText());
                        preparedStatement.setString(3, jf2.getText());
                        preparedStatement.setString(4, jComboBox.getSelectedItem().toString());
                        preparedStatement.setString(5, name);
                        for (SpotMessage spotMessage : arrayList) {
                            if (spotMessage.getName().equals(name)) {
                                spotMessage.setName(newname);
                                spotMessage.setPhonenumber(jf2.getText());
                                spotMessage.setIntroduction(jf3.getText());
                                spotMessage.setNuture(jComboBox.getSelectedItem().toString());
                            }
                        }
                        try {
                            int rs = preparedStatement.executeUpdate();
                            if (rs == 1) {
                                JOptionPane.showMessageDialog(jFrame, "更改成功!");
                                bored.repaint();
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } catch (Exception e2) {
                        conn.rollback();
                        e2.printStackTrace();
                    }
                    conn.commit();
                    DbUtils.closeAll(conn);
                }  catch (Exception e1) {
                    e1.printStackTrace();
                }
                jFrame.setVisible(false);
            }
        });
        jFrame.add(jButton);
        jButton.setBounds(100, 310, 100, 30);
        jFrame.setVisible(true);
    }
}