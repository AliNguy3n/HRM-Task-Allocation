
package dap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Duc Linh
 */
public class DBConnect {
    static Connection cnn;

    public static Connection makeConnection(String serverName, String port, String database, String usernameServer, String passwordServer){
      String dbUrl = "jdbc:sqlserver://"+serverName+":"+port+";databaseName="+database+";user="+usernameServer+";password="+passwordServer+";"
      		+ "encrypt=true;trustServerCertificate=true";
        try{
            cnn = DriverManager.getConnection(dbUrl);
           
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return cnn;
    }

}
