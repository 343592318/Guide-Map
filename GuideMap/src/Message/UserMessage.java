package Message;

import Utils.DbUtils;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserMessage {
    private String name;
    private ArrayList<String> likeSpot;
    private boolean flag;
    private String password;
    private int autologin;
    private int rememberpassword;
    private int Jurisdiction;


    public UserMessage() {
    }

    ;

    public int getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public UserMessage(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getAutologin() {
        return autologin;
    }

    public int getRememberpassword() {
        return rememberpassword;
    }

    public void setAutologin(int autologin) {
        this.autologin = autologin;
    }

    public void setRememberpassword(int rememberpassword) {
        this.rememberpassword = rememberpassword;
    }

    public void setLikeSpot(ArrayList<String> likeSpot) {
        this.likeSpot = likeSpot;
    }

    public ArrayList<String> getLikeSpot() {
        return likeSpot;
    }

    public String getName() {
        return name;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setFlag(boolean flag) {
        this.flag = flag;
        return flag;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean setFlag() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from user where user_name = ?")) {
                preparedStatement.setString(1, getName());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(4) == 1) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                rs.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getPassword() {
        return password;
    }
}
