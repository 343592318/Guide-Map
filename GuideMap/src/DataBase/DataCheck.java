package DataBase;

import Message.RoadMessage;
import Message.SpotMessage;
import Message.UserMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataCheck {
    private String name;
    private SpotMessage spotMessage1;
    private SpotMessage spotMessage2;
    private RoadMessage roadMessage;

    public DataCheck(SpotMessage spotMessage1) {
        this.spotMessage1 = spotMessage1;
    }

    public DataCheck(SpotMessage spotMessage1,SpotMessage spotMessage2) {
        this.spotMessage1 = spotMessage1;
        this.spotMessage2 = spotMessage2;
    }

    public DataCheck(RoadMessage roadMessage) {
        this.roadMessage = roadMessage;
    }

    public DataCheck(String name) {
        this.name = name;
    }

    public DataCheck() {
    }

    public boolean checkhasRoad() {
        //System.out.println(spotMessage2.getName());
        //System.out.println(spotMessage1.getName());
        boolean flag = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from road where start = ? and end = ?")) {
                preparedStatement.setString(1, spotMessage1.getName());
                preparedStatement.setString(2,spotMessage2.getName());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    flag = true;
                }
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from road where end = ? and start = ?")) {
                preparedStatement.setString(1, spotMessage1.getName());
                preparedStatement.setString(2,spotMessage2.getName());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean checkSpot(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from spot where name = ?")) {
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkRoad(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from road where name = ?")) {
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean checkPassword(UserMessage userMessage) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Guide?useSSL=False&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "200083";
            Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ? and user_password = ?")) {
                preparedStatement.setString(1, userMessage.getName());
                preparedStatement.setString(2,userMessage.getPassword());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
