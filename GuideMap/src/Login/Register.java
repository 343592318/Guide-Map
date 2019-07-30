package Login;

import Utils.DbUtils;
import Utils.MD5Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
public class Register {
    String name1;
    String password1;

    public void init() {
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel beijing = new JLabel(background);
        beijing.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(beijing, new Integer(Integer.MIN_VALUE));
        JLabel jLabel = new JLabel("注册");
        JLabel jLabel1 = new JLabel("请输入用户名:");
        JLabel jLabel3 = new JLabel("请确认密码:");
        JTextField jTextField = new JTextField(50);
        JLabel jLabel2 = new JLabel("请输入密码:");
        JPasswordField jTextField1 = new JPasswordField(50);
        jTextField1.setEchoChar('●');
        JPasswordField jPasswordField = new JPasswordField(50);
        jPasswordField.setEchoChar('●');
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jTextField1.getText().length() <= 20 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar>='a' && keyChar <= 'z')) {
                } else {
                    e.consume();
                }
            }
        });
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jTextField.getText().length() <= 20 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar>='a' && keyChar <= 'z') ) {

                } else {
                    e.consume();
                }
            }
        });
        jPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jPasswordField.getText().length() <= 20 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        jPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar>='a' && keyChar <= 'z')){

                } else {
                    e.consume();
                }
            }
        });
        jFrame.setLayout(null);
        jFrame.add(jLabel1);
        jFrame.add(jLabel2);
        jFrame.add(jLabel3);
        jFrame.add(jTextField);
        jFrame.add(jTextField1);
        jFrame.add(jPasswordField);
        jFrame.add(jLabel);
        jFrame.setBounds(300, 300, 400, 300);
        jLabel.setBounds(180, 10, 100, 30);
        jLabel1.setBounds(20, 60, 100, 30);
        jLabel2.setBounds(20, 120, 100, 30);
        jLabel3.setBounds(20,180,100,30);
        jTextField.setBounds(100, 60, 200, 30);
        jTextField1.setBounds(100, 120, 200, 30);
        jPasswordField.setBounds(100,180,200,30);
        JButton jButton = new JButton("确定");
        jFrame.add(jButton);
        jButton.setBounds(150, 230, 100, 30);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        jFrame.setVisible(true);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name1 = jTextField.getText();
                password1 = jTextField1.getText();
                String password2 = jPasswordField.getText();
                if (name1.length() <= 50 && password1.length() <= 50) {
                    if (name1.equals("") || password1.equals("")) {
                        JOptionPane.showMessageDialog(jFrame, "用户名或密码不能为空!");
                    } else {
                        if (password2.equals(password1)) {
                            try {
                                Connection conn = DbUtils.getConnection();
                                boolean flag = false;
                                try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ?")) {
                                    preparedStatement.setString(1, name1);
                                    ResultSet rs = preparedStatement.executeQuery();
                                    if (rs.next()) {
                                        JOptionPane.showMessageDialog(jFrame, "该账户已被注册");
                                    } else {
                                        flag = true;
                                    }
                                    rs.close();
                                }
                                if (flag) {
                                    try (PreparedStatement preparedStatement = conn.prepareStatement("insert into user values (?,?,?,?,?,?)")) {
                                        preparedStatement.setString(1, name1);
                                        password1 = MD5Utils.getHash(password1,"MD5");
                                        preparedStatement.setString(2, password1);
                                        preparedStatement.setString(3, "0&");
                                        preparedStatement.setInt(4, 1);
                                        preparedStatement.setInt(5,0);
                                        preparedStatement.setInt(6,0);
                                        int rs = preparedStatement.executeUpdate();
                                        conn.close();
                                        if (rs == 1) {
                                            JOptionPane.showMessageDialog(jFrame, "注册成功");
                                            jFrame.setVisible(false);
                                        }
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(jFrame,"两次输入的密码不一致,请重新输入!");
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(jFrame,"输入不合法,请重试");
                }
            }
        });

    }
}