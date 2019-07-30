package Operation;

import Message.SpotMessage;
import Utils.DbUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteRoad {
    private SpotMessage start;
    private SpotMessage end;
    private String name = null;

    public DeleteRoad(SpotMessage start, SpotMessage end, String name) {
        this.end = end;
        this.start = start;
        this.name = name;
    }

    public String init() {
        JFrame jFrame = new JFrame();
        int check = JOptionPane.showConfirmDialog(jFrame, "确定删除从 " + start.getName() + " 到 " + end.getName() + " 的路吗?");
        if (check == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DbUtils.getConnection();
                try (PreparedStatement statement = conn.prepareStatement(
                        "delete from road where name = ?")) {
                    statement.setString(1, name);
                    int rs = statement.executeUpdate();
                    if (rs == 1) {
                        JOptionPane.showConfirmDialog(jFrame, "删除成功");
                    }
                }
                conn.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return name;
        } else {
            return null;
        }
    }
}
