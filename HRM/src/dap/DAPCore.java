package dap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.Main;

/**
* @author Duc Linh
* Lớp DAPCore chứa các phương thức cần thiết để Truy xuất vào cơ sở dử liệu của dự án
* DAPCore cung cấp cho các lớp kế thừa nó phương thức kết nối, truy vấn vào CSDL
*/
/**
 * 
 */
abstract class DAPCore {
	String serverName = Main.obSettings.getValue("serverName");
	String port = Main.obSettings.getValue("port");
	String usernameServer = Main.obSettings.getValue("usernameServer") ;
	String passwordServer = Main.obSettings.getValue("passwordServer");
	String database = Main.obSettings.getValue("databaseName");
	Connection cnn;
	PreparedStatement st= null;
	ResultSet rs =null;
	int count=0;
	
	/**
	 * Phương thức @select trả về kết quả @ResultSet sau khi thực thi 
	 */
	public abstract ResultSet select(String param1, String param2) ;
	
	/**
	 *  Phương thức @insert trả về kết quả @Integer là số hàng sau khi thực thi 
	 */
	public abstract int insert();
	
	/**
	 *  Phương thức @update trả về kết quả @Integer là số hàng sau khi thực thi 
	 */
	public abstract int update();
	
	/**
	 *  Phương thức @delete trả về kết quả @Integer là số hàng sau khi thực thi 
	 */
	public abstract int delete(int param);
	
	/**
	 *  Phương thức @close đóng kết nối
	 */
	public abstract void close();
}
