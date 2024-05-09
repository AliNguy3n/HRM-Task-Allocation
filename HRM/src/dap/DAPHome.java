package dap;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
* @author Duc Linh
* @DAPHone chứa tất cả các phương thức cần cho truy xuất database cho Home Page
*/
public class DAPHome extends DAPCore {
	
	/**
	 * @selectTask phương thức trả về số lượng Task đang thực hiện theo tham số là khoảng thời gian
	 * @param id là ID của người đứng đầu phong ban
	 * @param start thời gian bắt đầu
	 * @param end thời gian kết thúc
	 * @return
	 */
	public ResultSet selectTask(int id, Date start, Date end) {
		String query = "SELECT Task.ID, Task.Title, Task.Content, Task.Started_Date, Task.Started_Time, \r\n"
				+ "Task.Ended_Date, Task.Ended_Time, Task.Assignedby , \r\n"
				+ "COUNT(DISTINCT StaffTask.StaffID) AS DifferentStaffIDs\r\n"
				+ "FROM Task\r\n"
				+ "INNER JOIN StaffTask ON Task.ID = StaffTask.TaskID\r\n"
				+ "INNER JOIN Staff ON Staff.ID = StaffTask.StaffID\r\n"
				+ "WHERE Task.Assignedby = ? and Task.Started_Date >= ? AND Task.Started_Date <= ? \r\n"
				+ "GROUP BY Task.ID, Task.Title, Task.Content, Task.Started_Date, Task.Started_Time, \r\n"
				+ "Task.Ended_Date, Task.Ended_Time, Task.Assignedby\r\n";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st = cnn.prepareStatement(query);
			st.setInt(1, id);
			st.setDate(2, start);
			st.setDate(3, end);
			rs = st.executeQuery();
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @selectPerson phương thức này trả về số lượng nhân sự trong phòng ban đang kiểm soát và số lượng task đang thực hiện của 
	 * mỗi nhân sự đó
	 * @param department Phòng ban thực hiện 
	 * @param id ID của trưởng phòng ban đó
	 * @return
	 */
	public ResultSet selectPerson(String department, int id) {
		String query = "SELECT Staff.ID, Staff.First_Name, Staff.Last_Name, Staff.Email , \r\n"
				+ "Staff.Phone_Number,\r\n"
				+ "COUNT(DISTINCT StaffTask.TaskID) AS TotalTasks,\r\n"
				+ "SUM(CASE WHEN Task_Execution.Status = 'Complete' THEN 1 ELSE 0 END) AS TasksCompleted\r\n"
				+ "FROM Staff \r\n"
				+ "LEFT JOIN StaffTask  ON Staff.ID = StaffTask.StaffID\r\n"
				+ "LEFT JOIN Task_Execution ON StaffTask.TaskID = Task_Execution.TaskID \r\n"
				+ "AND StaffTask.StaffID = Task_Execution.StaffID\r\n"
				+ "WHERE Staff.Department = ? AND Staff.ID<>?\r\n"
				+ "GROUP BY \r\n"
				+ "Staff.ID, Staff.First_Name, Staff.Last_Name, Staff.Email , Staff.Phone_Number";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st = cnn.prepareStatement(query);
			st.setString(1, department);
			st.setInt(2, id);
			rs = st.executeQuery();
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResultSet select(String param1, String param2) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
