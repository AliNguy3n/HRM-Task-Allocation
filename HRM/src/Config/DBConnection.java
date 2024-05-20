package Config;

import application.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = Main.obSettings.getValue("serverName");
    private static final String PORT = Main.obSettings.getValue("port");
    private static final String DATABASE =  Main.obSettings.getValue("databaseName");
    private static final String USER = Main.obSettings.getValue("usernameServer") ;
    private static final String PASSWORD = Main.obSettings.getValue("passwordServer");
    private static final String URL = "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE + ";user=" + USER + ";password=" + PASSWORD + ";encrypt=false";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
