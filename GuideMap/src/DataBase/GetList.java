package DataBase;

import Message.RoadMessage;
import Message.SplitMessage;
import Message.SpotMessage;
import Message.UserMessage;

import java.sql.*;
import java.util.ArrayList;

public class GetList implements SplitMessage {
    ArrayList<SpotMessage> list = new ArrayList<>();
    ArrayList<RoadMessage> list_road = new ArrayList<>();

    public ArrayList<SpotMessage> getSpotList() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * from spot"
            );
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    SpotMessage temp = new SpotMessage();
                    temp.setX(rs.getDouble(1));
                    temp.setY(rs.getDouble(2));
                    temp.setPhonenumber(rs.getString(3));
                    temp.setNuture(rs.getString(4));
                    temp.setName(rs.getString(5));
                    temp.setIntroduction(rs.getString(6));
                    list.add(temp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<RoadMessage> getRoadList() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = conn.prepareStatement("select * from road");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String temp1, temp2;
                    SpotMessage temp11 = null;
                    SpotMessage temp22 = null;
                    temp1 = rs.getString(1);
                    temp2 = rs.getString(2);
                    int length = rs.getInt(3);
                    int meihua = rs.getInt(4);
                    int lvhua = rs.getInt(5);
                    String name = rs.getString(6);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(temp1)) {
                            temp11 = list.get(i);
                        }
                        if (list.get(i).getName().equals(temp2)) {
                            temp22 = list.get(i);
                        }
                    }
                    RoadMessage roadMessage = new RoadMessage();
                    roadMessage.setStart_spot(temp11);
                    roadMessage.setEnd_spot(temp22);
                    roadMessage.setLvhua(lvhua);
                    roadMessage.setMeihua(meihua);
                    roadMessage.setName(name);
                    roadMessage.setLength(length);
                    list_road.add(roadMessage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_road;
    }
    public ArrayList<String> getLikeSpot(String name) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ?");){
                preparedStatement.setString(1,name);
                String like = null;
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    like = rs.getString(3);
                }
                if (like != null) {
                    int number = Integer.parseInt(like.split("&")[0]);
                    for(int i = 0;i < number;i++) {
                        list.add(like.split("&")[i+1]);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<UserMessage> getUserList() {
        ArrayList<UserMessage> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user")){
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    UserMessage userMessage = new UserMessage();
                    userMessage.setName(rs.getString(1));
                    userMessage.setPassword(rs.getString(2));
                    userMessage.setFlag(rs.getInt(4) == 1);
                    userMessage.setLikeSpot(new GetList().getLikeSpot(rs.getString(1)));
                    userMessage.setAutologin(rs.getInt(5));
                    userMessage.setRememberpassword(rs.getInt(6));
                    list.add(userMessage);
                    //System.out.println("!!!"+rs.getString(1));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
