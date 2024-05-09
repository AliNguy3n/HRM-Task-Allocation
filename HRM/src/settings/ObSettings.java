package settings;

import java.util.HashMap;


/**
* @author Duc Linh
* Lớp @ObSettings được tạo với mục đích lưu trữ tất cả các thông tin liên qua tới các cài đặt
* phiên hoạt động của người dùng hiện tại bao gồm thông tin đăng nhập vào DataBase.
*/

public class ObSettings {
	/**
	 * 1.General:
	 *	pageStartup
	 *	notification
	 * 2.Database:
	 * 	databaseType;
	 *	serverName;
	 *	usernameServer;
	 *	passwordServer;
	 *	databaseName;
	 *	port;
	 */
	private HashMap<String, String> settings = new HashMap<String, String>();
	
	public void setSettings(HashMap<String, String> settings) {
		this.settings =settings;
	}
	public HashMap<String, String> getSettings() {
		return settings;
	}
	/**
	 * Phương thức @setValue gán giá trị cho các thuộc tính 
	 */
	public void setValue(String key,String value) {
		if(value==null || value.isEmpty() || value.isBlank() ) {
			value = null;
		}
		settings.put(key, value);
	}
	/**
	 * Phương thức @getValue lấy giá trị của các thuộc tính 
	 */
	public String getValue(String key) {
		return settings.get(key);
	}
}
