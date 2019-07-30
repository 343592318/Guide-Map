package Operation;

import Message.RoadMessage;
import Message.SpotMessage;
import Utils.DbUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Delete {
    private SpotMessage spotMessage;
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    private ArrayList<RoadMessage> roadMessageArrayList = null;

    public Delete(SpotMessage spotMessage, ArrayList<SpotMessage> spotMessageArrayList, ArrayList<RoadMessage> roadMessageArrayList) {
        this.spotMessage = spotMessage;
        this.roadMessageArrayList = roadMessageArrayList;
        this.spotMessageArrayList = spotMessageArrayList;
    }

    public void init() {
        JFrame jFrame = new JFrame();
        int choice = JOptionPane.showConfirmDialog(jFrame, "确认删除该节点?");
        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DbUtils.getConnection();
                try (PreparedStatement preparedStatement = conn.prepareStatement("delete from road where start = ? or end = ?")) {
                    preparedStatement.setString(1, spotMessage.getName());
                    preparedStatement.setString(2, spotMessage.getName());
                    int rs = preparedStatement.executeUpdate();
                    ListIterator<RoadMessage> listIterator = roadMessageArrayList.listIterator();
                    while(listIterator.hasNext()) {
                        RoadMessage roadMessage = listIterator.next();
                        if (roadMessage.getStart_spot().getName().equals(spotMessage.getName()) ||
                        roadMessage.getEnd_spot().getName().equals(spotMessage.getName())) {
                            listIterator.remove();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try (PreparedStatement preparedStatement = conn.prepareStatement("delete from road where end = ? or end = ?")) {
                    preparedStatement.setString(1, spotMessage.getName());
                    preparedStatement.setString(2, spotMessage.getName());
                    int rs = preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try (PreparedStatement preparedStatement = conn.prepareStatement(
                        "delete from spot where name = ?"
                )) {
                    preparedStatement.setString(1, spotMessage.getName());
                    System.out.println("!!!" + spotMessage.getName());
                    try {
                        int rs = preparedStatement.executeUpdate();
                        if (rs == 1) {
                            ListIterator<SpotMessage> listIterator = spotMessageArrayList.listIterator();
                            while(listIterator.hasNext()) {
                                SpotMessage spotMessage = listIterator.next();
                                if (spotMessage.getName().equals(this.spotMessage.getName())) {
                                    listIterator.remove();
                                }
                            }
                            JOptionPane.showMessageDialog(jFrame, "删除成功!");
                            //jFrame.setVisible(false);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    DbUtils.closeAll(conn);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                //preparedStatement.setString(3,spotMessage.getIntroduction());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}