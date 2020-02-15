package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtils {
    private static final String url;
    private static final String username;
    private static final String password;
    private static final String driver;
    private static Connection connection;

    static {
        Properties properties = new Properties();
        try {
            properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        driver = properties.getProperty("driver");
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
