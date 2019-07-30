package Operation;

import Message.SpotMessage;
import Message.UserMessage;
import Utils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class StarChange {
    private ArrayList<String> list = null;
    private UserMessage userMessage = null;
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    public StarChange(ArrayList<String> list, UserMessage userMessage,ArrayList<SpotMessage> spotMessageArrayList) {
        this.spotMessageArrayList = spotMessageArrayList;
        this.list = list;
        this.userMessage = userMessage;
    }

    public boolean init() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("update user set likespot = ? where user_name = ?")) {
                ListIterator<String> listIterator = list.listIterator();
                while(listIterator.hasNext()) {
                    String temp = listIterator.next();
                    SpotMessage spotMessage = new SpotMessage();
                    spotMessage.setName(temp);
                    if (spotMessageArrayList.indexOf(spotMessage) == -1) {
                        listIterator.remove();
                    }
                }
                String like = String.valueOf(list.size()) + "&";
                ListIterator listIterator1 = list.listIterator();
                while(listIterator1.hasNext()) {
                    like += listIterator1.next() +"&";
                }
                preparedStatement.setString(1, like);
                preparedStatement.setString(2, userMessage.getName());
                int rs = preparedStatement.executeUpdate();
                conn.close();
                if (rs == 1) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        catch (Exception e1){
            e1.printStackTrace();
        }
        return false;
    }
}
