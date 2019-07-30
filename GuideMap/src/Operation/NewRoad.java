package Operation;

import DataBase.DataCheck;
import Message.RoadMessage;
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
import java.util.ArrayList;

public class NewRoad {
    private SpotMessage start;
    private SpotMessage end;
    private UserFrame.beijing bored;
    private ArrayList<RoadMessage> arrayList = null;

    public NewRoad(SpotMessage start, SpotMessage end, ArrayList<RoadMessage> arrayList, UserFrame.beijing bored) {
        this.bored = bored;
        this.start = start;
        this.end = end;
        this.arrayList = arrayList;
    }

    public void init() {
        RoadMessage roadMessage = new RoadMessage();
        JFrame jFrame = new JFrame();
        jFrame.setBounds(0, 0, 280, 300);
        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel beijing = new JLabel(background);
        beijing.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(beijing, new Integer(Integer.MIN_VALUE));
        jFrame.setLayout(null);
        roadMessage.setStart_spot(start);
        roadMessage.setEnd_spot(end);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        JLabel jLabel = new JLabel("建立新路");
        JLabel jLabel1 = new JLabel("道路长度");
        JLabel jLabel2 = new JLabel("道路名称");
        JLabel jLabel3 = new JLabel("美化程度");
        JLabel jLabel4 = new JLabel("绿化程度");

        JTextField jt1 = new JTextField();
        JTextField jt2 = new JTextField();
        JTextField jt3 = new JTextField();
        JTextField jt4 = new JTextField();
        jt1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jt1.getText().length() <= 4 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jt2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jt2.getText().length() <= 5 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jt3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jt3.getText().length() <= 3 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jt4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jt4.getText().length() <= 3 ) {

                }
                else {
                    e.consume();
                }
            }
        });

        jFrame.add(jLabel);
        jFrame.add(jLabel1);
        jFrame.add(jLabel2);
        jFrame.add(jLabel3);
        jFrame.add(jLabel4);

        jFrame.add(jt1);
        jFrame.add(jt2);
        jFrame.add(jt3);
        jFrame.add(jt4);

        jt1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                    e.consume();
                }
            }
        });

        jt3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                    e.consume();
                }
            }
        });

        jt4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                    e.consume();
                }
            }
        });
        jLabel1.setBounds(40, 40, 50, 30);
        jLabel2.setBounds(40, 80, 50, 30);
        jLabel3.setBounds(40, 120, 50, 30);
        jLabel4.setBounds(40, 160, 50, 30);

        jt1.setBounds(90, 40, 100, 30);
        jt2.setBounds(90, 80, 100, 30);
        jt3.setBounds(90, 120, 100, 30);
        jt4.setBounds(90, 160, 100, 30);

        JButton jButton = new JButton("确认");
        jFrame.add(jButton);
        jButton.setBounds(90, 200, 100, 30);
        jFrame.setVisible(true);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jt1.getText().equals("") && !jt2.getText().equals("") && !jt3.getText().equals("") && !jt4.getText().equals("")) {
                    if (jt1.getText().length() <= 4 && jt2.getText().length() <= 5 && jt3.getText().length() <= 5 && jt4.getText().length() <= 5) {
                        if (!new DataCheck().checkRoad(jt2.getText())) {
                            int check = JOptionPane.showConfirmDialog(jFrame, "确定建立从 " + start.getName() + " 到 " + end.getName() + "的道路吗?");
                            if (check == JOptionPane.YES_OPTION) {
                                try {
                                    Connection conn = DbUtils.getConnection();
                                    try (PreparedStatement statement = conn.prepareStatement("" +
                                            "insert into road values (?,?,?,?,?,?)")) {
                                        statement.setString(1, start.getName());
                                        statement.setString(2, end.getName());
                                        statement.setInt(3, Integer.parseInt(jt1.getText()));
                                        statement.setInt(4, Integer.parseInt(jt3.getText()));
                                        statement.setInt(5, Integer.parseInt(jt4.getText()));
                                        statement.setString(6, jt2.getText());
                                        int rs = statement.executeUpdate();
                                        if (rs == 1) {
                                            JOptionPane.showMessageDialog(jFrame, "创建成功");
                                            jFrame.setVisible(false);
                                        }
                                        roadMessage.setStart_spot(start);
                                        roadMessage.setEnd_spot(end);
                                        roadMessage.setLength(Integer.parseInt(jt1.getText()));
                                        roadMessage.setName(jt2.getText());
                                        roadMessage.setMeihua(Integer.parseInt(jt3.getText()));
                                        roadMessage.setLvhua(Integer.parseInt(jt4.getText()));
                                        arrayList.add(roadMessage);
                                        bored.repaint();

                                    }
                                    conn.close();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(jFrame, "您已取消该操作");
                            }
                        } else {
                            JOptionPane.showMessageDialog(jFrame, "该路名以存在,请重新输入");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(jFrame,"输入不合法,请重试");
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame, "输入有误,请重新输入");
                }

            }
        });
    }
}
