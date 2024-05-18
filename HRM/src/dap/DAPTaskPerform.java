package dap;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
* @author Duc Linh
* @DAPHone chứa tất cả các phương thức cần cho truy xuất database cho Task Management Page
*/
public class DAPTaskPerform extends DAPCore {
	
	/**
	 * @selectTask phương thức trả về số lượng Task của phòng ban đang thực hiện theo tham số là khoảng thời gian
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
			+ "WHERE Task.Assignedby = ?  AND Task.Started_Date >= ? AND Task.Started_Date <= ? \r\n"
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
	 * @selectStaffTask phương thức trả về số lượng Task đang thực hiện của một nhân viên theo tham số là khoảng thời gian
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 */
	public ResultSet selectStaffTask(int id, Date start, Date end) {
		String query = "SELECT Task.ID, Task.Title, Task.Content, Task.Started_Date, Task.Started_Time,\r\n"
				+ "Task.Ended_Date, Task.Ended_Time, Task.Assignedby, Staff.Last_Name,\r\n"
				+ "Task_Execution.[Status]\r\n"
				+ "FROM Task\r\n"
				+ "INNER JOIN StaffTask ON Task.ID = StaffTask.TaskID\r\n"
				+ "INNER JOIN Staff ON Staff.ID = StaffTask.StaffID\r\n"
				+ "INNER JOIN Task_Execution ON Task_Execution.TaskID= Task.ID  AND Task_Execution.StaffID = Staff.ID\r\n"
				+ "WHERE StaffTask.StaffID = ?  AND Task.Started_Date >=?  AND Task.Started_Date <= ? \r\n"
				+ "GROUP BY Task.ID, Task.Title, Task.Content, Task.Started_Date, Task.Started_Time,\r\n"
				+ "Task.Ended_Date, Task.Ended_Time, Task.Assignedby, Staff.Last_Name,Task_Execution.[Status]";
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
				+ "Staff.Phone_Number, \r\n"
				+ "COUNT(DISTINCT StaffTask.TaskID) AS TotalTasks,\r\n"
				+ "SUM(CASE WHEN Task_Execution.Status = 'Complete' THEN 1 ELSE 0 END) AS TasksCompleted ,\r\n"
				+ "SUM(CASE WHEN Task_Execution.Status = 'Delay' THEN 1 ELSE 0 END) AS TasksDelay \r\n"
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
	
	/**
	 * @selectDataForChart Phương thức này được sử dụng để nạp dữ liệu đầu vào cho Biểu đồ Donut trong TaskManagement
	 * dữ liệu được tổng hợp bao gồm TaskTotal, TaskComplete, TaskDelay
	 * 
	 * @param staffID : ID của nhân sự quản lý hoặc thực hiện các Task đó 
	 * @return
	 */
	public ResultSet selectDataForChart(int staffID) {
		String query = "SELECT Task.ID, Task_Execution.Status\r\n"
				+ "INTO #TemporaryTable\r\n"
				+ "FROM Task\r\n"
				+ "LEFT JOIN Task_Execution ON Task.ID = Task_Execution.TaskID \r\n"
				+ "WHERE Task.Assignedby = ? \r\n"
				+ "AND MONTH(Started_Date) = MONTH(GETDATE()) AND YEAR(Started_Date) = YEAR(GETDATE())\r\n"
				+ "GROUP BY Task.ID, Task.Title, Task_Execution.Status\r\n"
				+ "SELECT \r\n"
				+ "    COUNT(*) AS TotalRows,\r\n"
				+ "    SUM(CASE WHEN #TemporaryTable.Status = 'Delay' THEN 1 ELSE 0 END) AS DelayedRows,\r\n"
				+ "	SUM(CASE WHEN #TemporaryTable.Status = 'Complete' THEN 1 ELSE 0 END) AS CompeledRows\r\n"
				+ "FROM #TemporaryTable";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st = cnn.prepareStatement(query);
			st.setInt(1, staffID);
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
	public int delete(int parm) {
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
