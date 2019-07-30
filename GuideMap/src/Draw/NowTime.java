package Draw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


public class NowTime extends JPanel {
    //添加 显示时间的JLabel
    public NowTime() {
        this.setFont(new Font("微软雅黑", Font.BOLD, 12));
        JLabel time = new JLabel();
        add(time);
        this.setTimer(time);
    }

    private void setTimer(JLabel time) {
        final JLabel varTime = time;
        Timer timeAction = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                //转换日期显示格式   
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }
    //运行方法
}

