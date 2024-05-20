package dap;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* @author Duc Linh
*/
public class DAPStaff extends DAPCore{

	public ResultSet select(int id) {
		String query = "SELECT [Email] FROM [Staff] WHERE [ID] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st =cnn.prepareStatement(query);
			st.setInt(1, id);
			rs= st.executeQuery();
			return rs;
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return null;
	}
	@Override
	public ResultSet select(String param1, String param2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int param) {
		// TODO Auto-generated method stub
		return 0;
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

}
