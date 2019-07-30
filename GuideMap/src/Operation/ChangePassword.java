package Operation;

import DataBase.DataCheck;
import Message.UserMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChangePassword {
    private UserMessage userMessage;

    public ChangePassword(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public void change() {
        JFrame jFrame = new JFrame("修改密码");
        jFrame.setLayout(null);
        jFrame.setBounds(0, 0, 300, 300);
        JLabel jLabel = new JLabel("修改密码");
        JLabel jLabel1 = new JLabel("原密码");
        JLabel jLabel2 = new JLabel("新密码");
        JLabel jLabel3 = new JLabel("确认密码");
        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel beijing = new JLabel(background);
        beijing.setBounds(0, 0, 500, 400);
        JPanel backgroundpanel = (JPanel) jFrame.getContentPane();
        backgroundpanel.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(beijing, new Integer(Integer.MIN_VALUE));

        JTextField jTextField = new JTextField(50);
        JPasswordField jPasswordField = new JPasswordField(50);
        jPasswordField.setEchoChar('●');
        JPasswordField jPasswordField1 = new JPasswordField(50);
        jPasswordField1.setEchoChar('●');
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
        jPasswordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jPasswordField1.getText().length() <= 20 ) {

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
                if (!((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar >= 'a' && keyChar <= 'z'))) {
                    e.consume();
                }
            }
        });
        jPasswordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar >= 'a' && keyChar <= 'z'))) {
                    e.consume();
                }
            }
        });
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar >= 'a' && keyChar <= 'z'))) {
                    e.consume();
                }
            }
        });

        JButton jButton = new JButton("确认");
        JButton jButton1 = new JButton("取消");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password1 = jPasswordField.getText();
                String password2 = jPasswordField1.getText();
                System.out.println(password1);
                System.out.println(password2);
                if (!password1.equals("") && !password2.equals("") &&
                !jTextField.getText().equals("") && password1.length() <= 20) {
                    if (new DataCheck().checkPassword(userMessage)) {
                        if (password1.equals(password2)) {
                            userMessage.setPassword(password1);
                            new SaveUserMessage(userMessage).savePassword();
                            JOptionPane.showMessageDialog(jFrame,"密码修改成功");
                            jFrame.setVisible(false);
                        }
                        else {

                            JOptionPane.showMessageDialog(jFrame,"新密码输入不一致,请重新输入");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(jFrame,"原密码错误,请重新输入");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(jFrame,"原密码或新密码不能为空");
                }
            }
        });
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "您已取消操作");
                jFrame.setVisible(false);
            }
        });
        jFrame.add(jButton);
        jFrame.add(jButton1);
        jFrame.add(jLabel);
        jFrame.add(jLabel1);
        jFrame.add(jLabel2);
        jFrame.add(jLabel3);
        jFrame.add(jPasswordField);
        jFrame.add(jPasswordField1);
        jFrame.add(jTextField);
        jFrame.setBounds(300, 300, 400, 320);
        jLabel.setBounds(180, 10, 100, 30);
        jLabel1.setBounds(20, 60, 100, 30);
        jLabel2.setBounds(20, 120, 100, 30);
        jLabel3.setBounds(20, 180, 100, 30);
        jTextField.setBounds(100, 60, 200, 30);
        jPasswordField1.setBounds(100, 120, 200, 30);
        jPasswordField.setBounds(100, 180, 200, 30);
        jButton.setBounds(150, 230, 100, 30);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        jFrame.setVisible(true);

    }
}
