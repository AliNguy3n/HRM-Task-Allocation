package dap;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
* @author Duc Linh
*/
public class DAPCreateTask extends DAPCore{

	@Override
	public ResultSet select(String department, String id) {
		String query ="select [Email],[ID] from [Staff] where [Department] = ? and not [ID] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, department);
			st.setInt(2, Integer.parseInt(id));
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet insertTask(String title, Date startDate, Time startTime, Date endDate, Time endTime, 
		String content, int id) {
		
		String query ="INSERT INTO [Task]([Title],[Started_Date],[Started_Time],[Ended_Date],[Ended_Time],"
				+ "[Content],[Assignedby]) OUTPUT inserted.ID VALUES (?,?,?,?,?,?,?)";
		
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, title);
			st.setDate(2, startDate);
			st.setTime(3, startTime);
			st.setDate(4, endDate);
			st.setTime(5, endTime);
			st.setString(6, content);
			st.setInt(7, id);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int insertStaffTask(int StaffID, int TaskID) {
		String query ="insert into [StaffTask] values (?,?)";
		
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		
		try {
			st= cnn.prepareStatement(query);
			st.setInt(1, StaffID);
			st.setInt(2, TaskID);
			
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int insert() {
		
		return 0;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete() {
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
