package Operation;

import Message.UserMessage;
import Utils.DbUtils;
import Utils.MD5Utils;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SaveUserMessage {
    private UserMessage userMessage;

    public SaveUserMessage() {
    }

    public SaveUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public void savecheck() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set user_check = ? where user_name = ?")) {
                if (userMessage.getFlag()) {
                    preparedStatement.setInt(1, 1);
                } else {
                    preparedStatement.setInt(1, 0);
                }
                preparedStatement.setString(2, userMessage.getName());
                int rs = preparedStatement.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePassword() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set user_password = ? where user_name = ?")) {
                preparedStatement.setString(1, MD5Utils.getHash(userMessage.getPassword(),"MD5"));
                preparedStatement.setString(2, userMessage.getName());
                int rs = preparedStatement.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRemeberPassword() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set rememberpassword = ? where user_name = ?")) {
                preparedStatement.setInt(1, userMessage.getRememberpassword());
                preparedStatement.setString(2, userMessage.getName());
                int rs = preparedStatement.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAutoLogin() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set autologin = ? where user_name = ?")) {
                preparedStatement.setInt(1, userMessage.getAutologin());
                preparedStatement.setString(2, userMessage.getName());
                int rs = preparedStatement.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoReset() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set autologin = ?")) {
                preparedStatement.setInt(1, 0);
                int rs = preparedStatement.executeUpdate();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
