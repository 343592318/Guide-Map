package Login;

import DataBase.GetList;
import Message.UserMessage;
import Operation.SaveUserMessage;
import Use.UserFrame;
import Utils.DbUtils;
import Utils.MD5Utils;
import Utils.SwingUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.xdevapi.Result;
//import com.sun.awt.AWTUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.plaf.FontUIResource;

public class LoginFrame {
    private boolean quanxian = false;
    private JFrame jFrame = new JFrame("校园导航系统登陆");
    private DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<>();
    private JComboBox<String> name = new JComboBox<>(defaultComboBoxModel);//登陆用户名框
    private JPasswordField password = new JPasswordField(20);//登陆密码框
    private JButton loginButton = new JButton("登陆");//登陆按钮;
    private JButton nimingButton = new JButton("游客登陆");
    private JButton zhuce = new JButton("注册账户");
    private JCheckBox autologin, rememberoassword;
    private boolean changeauto, changeremember;
    private ArrayList<UserMessage> arrayList = new GetList().getUserList();

    public void init() throws Exception {

        jFrame.setResizable(false);
        ImageIcon background = new ImageIcon("Resources/背景6.jpg");
        JPanel backgrounded = (JPanel) jFrame.getContentPane();
        JLabel jLabel = new JLabel(background);
        jLabel.setBounds(0, 0, 500, 400);
        backgrounded.setOpaque(false);
        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(jLabel, Integer.valueOf(Integer.MIN_VALUE));

        autologin = new JCheckBox("自动登陆");
        rememberoassword = new JCheckBox("记住密码");

        name.setFont(new Font("", 2, 16));
        password.setEchoChar('●');
        zhuce.setFont(new Font("楷体", 0, 13));
        zhuce.setForeground(new Color(0x861C1C1C, true));
        loginButton.setFont(new Font("楷体", 0, 16));
        nimingButton.setFont(new Font("楷体", 0, 13));
        nimingButton.setForeground(new Color(0x861C1C1C, true));
        //name.setFont(new Font("",Font.PLAIN,14));
        name.setOpaque(false);
        password.setFont(new Font("", Font.PLAIN, 11));
        password.setOpaque(false);
        zhuce.setOpaque(false);
        for (UserMessage item : arrayList) {
            defaultComboBoxModel.addElement(item.getName());
        }
        name.setEditable(true);
        name.setSelectedIndex(0);
        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (name.getSelectedItem().toString().length() <= 20 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (password.getText().length() <= 20 ) {

                }
                else {
                    e.consume();
                }
            }
        });
        if (arrayList.get(0).getRememberpassword() == 1) {
            rememberoassword.setSelected(true);
            quanxian = true;
            password.setText("******");
        }

        name.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String name = e.getItem().toString();
                    for (UserMessage item : arrayList) {
                        if (item.getName().equals(name)) {
                            if (item.getRememberpassword() == 1) {
                                quanxian = true;
                                //password.setText(item.getPassword());
                                rememberoassword.setSelected(true);
                            } else {
                                quanxian = false;
                                password.setText("");
                                rememberoassword.setSelected(false);
                            }
                            break;
                        }
                    }
                }
            }
        });
        name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                password.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                password.setText("");
            }
        });
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                password.setText("");
                quanxian = false;
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        autologin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox jCheckBox = (JCheckBox) e.getItem();
                if (jCheckBox.isSelected()) {
                    new SaveUserMessage().autoReset();
                    String name = LoginFrame.this.name.getSelectedItem().toString();
                    UserMessage temp = null;
                    for (UserMessage userMessage : arrayList) {
                        if (userMessage.getName().equals(name)) {
                            temp = userMessage;
                            break;
                        }
                    }
                    if (temp == null) {
                        JOptionPane.showMessageDialog(jFrame, "请输入正确账户");
                    } else {
                        if (MD5Utils.getHash(password.getText(),"MD5").equals(temp.getPassword()) || quanxian) {
                            temp.setRememberpassword(1);
                            new SaveUserMessage(temp).saveRemeberPassword();
                            rememberoassword.setSelected(true);
                            temp.setAutologin(1);
                            new SaveUserMessage(temp).saveAutoLogin();
                        } else if (password.getText().equals("")) {
                            JOptionPane.showMessageDialog(jFrame, "请输入密码后选择记住密码");
                        } else {
                            JOptionPane.showMessageDialog(jFrame, "密码错误,请输入正确密码");
                        }
                    }
                } else {
                    String name = LoginFrame.this.name.getSelectedItem().toString();
                    UserMessage temp = null;
                    for (UserMessage item : arrayList) {
                        if (item.getName().equals(name)) {
                            temp = item;
                            break;
                        }
                    }
                    if (temp == null) {
                        JOptionPane.showMessageDialog(jFrame, "请输入正确账号");
                    } else {
                        temp.setAutologin(0);
                        new SaveUserMessage(temp).saveAutoLogin();
                    }
                }
            }
        });
        rememberoassword.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox jCheckBox = (JCheckBox) e.getItem();
                if (jCheckBox.isSelected()) {
                    String name = LoginFrame.this.name.getSelectedItem().toString();
                    UserMessage temp = null;
                    for (UserMessage userMessage :arrayList) {
                        if (userMessage.getName().equals(name)) {
                            temp = userMessage;
                            break;
                        }
                    }
                    if (temp == null) {
                        JOptionPane.showMessageDialog(jFrame, "请输入正确账户");
                    } else {
                        if (MD5Utils.getHash(password.getText(),"MD5").equals(temp.getPassword())) {

                                temp.setRememberpassword(1);
                                new SaveUserMessage(temp).saveRemeberPassword();
                            //System.out.println("成功");
                            temp.setRememberpassword(1);
                            new SaveUserMessage(temp).saveRemeberPassword();
                        } else if (password.getText().equals("")) {
                            JOptionPane.showMessageDialog(jFrame, "请输入密码后选择记住密码");
                        } else {
                            JOptionPane.showMessageDialog(jFrame, "密码错误,请输入正确密码");
                        }
                    }
                } else {
                    String name = Objects.requireNonNull(LoginFrame.this.name.getSelectedItem()).toString();
                    UserMessage temp = null;
                    for (UserMessage item : arrayList) {
                        if (item.getName().equals(name)) {
                            temp = item;
                            break;
                        }
                    }
                    if (temp == null) {
                        JOptionPane.showMessageDialog(jFrame, "请输入正确账号");
                    } else {
                        temp.setRememberpassword(0);
                        new SaveUserMessage(temp).saveRemeberPassword();
                    }
                }
            }
        });
        zhuce.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register().init();
                arrayList = new GetList().getUserList();
                defaultComboBoxModel.removeAllElements();
                for (UserMessage item : arrayList) {
                    defaultComboBoxModel.addElement(item.getName());
                }
            }
        });
        nimingButton.addActionListener(e -> {
            UserMessage userMessage = new UserMessage("游客", "");
            userMessage.setLikeSpot(new ArrayList<String>());
            userMessage.setJurisdiction(0);
            new UserFrame(userMessage).init();
            jFrame.setVisible(false);
        });
        ActionListener login = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(name.getSelectedItem().toString() + password.getText());
                    if (name.getSelectedItem().toString().equals("admin")) {
                        if (password.getText().equals("123456")) {
                            JOptionPane.showMessageDialog(jFrame, "登陆成功!");
                            jFrame.setVisible(false);
                            UserMessage userMessage1 = new UserMessage();
                            userMessage1.setName("admin");
                            userMessage1.setPassword("123456");
                            userMessage1.setLikeSpot(new ArrayList<String>());
                            userMessage1.setJurisdiction(2);
                            new UserFrame(userMessage1).init();
                        }
                    }
                    else{
                        if (validate(name.getSelectedItem().toString(), password.getText()) == 1 || quanxian) {
                            JOptionPane.showMessageDialog(jFrame, "登陆成功!");
                            jFrame.setVisible(false);
                            UserMessage userMessage = getUserMessasge(name.getSelectedItem().toString());
                            userMessage.setJurisdiction(1);
                            new UserFrame(userMessage).init();
                        }  else {
                            JOptionPane.showMessageDialog(jFrame, "登陆失败!");
                        }
                    }
                } catch (Exception s) {
                    s.printStackTrace();
                }
            }
        };
        loginButton.addActionListener(login);
        //AWTUtilities.setWindowOpacity(jFrame,0.5f);
        SwingUtils.enterPressesWhenFocused(loginButton);
        SwingUtils.enterPressesWhenFocused(password, login);

        JLabel jl1 = new JLabel("用户名:");
        jl1.setFont(new Font("楷体", 0, 16));
        JLabel jl2 = new JLabel("密  码:");
        jl2.setFont(new Font("楷体", 0, 16));
        JLabel jl3 = new JLabel(new ImageIcon("Resources/地图标示.png"));
        jl3.setFont(new Font("楷体", 0, 19));
        jFrame.setLayout(null);
        backgrounded.add(autologin);
        backgrounded.add(rememberoassword);
        rememberoassword.setBounds(260, 188, 80, 30);
        autologin.setBounds(100, 188, 80, 30);
        autologin.setForeground(new Color(2));
        rememberoassword.setForeground(new Color(2));
        backgrounded.add(jl1);
        jl1.setBounds(45, 100, 60, 30);
        backgrounded.add(jl2);
        jl2.setBounds(45, 150, 60, 30);
        backgrounded.add(jl3);
        jl3.setBounds(177, 10, 75, 75);

        backgrounded.add(name);
        name.setBounds(100, 100, 250, 30);
        backgrounded.add(password);
        backgrounded.add(loginButton);
        loginButton.setBounds(115, 230, 200, 42);
        nimingButton.setBounds(340, 270, 80, 33);
        nimingButton.setOpaque(false);
        nimingButton.setBorder(BorderFactory.createEmptyBorder());
        nimingButton.setContentAreaFilled(false);
        zhuce.setBounds(10, 270, 80, 33);
        zhuce.setOpaque(false);
        zhuce.setBorder(BorderFactory.createEmptyBorder());
        zhuce.setContentAreaFilled(false);

        backgrounded.add(nimingButton);
        backgrounded.add(zhuce);

        password.setBounds(100, 150, 250, 30);

        jFrame.setBounds(0, 0, 440, 340);
//        name.setOpaque(false);
//        name.setUI(new BasicComboBoxUI() {
//
//            public void installUI(JComponent comboBox) {
//
//                super.installUI(comboBox);
//
//                listBox.setForeground(Color.WHITE);
//
//                listBox.setSelectionBackground(new Color(0, 0, 0, 0));
//
//                listBox.setSelectionForeground(Color.BLACK);
//
//            }
//
//
//            /**
//             * 该方法返回右边的按钮
//
//             */
//
//            protected JButton createArrowButton() {
//
//                return super.createArrowButton();
//
//            }
//
//        });
        loginButton.setBackground(new Color(255, 255, 255));
        //ImageIcon imageIcon = new ImageIcon("Resources/登陆.png");
        //loginButton.setIcon(imageIcon);
        try {
            //UIManager.put("ComboBox.background", new Color(0, 0, 0, 0));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(jFrame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jFrame.getRootPane().setDefaultButton(loginButton);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
        boolean checkautologin = true;
        String nameauto = null;
        for (UserMessage item : arrayList) {
            if (item.getAutologin() == 1) {
                checkautologin = false;
                jFrame.setVisible(false);
                nameauto = item.getName();
                break;
            }
        }
        if (checkautologin) {
            jFrame.setVisible(true);
        } else {
            UserMessage userMessage = getUserMessasge(nameauto);
            userMessage.setJurisdiction(1);
            new UserFrame(userMessage).init();
        }

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private int validate(String userName, String userPassword) throws Exception {
        Connection conn = DbUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement("select * from admin where admin_name = ? and admin_password = ?");
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2,userPassword);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            DbUtils.closeAll(conn);
            return 2;
        }
        userPassword = MD5Utils.getHash(userPassword,"MD5");
        preparedStatement = conn.prepareStatement("select * from user where user_name = ? and user_password = ?");
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2,userPassword);
        rs = preparedStatement.executeQuery();
        if (rs.next()) {
            DbUtils.closeAll(conn);
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        //Font font = new Font("楷体",0,13);
        //initGlobalFontSetting(font);
        new LoginFrame().init();

    }

    public static void initGlobalFontSetting(Font fnt) {
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    private UserMessage getUserMessasge(String name) {
        UserMessage userMessage = new UserMessage();
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ?")) {
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    userMessage.setName(name);
                    userMessage.setPassword(rs.getString(2));
                    userMessage.setFlag(rs.getInt(4) == 1);
                    userMessage.setLikeSpot(new GetList().getLikeSpot(name));
                    userMessage.setAutologin(rs.getInt(5));
                    userMessage.setRememberpassword(rs.getInt(6));
                }
                rs.close();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMessage;
    }

    public UserMessage getAdminrMessasge(String name) {
        UserMessage userMessage = new UserMessage();
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from admin where admin_name = ?")) {
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    userMessage.setName(name);
                    userMessage.setPassword(rs.getString(2));
                    userMessage.setLikeSpot(new ArrayList<String>());
                }
                rs.close();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMessage;
    }
}
