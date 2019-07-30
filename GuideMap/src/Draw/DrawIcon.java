package Draw;

import Message.SpotMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrawIcon {
    ArrayList<SpotMessage> spotMessageArrayList;
    public void init(SpotMessage spotMessage, Graphics2D graphics2D, ArrayList<Image> list) {
        if (spotMessage.getNuture().equals("教学楼")) {
            graphics2D.drawImage(list.get(3), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if(spotMessage.getNuture().equals("食堂")) {
            graphics2D.drawImage(list.get(1), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if(spotMessage.getNuture().equals("实验室")) {
            graphics2D.drawImage(list.get(2), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if (spotMessage.getNuture().equals("楼房")) {
            graphics2D.drawImage(list.get(0), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if (spotMessage.getNuture().equals("体育馆")) {
            graphics2D.drawImage(list.get(4), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if (spotMessage.getNuture().equals("图书馆")) {
            graphics2D.drawImage(list.get(5), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if(spotMessage.getNuture().equals("洗手间")) {
            graphics2D.drawImage(list.get(6), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);
        }
        else if(spotMessage.getNuture().equals("医院")) {
            graphics2D.drawImage(list.get(7), (int) spotMessage.getX() - 20, (int) spotMessage.getY() - 20, 40, 40, null);

        }
    }
    public void drawLikeSpot(int pos,Image xingbiao,ArrayList<SpotMessage> spotMessageArrayList, Graphics2D graphics2D) {
        this.spotMessageArrayList = spotMessageArrayList;
        graphics2D.drawImage(xingbiao,(int)spotMessageArrayList.get(pos).getX()-20,
                (int)spotMessageArrayList.get(pos).getY()-20,40,40,null);
    }
}
