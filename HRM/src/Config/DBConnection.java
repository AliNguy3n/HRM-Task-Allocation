package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "hrmandtaskallocation";
    private static final String USER = "sa";
    private static final String PASSWORD = "Nghia456";
    private static final String URL = "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE + ";user=" + USER + ";password=" + PASSWORD + ";encrypt=false";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //test Database
//    public static void main(String[] args) {
//        Connection connection = DBConnection.getConnection();
//        if (connection != null) {
//            System.out.println("Connected to the database");
//        } else {
//            System.out.println("Failed to connect to the database");
//        }
//    }

}