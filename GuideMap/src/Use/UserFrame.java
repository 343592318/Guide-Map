package Use;

import DataBase.DataCheck;
import DataBase.GetList;
import Draw.DrawIcon;
import Draw.NowTime;
import Login.LoginFrame;
import Message.RoadMessage;
import Message.SpotMessage;
import Message.UserMessage;
import Operation.*;
import algorithm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserFrame extends JFrame {
    private int Jurisdiction;
    private UserMessage userMessage;
    private boolean chooseLikeSpot;
    private Container container = this.getContentPane();
    private JTextArea zuojiankuang = new JTextArea(4, 29);
    private JTextArea textArea2 = new JTextArea(4, 30);
    private JPanel jp_north = new JPanel();
    private JPanel jp_east = new JPanel();
    private JPanel jp_south = new JPanel();
    private DefaultComboBoxModel<String> chufamodel = new DefaultComboBoxModel<>();
    private JComboBox<String> chufa = new JComboBox(chufamodel);
    private DefaultComboBoxModel<String> daodamodel = new DefaultComboBoxModel<>();
    private JComboBox<String> daoda = new JComboBox(daodamodel);
    private Image table;
    private Image biaoshi;
    private Image biaoshi2;
    private Image biaoshi3;
    private Image biaoshi4;
    private Image ditubiaoshi;
    private JComboBox<String> luxian;
    private PopupMenu popupMenu = new PopupMenu();
    private ArrayList<SpotMessage> list = new ArrayList<>();
    private ArrayList<RoadMessage> list_road = new ArrayList<>();
    private ArrayList<String> list_like = new ArrayList<>();
    private ArrayList<String> likespotList = null;
    private SpotMessage now = null;
    private SpotMessage next = null;
    private double XPOS;
    private double XXPOS;
    private double YPOS;
    private double YYPOS;
    private boolean[] draw = new boolean[100];
    private boolean[] draw2 = new boolean[100];
    private int[] draw3 = new int[100];

    public UserFrame(UserMessage userMessage) {
        this.userMessage = userMessage;
        System.out.println(userMessage.getName());
        chooseLikeSpot = userMessage.getFlag();
    }

    private boolean start = false;
    private JButton xingbiaoButton = null;
    private JButton xingbiaoshezhi = null;
    private JButton changepasswordButton = null;
    private boolean[] xingbiaoflag = new boolean[100];
    private Image loufang;
    private Image jiaoxuelou;
    private Image shiyanshi;
    private Image shitang;
    private Image tiyuguan;
    private Image tushuguan;
    private Image xishoujian;
    private Image yiyuan;
    private Image lujing2;
    private Image xingbiao;
    private beijing bored = new beijing();
    private ImageIcon imageIcon = new ImageIcon("beijing.jpg");
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private JButton zhuxiaoButton = new JButton("注销登陆");

    public void init() {
        Jurisdiction = userMessage.getJurisdiction();
        ImageIcon background = new ImageIcon("Resources/背景2.jpg");
        JLabel jLabel5 = new JLabel(background);
        jLabel5.setBounds(0, 0, 800, 725);
        JPanel backgroundpanel = (JPanel) this.getContentPane();
        backgroundpanel.setOpaque(false);
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(jLabel5, new Integer(Integer.MIN_VALUE));
        container.add(zhuxiaoButton);
        if (Jurisdiction == 1 || Jurisdiction == 0) {
            zhuxiaoButton.setBounds(640, 540, 100, 40);
        } else {
            zhuxiaoButton.setBounds(640, 560, 100, 40);
        }
        zhuxiaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SaveUserMessage().autoReset();
                if (userMessage.getAutologin() == 1) {
                    userMessage.setAutologin(0);
                    new SaveUserMessage(userMessage).saveAutoLogin();
                }
                setVisible(false);
                try {
                    new LoginFrame().init();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        GetList getList = new GetList();
        list = getList.getSpotList();
        list_road = getList.getRoadList();
        Arrays.fill(xingbiaoflag, false);
        likespotList = userMessage.getLikeSpot();
        list_like = userMessage.getLikeSpot();
        //System.out.println(userMessage.getName());
        if (!userMessage.getName().equals("admin")) {
            for (String item : userMessage.getLikeSpot()) {
                SpotMessage temp = new SpotMessage();
                temp.setName(item);
                if (list.indexOf(temp) != -1) {
                    xingbiaoflag[list.indexOf(temp)] = true;
                }
            }
        }
        this.setResizable(false);
        setTitle("校园导航系统");
        JLabel beijing = new JLabel();
        beijing.setIcon(imageIcon);
        beijing.setBounds(0, 0, 800, 725);
        JButton chaxunButton = new JButton("查询");
        container.add(chaxunButton);
        container.add(textArea2);
        chaxunButton.setBounds(640, 240, 100, 40);
        JButton chakandaolu = new JButton("道路信息");
        if (Jurisdiction == 1 || Jurisdiction == 0) {
            chakandaolu.setBounds(640, 300, 100, 40);
        } else {
            chakandaolu.setBounds(640, 520, 100, 40);
        }
        container.add(chakandaolu);


        textArea2.setEditable(false);
        chaxunButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chufaSelectedItem = (String) chufa.getSelectedItem();
                String daodaSelectedItem = (String) daoda.getSelectedItem();
                String moshi = (String) luxian.getSelectedItem();
                if (chufaSelectedItem != null && daodaSelectedItem != null && moshi != null) {
                    SpotMessage temp = new SpotMessage();
                    temp.setName(chufaSelectedItem);
                    int qidian = list.indexOf(temp);
                    temp.setName(daodaSelectedItem);
                    int zhongdian = list.indexOf(temp);
                    if (qidian == -1 || zhongdian == -1) {
                        JOptionPane.showMessageDialog(container, "您输入的地点信息有误");
                    } else {
                        QueryRoute(moshi, qidian, zhongdian);
                    }
                } else {
                    JOptionPane.showMessageDialog(container, "请输入或在图中标记需要查询的路线");
                }
            }
        });
        this.getRootPane().setDefaultButton(chaxunButton);
        setFont(new Font("楷体", Font.PLAIN, 13));
        zuojiankuang.setEditable(false);

        try {
            //xingbiao = ImageIO.read(new File("Resources/星标建筑.png"));
            xingbiao = new ImageUser("星标建筑.png").out();
            ditubiaoshi = new ImageUser("地图标示.png").out();
            biaoshi3 = new ImageUser("左键4.png").out();
            biaoshi2 = new ImageUser("右键4.png").out();
            biaoshi4 = new ImageUser("路径.png").out();
            table = new ImageUser("背景4.png").out();
            loufang = new ImageUser("楼房.png").out();
            shiyanshi = new ImageUser("实验室.png").out();
            tiyuguan = new ImageUser("体育馆.png").out();
            jiaoxuelou = new ImageUser("教学楼.png").out();
            shitang = new ImageUser("食堂.png").out();
            tushuguan = new ImageUser("图书馆.png").out();
            xishoujian = new ImageUser("洗手间.png").out();
            yiyuan = new ImageUser("医院.png").out();
            lujing2 = new ImageUser("路径2.png").out();
            imageArrayList.add(loufang);
            imageArrayList.add(shitang);
            imageArrayList.add(shiyanshi);
            imageArrayList.add(jiaoxuelou);
            imageArrayList.add(tiyuguan);
            imageArrayList.add(tushuguan);
            imageArrayList.add(xishoujian);
            imageArrayList.add(yiyuan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userMessage.getFlag()) {
            xingbiaoButton = new JButton("关闭星标显示");
        } else {
            xingbiaoButton = new JButton("打开星标显示");
        }
        container.add(xingbiaoButton);
        xingbiaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userMessage.getFlag()) {
                    userMessage.setFlag(false);
                    xingbiaoButton.setText("打开星标显示");
                    xingbiaoshezhi.setVisible(false);
                    chooseLikeSpot = false;
                    bored.repaint();
                    container.invalidate();
                    container.validate();
                    JOptionPane.showMessageDialog(container, "关闭成功");
                } else {
                    userMessage.setFlag(true);
                    xingbiaoButton.setText("关闭星标显示");
                    xingbiaoshezhi.setVisible(true);
                    chooseLikeSpot = true;
                    bored.repaint();
                    container.invalidate();
                    container.validate();
                    JOptionPane.showMessageDialog(container, "打开成功");
                }
                new SaveUserMessage(userMessage).savecheck();
            }
        });
        changepasswordButton = new JButton("修改密码");
        changepasswordButton.setBounds(640, 480, 100, 40);
        container.add(changepasswordButton);
        changepasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword(userMessage).change();
            }
        });
        if (Jurisdiction == 0 || Jurisdiction == 2) {
            changepasswordButton.setVisible(false);
            xingbiaoButton.setVisible(false);
        }
        xingbiaoButton.setBounds(640, 360, 100, 40);
        setSize(new Dimension(800, 733));
        container.setLayout(null);
        xingbiaoshezhi = new JButton("设置星标");
        container.add(xingbiaoshezhi);
        xingbiaoshezhi.setBounds(640, 420, 100, 40);
        if (userMessage.getFlag()) {
            xingbiaoshezhi.setVisible(true);
        } else {
            xingbiaoshezhi.setVisible(false);
        }
        xingbiaoshezhi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (now != null) {
                    if (!userMessage.getName().equals("游客")) {
                        int pos = list.indexOf(now);
                        if (!xingbiaoflag[pos]) {
                            int check = JOptionPane.showConfirmDialog(container, "确定将" + now.getName() + "设为星标建筑吗?");
                            if (check == JOptionPane.YES_OPTION) {
                                list_like.add(now.getName());
                                xingbiaoflag[pos] = true;
                                bored.repaint();
                                if (new StarChange(list_like, userMessage, list).init()) {
                                    JOptionPane.showMessageDialog(container, "设置成功");
                                } else {
                                    JOptionPane.showMessageDialog(container, "设置失败");
                                }
                            }
                        } else {
                            int check = JOptionPane.showConfirmDialog(container, "确定将" + now.getName() + "取消星标建筑吗?");
                            if (check == JOptionPane.YES_OPTION) {
                                list_like.remove(now.getName());
                                xingbiaoflag[pos] = false;
                                bored.repaint();
                                if (new StarChange(list_like, userMessage, list).init()) {
                                    JOptionPane.showMessageDialog(container, "设置成功");
                                } else {
                                    JOptionPane.showMessageDialog(container, "设置失败");
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(container, "游客无权进行该操作,请登录后重试");
                    }
                } else {
                    JOptionPane.showMessageDialog(container, "请左键选中需要设置星标的建筑");
                }
            }
        });
        if (Jurisdiction == 2) {
            container.remove(xingbiaoshezhi);
            setTitle("校园导航系统 管理员版");
            JButton xiugaiButton = new JButton("修改信息");
            JButton deletespotButton = new JButton("删除建筑");
            JButton newspotButton = new JButton("添加建筑");
            JButton newroadButton = new JButton("添加道路");
            JButton deleteroadButton = new JButton("断开道路");
            JButton moveButton = new JButton("移动建筑");

            container.add(xiugaiButton);
            container.add(newspotButton);
            container.add(newroadButton);
            container.add(deleteroadButton);
            container.add(moveButton);
            container.add(deletespotButton);


            xiugaiButton.setBounds(640, 280, 100, 40);
            deletespotButton.setBounds(640, 320, 100, 40);
            newspotButton.setBounds(640, 360, 100, 40);
            newroadButton.setBounds(640, 400, 100, 40);
            deleteroadButton.setBounds(640, 440, 100, 40);
            moveButton.setBounds(640, 480, 100, 40);

            xiugaiButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null) {
                        new Change(now, list, list_road, bored).init();
                    }
                }
            });
            deletespotButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null) {
                        String name = list.get(list.indexOf(now)).getName();
                        new Delete(now, list, list_road).init();
                        chufamodel.removeElement(name);
                        daodamodel.removeElement(name);
                        bored.repaint();
                    }
                }
            });
            newspotButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ((Math.pow(Math.abs(now.getX() - XPOS), 2) + Math.pow(Math.abs(now.getY() - YPOS), 2)) <= 1200) {
                        JOptionPane.showMessageDialog(container, "坐标点与已有坐标太近,请重新选择");
                    } else {
                        new New(XPOS, YPOS, list, bored).init();
                        chufamodel.removeAllElements();
                        daodamodel.removeAllElements();
                        for (SpotMessage item : list) {
                            chufamodel.addElement(item.getName());
                        }
                        daodamodel.removeAllElements();
                        for (SpotMessage item : list) {
                            daodamodel.addElement(item.getName());
                        }
                        bored.repaint();
                    }
                }
            });
            newroadButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null && next != null && now != next) {
                        boolean flag = true;
                        String temp1 = now.getName();
                        String temp2 = next.getName();
                        for (RoadMessage item : list_road) {
                            if (((temp1.equals(item.getStart_spot().getName())
                                    && temp2.equals(item.getEnd_spot().getName())) ||
                                    (temp2.equals(item.getStart_spot().getName())
                                            && temp1.equals(item.getEnd_spot().getName())))) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            new NewRoad(now, next, list_road, bored).init();
                        } else {
                            JOptionPane.showMessageDialog(container, "两建筑已存在路,请重试");
                        }

                    } else {
                        JOptionPane.showMessageDialog(container, "请选择起点与终点!");
                    }
                }
            });
            deleteroadButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null && next != null && now != next) {
                        String name = null;
                        for (RoadMessage item : list_road) {
                            if ((item.getStart_spot().getName().equals(now.getName()) &&
                                    item.getEnd_spot().getName().equals(next.getName())) ||
                                    (item.getEnd_spot().getName().equals(now.getName()) &&
                                            item.getStart_spot().getName().equals(next.getName()))) {
                                name = item.getName();
                            }
                        }
                        if (name == null) {
                            JOptionPane.showMessageDialog(container, "两建筑之间不存在路!");
                        } else {
                            DeleteRoad deleteRoad = new DeleteRoad(now, next, name);
                            name = deleteRoad.init();
                        }
                        if (name != null) {
                            ListIterator<RoadMessage> listIterator = list_road.listIterator();
                            while (listIterator.hasNext()) {
                                if (listIterator.next().getName().equals(name)) {
                                    listIterator.remove();
                                }
                            }
                            bored.repaint();
                        } else {
                            JOptionPane.showMessageDialog(container, "您已取消操作");
                        }
                    } else {
                        JOptionPane.showMessageDialog(container, "请输入节点与终点");
                    }
                }
            });
            moveButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (XPOS == 0 || YPOS == 0 || YYPOS == 0 || XXPOS == 0) {
                        JOptionPane.showMessageDialog(container, "请选择正确位置");
                    } else {
                        boolean check = new Move(now, (int) XXPOS, (int) YYPOS).init();
                        if (check) {
                            //JOptionPane.showMessageDialog(container,"修改成功");
                            for (SpotMessage spotMessage : list) {
                                if (spotMessage.getName().equals(now.getName())) {
                                    spotMessage.setX(XXPOS);
                                    spotMessage.setY(YYPOS);
                                }
                            }
                            bored.repaint();
                        } else {
                            JOptionPane.showMessageDialog(container, "您已取消修改");
                        }
                    }
                }
            });
        }

        Vector<String> luxianmodel = new Vector<>();
        luxian = new JComboBox<>(luxianmodel);
        luxian.setName("请选择您需要的路线");
        //luxian.setFont(new Font("楷体",0,13));
        luxian.setPreferredSize(new Dimension(100, 30));
        JPanel jp4 = new JPanel();
        //jp4.setLayout(null);
        JLabel chooseroad = new JLabel("      请选择路线:");
        container.add(chooseroad);
        chooseroad.setBounds(635, 145, 100, 40);
        luxianmodel.addElement("最快路线");
        luxianmodel.addElement("最短路线");
        luxianmodel.addElement("最美路线");
        luxianmodel.addElement("最绿路线");
        jp4.add(luxian);
        container.add(jp4);
        luxian.setSelectedItem("最快路线");
        jp4.setBounds(640, 175, 100, 200);
        jp4.setOpaque(false);

        JButton queryspotButton = new JButton("查询地点");
        //this.getRootPane().setDefaultButton(jb1);
        queryspotButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(container, "请输入您要查询的地点");
                if (name != null) {
                    QuerySpot query = new QuerySpot(name, chufa, daoda);
                    int check = query.init();
                }
            }
        });
        JButton cleanButton = new JButton("清除标记");
        cleanButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mousRightMove(0, 0);
                mouseLeftMove(0, 0);
                new Clean(draw, draw2).init();
                bored.repaint();
            }
        });
        container.add(zuojiankuang);
        zuojiankuang.setBounds(4, 605, 300, 83);
        textArea2.setFont(new Font("楷体", 0, 15));
        //textArea2.setDisabledTextColor(new Color(232,0, 31));
        zuojiankuang.setFont(new Font("楷体", 0, 15));
        bored.setOpaque(false);
        bored.setPreferredSize(new Dimension(600, 600));
        bored.setBounds(2, 0, 600, 600);
        bored.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getX() > 20 && e.getY() > 20) {
                        mouseLeftMove(e.getX(), e.getY());
                        bored.repaint();
                    }
                    int xPos = (int) e.getX();
                    int yPos = (int) e.getY();
                    double min_length = Integer.MAX_VALUE;
                    SpotMessage min = null;
                    //int hjsd=1/0;
                    int pos = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if ((list.get(i).getX() - xPos) * (list.get(i).getX() - xPos) + (list.get(i).getY() - yPos) * (list.get(i).getY() - yPos) < min_length) {
                            min_length = (list.get(i).getX() - xPos) * (list.get(i).getX() - xPos) + (list.get(i).getY() - yPos) * (list.get(i).getY() - yPos);
                            min = list.get(i);
                            pos = i;
                        }
                    }
                    if (xingbiaoflag[pos]) {
                        xingbiaoshezhi.setText("取消星标");
                        container.invalidate();
                        container.validate();
                    } else {
                        xingbiaoshezhi.setText("设置星标");
                        container.invalidate();
                        container.validate();
                    }
                    now = min;
                    if (now != null) {
                        chufa.setSelectedItem(now.getName());
                        if (Jurisdiction == 1 || Jurisdiction == 0) {
                            mouseLeftMove(now.getX(),now.getY());
                        }
                    }
                    zuojiankuang.setText("");
                    if (min != null) {
                        zuojiankuang.append("\n\t名称:" + min.getName() + "\n" + "\t联系电话:" + min.getPhonenumber() + "\n" + "\t介绍:" + min.getIntroduction());

                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (e.getX() > 20 && e.getX() > 20) {
                        mousRightMove(e.getX(), e.getY());
                        bored.repaint();
                    }
                    int xPos = (int) e.getX();
                    int yPos = (int) e.getY();
                    double min_length = Integer.MAX_VALUE;
                    SpotMessage min = null;
                    for (SpotMessage spotMessage : list) {
                        if ((spotMessage.getX() - xPos) * (spotMessage.getX() - xPos) + (spotMessage.getY() - yPos) * (spotMessage.getY() - yPos) < min_length) {
                            min_length = (spotMessage.getX() - xPos) * (spotMessage.getX() - xPos) + (spotMessage.getY() - yPos) * (spotMessage.getY() - yPos);
                            min = spotMessage;
                        }
                    }
                    next = min;
                    if (next != null) {
                        daoda.setSelectedItem(next.getName());
                        if (Jurisdiction == 1 || Jurisdiction == 0) {
                            mousRightMove(next.getX(),next.getY());
                        }
                    }

                    textArea2.setText("");
                    if (min != null) {
                        textArea2.append("\n\t名称:" + min.getName() + "\n" + "\t联系电话:" + min.getPhonenumber() + "\n" + "\t介绍:" + min.getIntroduction());

                    }
                }
            }
        });
        if (Jurisdiction == 1 || Jurisdiction == 0) {
            chakandaolu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null && next != null) {
                        if (new DataCheck(now, next).checkhasRoad()) {
                            for (RoadMessage roadMessage : list_road) {
                                if ((roadMessage.getStart_spot().getName().equals(now.getName()) &&
                                        roadMessage.getEnd_spot().getName().equals(next.getName())) || (
                                        roadMessage.getEnd_spot().getName().equals(now.getName()) &&
                                                roadMessage.getStart_spot().getName().equals(next.getName()))
                                ) {
                                    JOptionPane.showMessageDialog(container, "道路名称:" + roadMessage.getName()
                                            + "\n\n道路长度:" + roadMessage.getLength() + "\n\n美化程度:" + roadMessage.getMeihua());
                                    break;
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(container, now.getName() + "与" + next.getName() + "间不存在路");
                        }
                    } else {
                        JOptionPane.showMessageDialog(container, "请选择起点终点");
                    }
                }
            });
        } else {
            chakandaolu.setText("修改道路信息");
            chakandaolu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (now != null && next != null && XPOS != 0 && YPOS != 0) {
                        if (new DataCheck(now, next).checkhasRoad()) {
                            RoadMessage tempRoadMessage = null;
                            for (RoadMessage roadMessage : list_road) {
                                if ((roadMessage.getStart_spot().getName().equals(now.getName()) &&
                                        roadMessage.getEnd_spot().getName().equals(next.getName())) || (
                                        roadMessage.getEnd_spot().getName().equals(now.getName()) &&
                                                roadMessage.getStart_spot().getName().equals(next.getName()))
                                ) {
                                    tempRoadMessage = roadMessage;
                                    break;
                                }
                            }
                            new ChangeRoad(tempRoadMessage).init();
                        } else {
                            int check = JOptionPane.showConfirmDialog(container, now.getName() + "与" + next.getName() + "间不存在路" + "  是否新建道路?");
                            if (check == JOptionPane.YES_OPTION) {
                                new NewRoad(now, next, list_road, bored).init();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(container, "请选择起点终点");
                    }
                }
            });
        }
        chufa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(container, "点击了回车");
                }
            }
        });
        textArea2.setBounds(304, 605, 300, 83);
        jp_south.setBounds(4, 600, 300, 80);
        container.add(jp_south);
        jp_south.setOpaque(false);
        //bored.setBorder(new TitledBorder("Map"));
        container.add(bored);
        //container.add(jp_east);
        container.add(queryspotButton);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(new Date());
        JLabel jLabel = new JLabel(s);

        queryspotButton.setBounds(610, 630, 80, 30);
        container.add(cleanButton);
        cleanButton.setBounds(690, 630, 80, 30);

        for (SpotMessage spotMessage : list) {
            chufamodel.addElement(spotMessage.getName());
        }

        for (SpotMessage spotMessage : list) {
            daodamodel.addElement(spotMessage.getName());
        }
        textArea2.setOpaque(false);
        textArea2.setBorder(BorderFactory.createEmptyBorder());
        textArea2.setBackground(new Color(0, 0, 0, 40));
        //textArea2.setForeground(new Color(255, 255, 255));
        zuojiankuang.setOpaque(false);
        zuojiankuang.setBorder(BorderFactory.createEmptyBorder());
        zuojiankuang.setBackground(new Color(0, 0, 0, 40));
        //chaxunButton.setOpaque(false);
        //jButton9.setBorder(BorderFactory.createEmptyBorder());
        //jButton9.setContentAreaFilled(false);
        //jButton9.setBackground(new Color(0, 0, 0,0));
        chufa.setOpaque(false);
        chufa.setBorder(BorderFactory.createEmptyBorder());
        chufa.setBackground(new Color(255, 255, 255, 200));
        container.add(chufa);
        JLabel jLabel2 = new JLabel("出发地:");
        JLabel jLabel3 = new JLabel("到达地:");
        container.add(jLabel2);
        container.add(jLabel3);
        container.add(daoda);
        jLabel2.setBounds(650, 10, 100, 30);
        jLabel3.setBounds(650, 80, 100, 30);
        chufa.setBounds(640, 40, 100, 30);
        daoda.setBounds(640, 110, 100, 30);
        chufa.setMaximumRowCount(5);
        daoda.setMaximumRowCount(5);
        chufa.setEditable(true);
        daoda.setEditable(true);
        chufa.setSelectedIndex(0);
        daoda.setSelectedIndex(1);
        addExtraKeyListener();
        chufa.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    for (SpotMessage spotMessage : list) {
                        if (spotMessage.getName().equals((String) e.getItem())) {
                            now = spotMessage;
                            zuojiankuang.setText("");
                            zuojiankuang.append("\n\t名称:" + now.getName() + "\n" + "\t联系电话:" + now.getPhonenumber() + "\n" + "\t介绍:" + now.getIntroduction());
                            if (Jurisdiction == 1 || Jurisdiction == 0) {
                                mouseLeftMove(spotMessage.getX(), spotMessage.getY());
                            }
                            bored.repaint();
                        }
                    }
                }

            }
        });
        daoda.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    for (SpotMessage spotMessage : list) {
                        if (spotMessage.getName().equals((String) e.getItem())) {
                            next = spotMessage;
                            textArea2.setText("");
                            textArea2.append("\n\t名称:" + next.getName() + "\n" + "\t联系电话:" + next.getPhonenumber() + "\n" + "\t介绍:" + next.getIntroduction());
                            if (Jurisdiction == 1 || Jurisdiction == 0) {
                                mousRightMove(spotMessage.getX(), spotMessage.getY());
                            }
                            bored.repaint();
                        }
                    }
                }
            }
        });

        try {
            //UIManager.put("ComboBox.background", new Color(0,0,0,0));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(container);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NowTime nowTime = new NowTime();
        container.add(nowTime);
        nowTime.setBounds(647, 600, 100, 30);
        nowTime.setOpaque(false);
        //luxian.setOpaque(false);
        if (userMessage.getJurisdiction() == 1) {
            setLikeSpot();
        }
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        if (userMessage.getJurisdiction() == 0) {
            JOptionPane.showMessageDialog(container, "请在图中左键选择出发地,右键选择到达地,回车键查询路线");
        }
    }

    public class beijing extends JPanel {
        public void paint(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics2D.drawImage(table, 0, 0, 600, 600, null);
            graphics2D.drawImage(ditubiaoshi, 70, 450, 75, 75, null);
            for (int i = 0; i < list_road.size(); i++) {
                if (!draw[i]) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2.3f));
                    g2.setColor(new Color(0, 0, 0, 50));
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.drawLine((int) list_road.get(i).getStart_spot().getX(), (int) list_road.get(i).getStart_spot().getY(),
                            (int) list_road.get(i).getEnd_spot().getX(), (int) list_road.get(i).getEnd_spot().getY());
                } else {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2.3f));
                    g2.setColor(new Color(230, 0, 31, 160));
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.drawLine((int) list_road.get(i).getStart_spot().getX(), (int) list_road.get(i).getStart_spot().getY(),
                            (int) list_road.get(i).getEnd_spot().getX(), (int) list_road.get(i).getEnd_spot().getY());
                    g2.setColor(new Color(0, 0, 0, 50));
                }

            }
            DrawIcon drawIcon = new DrawIcon();
            for (int i = 0; i < list.size(); i++) {
                if (!draw2[i]) {
                    if (chooseLikeSpot && xingbiaoflag[i]) {
                        drawIcon.drawLikeSpot(i, xingbiao, list, graphics2D);
                    } else {
                        drawIcon.init(list.get(i), graphics2D, imageArrayList);
                    }
                    graphics2D.setColor(new Color(0, 0, 0));
                    graphics2D.setFont(new Font("楷体", Font.PLAIN, 14));
                    graphics2D.drawString(list.get(i).getName(), (int) list.get(i).getX() - 20, (int) list.get(i).getY() + 40);
                } else {
                    graphics2D.setColor(new Color(255, 0, 15));
                    graphics2D.drawImage(lujing2, (int) list.get(i).getX() - 20, (int) list.get(i).getY() - 20, 40, 40, null);
                    graphics2D.setFont(new Font("", Font.PLAIN, 18));
                    graphics2D.drawString(String.valueOf(draw3[i]), (int) list.get(i).getX() - 5, (int) list.get(i).getY() + 6);
                    graphics2D.setFont(new Font("楷体", Font.PLAIN, 14));
                    graphics2D.drawString(list.get(i).getName(), (int) list.get(i).getX() - 20, (int) list.get(i).getY() + 40);
                    graphics2D.setColor(new Color(0, 0, 0));
                }
            }
            if (XPOS != 0 && YPOS != 0) {
                graphics2D.drawImage(biaoshi2, (int) XPOS - 12, (int) YPOS - 32, 24, 32, null);
            }
            if (XXPOS != 0 && YYPOS != 0) {
                graphics2D.drawImage(biaoshi3, (int) XXPOS - 12, (int) YYPOS - 32, 24, 32, null);
            }
        }
    }

    private void mouseLeftMove(double xpos, double ypos) {
        XPOS = xpos;
        YPOS = ypos;
    }

    private void mousRightMove(double xpos, double ypos) {
        XXPOS = xpos;
        YYPOS = ypos;
    }

    private void QueryRoute(String moshi, int qidian, int zhongdian) {
        Algorithm algorithm = new Dijkstra(list, list_road, qidian, zhongdian);
        switch (moshi) {
            case "最快路线":
                break;
            case "最美路线":
                algorithm = new Beautifal(list, list_road, qidian, zhongdian);
                break;
            case "最短路线":
                algorithm = new Faster(list, list_road, qidian, zhongdian);
                break;
            case "最绿路线":
                algorithm = new Greening(list, list_road, qidian, zhongdian);
                break;
        }
        ArrayList<String> result = algorithm.doAlgorithm();
        String lujing = result.get(0);
        for (int i = 1; i < result.size(); i++) {
            lujing = lujing + " --> " + result.get(i);
        }
        new Clean(draw, draw2).init();
        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < list_road.size(); j++) {
                RoadMessage temp = list_road.get(j);
                String s1 = temp.getEnd_spot().getName();
                String s2 = temp.getStart_spot().getName();
                if (s1.equals(result.get(i)) && s2.equals(result.get(i + 1))) {
                    draw[j] = true;
                }
                if (s2.equals(result.get(i)) && s1.equals(result.get(i + 1))) {
                    draw[j] = true;
                }
            }
        }
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (result.get(i).equals(list.get(j).getName())) {
                    draw2[j] = true;
                    draw3[j] = i + 1;
                }
            }
        }
        mouseLeftMove(0, 0);
        mousRightMove(0, 0);
        bored.repaint();
        JOptionPane.showMessageDialog(container, lujing);
    }

    private void setLikeSpot() {
        if (likespotList.size() > 0) {
            SpotMessage temp = new SpotMessage();
            temp.setName(likespotList.get(0));
            XPOS = list.get(list.indexOf(temp)).getX();
            YPOS = list.get(list.indexOf(temp)).getY();
            if (likespotList.size() != 1) {
                temp.setName(likespotList.get(1));
                if (list.indexOf(temp) != -1) {
                    XXPOS = list.get(list.indexOf(temp)).getX();
                    YYPOS = list.get(list.indexOf(temp)).getY();
                }
            }
            bored.repaint();
        }
    }

    private void addExtraKeyListener() {
        Component component = chufa.getEditor().getEditorComponent();
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String strItem = chufa.getEditor().getItem().toString();
                if (strItem.isEmpty()) {
                    zhuxiaoButton.setEnabled(false);
                    container.validate();
                    container.invalidate();
                }
            }
        });
        chufa.setEditable(true);
    }
}
