package Operation;

import Message.SpotMessage;
import Utils.DbUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class  Move{
    private SpotMessage spotMessage = null;
    private int XXPOS;
    private int YYPOS;

    public Move(SpotMessage spotMessage, int XXPOS, int YYPOS) {
        this.spotMessage = spotMessage;
        this.XXPOS = XXPOS;
        this.YYPOS = YYPOS;
    }

    public boolean init() {
        JFrame jFrame = new JFrame();
        int check = JOptionPane.showConfirmDialog(jFrame, "确定修改该建筑位置?");
        if (check == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DbUtils.getConnection();
                try (PreparedStatement preparedStatement = conn.prepareStatement("" +
                        "update spot set x = ?,y = ? where name = ?")) {
                    preparedStatement.setDouble(1, (double) XXPOS);
                    preparedStatement.setDouble(2, (double) YYPOS);
                    preparedStatement.setString(3, spotMessage.getName());
                    int rs = preparedStatement.executeUpdate();
                    if (rs == 1) {
                        JOptionPane.showMessageDialog(jFrame, "修改成功");
                    }
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
