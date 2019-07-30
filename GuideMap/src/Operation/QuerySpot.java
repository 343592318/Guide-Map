package Operation;

import Message.SpotMessage;
import Utils.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class QuerySpot {
    private String name;
    private ArrayList<SpotMessage> list;
    private JComboBox<String> chufa;
    private JComboBox<String> daoda;
    private ArrayList<String> namelist = new ArrayList<String>();

    public QuerySpot(String name, JComboBox<String> chufa, JComboBox<String> daoda) {
        this.name = name;
        this.chufa = chufa;
        this.daoda = daoda;
    }

    public int init() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from spot where name like ?")) {
                preparedStatement.setString(1, "%" + name + "%");
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println("!!!!");
                    namelist.add(rs.getString(5));
                }
                rs.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame jFrame = new JFrame();
        jFrame.setLayout(null);
        jFrame.setBounds(200, 200, 420, 270);
        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel beijing = new JLabel(background);
        beijing.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(beijing, new Integer(Integer.MIN_VALUE));
        Vector<String> vector = new Vector<>();
        JComboBox<String> jComboBox = new JComboBox<>(vector);
        if (namelist.size() != 0) {
            for (String name : namelist) {
                vector.addElement(name);
            }
        }
        else{
            JOptionPane.showMessageDialog(jFrame,"没有搜索到结果!");
            return 0;
        }
        jFrame.add(jComboBox);
        JLabel jLabel = new JLabel("查询结果");
        jFrame.add(jLabel);
        jLabel.setBounds(180, 20, 100, 40);
        jComboBox.setBounds(100, 60, 200, 30);
        jComboBox.setSelectedIndex(0);
        JButton jButton1 = new JButton("作为出发地");
        JButton jButton2 = new JButton("作为到达地");
        jFrame.add(jButton1);
        jFrame.add(jButton2);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        jButton1.setBounds(100, 100, 100, 40);
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = (String) jComboBox.getSelectedItem();
                if (string != null) {
                    chufa.setSelectedItem(string);
                } else {
                    JOptionPane.showMessageDialog(jFrame, "请选择地点");
                }
            }
        });
        jButton2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = (String) jComboBox.getSelectedItem();
                if (string != null) {
                    daoda.setSelectedItem(string);
                } else {
                    JOptionPane.showMessageDialog(jFrame, "请选择地点");
                }
            }
        });
        jButton2.setBounds(200, 100, 100, 40);
        jFrame.setVisible(true);
        return 0;
    }
}
