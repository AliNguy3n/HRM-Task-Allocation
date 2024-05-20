package dap;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* @author Duc Linh
* @DAPLogin được sử dụng để triển khai các phương thức cần sử dụng cho quá trình đăng nhập của thằng LoginController
*/
public class DAPLogin extends DAPCore{
	
	@Override
	public ResultSet select(String username, String password) {
		String query ="select * from [Staff] where [user_name] = ? and [password] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, username);
			st.setString(2, password);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	@Override
	public void close() {
		try {
			rs.close();
			st.close();
			cnn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert() {
		// Phương thức này không được triển khai
		return 0;
	}

	@Override
	public int update() {
		// Phương thức này không được triển khai
		return 0;
	}

	public ResultSet selectUser(String username, String email, float salary) {
		String query ="select * from [Staff] where [user_name] = ? and [email] = ? and [salary] =?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, username);
			st.setString(2, email);
			st.setFloat(3, salary);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public int updatePass(String username, String password) {
		String query ="UPDATE [Staff] SET [password] = ? WHERE [user_name]= ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, password);
			st.setString(2, username);
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int delete(int param) {
		// Phương thức này không được triển khai
		return 0;
	}
	
}
