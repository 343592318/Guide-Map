package Operation;

import Utils.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.PropertyResourceBundle;

public class ImageUser {
    private String name;
    private String path;
    public ImageUser(String name,String path) {
        this.name = name;
        this.path = path;
    }

    public  ImageUser(String name) {
        this.name = name;
    }
    public Image out() {
        try {
            Connection conn = DbUtils.getConnection();
            try(PreparedStatement preparedStatement = conn.prepareStatement("select * from listimage where name = ?")) {
                preparedStatement.setString(1,name);
                ImageIcon imageIcon = null;
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    Blob blob = rs.getBlob(2);
                    imageIcon = new ImageIcon(blob.getBytes(1L,(int)blob.length()));
                }
                conn.close();
                return imageIcon.getImage();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void init() {
        try {
            Connection conn = DbUtils.getConnection();
            File f  = new File(path);
            try (InputStream inputStream = new FileInputStream(f)){
                PreparedStatement pre = conn.prepareStatement("insert into listimage values (?,?)");
                pre.setString(1,name);
                pre.setBinaryStream(2,inputStream);
                int affect = pre.executeUpdate();
            }
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean check() {
        try {
            Connection conn = DbUtils.getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from listimage where name = ?")){
                preparedStatement.setString(1,name);
                if (preparedStatement.executeQuery().next()) {
                    return false;
                }
            }
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String[] args) {
        ImageUser saveImage = null;
        String basePath = "Resources";
        String[] list = new File(basePath).list();
        if (list != null){
            for (String item : list) {
                saveImage = new ImageUser(item);
                if (saveImage.check()) {
                    saveImage.setPath("Resources/"+item);
                    saveImage.init();
                }
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
