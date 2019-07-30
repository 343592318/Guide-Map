package Utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DbUtils {
    private static ComboPooledDataSource comboPooledDataSource;
    private static DruidDataSource druidDataSource = new DruidDataSource();
    private static Properties properties = new Properties();
    static {
//        InputStream inputStream = DbUtils.class.getClassLoader().getResourceAsStream("druid.properties");
//        try {
//            properties.load(inputStream);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        druidDataSource.setDriverClassName("driverClassName");
//        druidDataSource.setUrl(properties.getProperty("url"));
//        druidDataSource.setUsername(properties.getProperty("username"));
//        druidDataSource.setPassword(properties.getProperty("password"));
//        druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
//        druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
//        druidDataSource.setMaxWait(Integer.parseInt(properties.getProperty("maxWait")));


        comboPooledDataSource = new ComboPooledDataSource("myc3p0");
    }

    public static Connection getConnection() {
        try {
            return comboPooledDataSource.getConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeAll(ResultSet rs, PreparedStatement preparedStatement ,Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void closeAll(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
