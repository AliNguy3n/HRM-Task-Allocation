package dap;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
* @author Duc Linh
*/
public class DAPTask extends DAPCore{
	
	/**
	 *@select phương thức select trả về Danh sách nhân sự của mỗi phòng ban dựa trên phòng ban của người quản lý.
	 * Phương thức này được sử dụng trong việc nạp dữ liệu cho selectBox Create Task
	 */
	@Override
	public ResultSet select(String department, String id) {
		String query ="select [Email],[ID] from [Staff] where [Department] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, department);
			//st.setInt(2, Integer.parseInt(id));
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @selectAssignees phương thức select trả về Danh sách nhân sự của Task đang được chỉnh sửa
	 * Phương thức này được sử dụng trong việc nạp dữ liệu cho selectBox Edit Task
	 */
	public ResultSet selectAssignees(int taskID) {
		
		String query ="SELECT  StaffTask.StaffID, Staff.Email, Staff.First_Name, Staff.Last_Name\r\n"
				+ "FROM StaffTask\r\n"
				+ "INNER JOIN Staff ON StaffTask.StaffID = Staff.ID\r\n"
				+ "WHERE StaffTask.TaskID = ?\r\n";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setInt(1, taskID);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @insertTask được sử dụng để chèn thêm một Task mới vào bảng @Task
	 */
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
	
	/**
	 * @insertStaffTask được sử dụng để chèn thêm một bản ghi mới vào bảng @StaffTask
	 */
	public int insertStaffTask(int staffID, int taskID) {
		String query ="insert into [StaffTask] values (?,?)";
		
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		
		try {
			st= cnn.prepareStatement(query);
			st.setInt(1, staffID);
			st.setInt(2, taskID);
			
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int insertTaskExecution(int staffID, int taskID, String status) {
		String query ="insert into [Task_Execution](TaskID,StaffID,Status) values (?,?,?)";
		
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		
		try {
			st= cnn.prepareStatement(query);
			st.setInt(2, staffID);
			st.setInt(1, taskID);
			st.setString(3, status);
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @updateTask được sử dụng để @update thêm một @Task trong bảng @Task
	 */
	public Integer updateTask(int taskID,String title, Date startDate, Time startTime, Date endDate, Time endTime, 
			String content) {
			
			String query ="UPDATE [Task]"
					+ "   SET Title = ? ,"
					+ "   Started_Date = ? ,"
					+ "	  Started_Time = ? ,"
					+ "   Ended_Date = ? ,"
					+ "   Ended_Time = ?,"
					+ "	  Content = ? "
					+ " WHERE ID = ?";
			
			cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
			
			try {
				st= cnn.prepareStatement(query);
				st.setString(1, title);
				st.setDate(2, startDate);
				st.setTime(3, startTime);
				st.setDate(4, endDate);
				st.setTime(5, endTime);
				st.setString(6, content);
				st.setInt(7, taskID);
				count = st.executeUpdate();
		
				return count;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
	
	/**
	 * @updateStaffTask 
	 */
//	public int updateStaffTask(int StaffID, int TaskID) {
//		System.out.println("Da xoa: "+ delete(TaskID));		
//		return insertStaffTask(StaffID, TaskID);
//	}
	
	@Override
	public int insert() {
		
		return 0;
	}

	/**
	 *@update được sử dụng để kích hoạt Trigger 
	 */
	@Override
	public int update() {
		String query ="UPDATE [Task] SET [Check] = ? ";
		java.util.Date now = new java.util.Date();
		Timestamp tstp = new Timestamp(now.getTime());
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		
		try {
			st= cnn.prepareStatement(query);
			st.setTimestamp(1, tstp);
			count = st.executeUpdate();
			st.close();
			cnn.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	private int deleteFromTable(String tableName, String conditionColumn, int taskID) {
        String query = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";
        cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
        
        try  {
        	st = cnn.prepareStatement(query);
            st.setInt(1, taskID);
            count = st.executeUpdate();
            st.close();
            cnn.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteStaffTask(int TaskID) {
        return deleteFromTable("[StaffTask]", "[TaskID]", TaskID);
    }

    public int deleteTask(int TaskID) {
        return deleteFromTable("[Task]", "[ID]", TaskID);
    }

    public int deleteTaskExecution(int TaskID) {
        return deleteFromTable("[Task_Execution]", "[TaskID]", TaskID);
    }

    public int deleteTaskRequest(int TaskID) {
    	return deleteFromTable("[TaskRequest]", "[TaskID]", TaskID);
    }
    public int deleteTaskEvaluation(int TaskID) {
    	return deleteFromTable("[Evaluation]", "[TaskID]", TaskID);
    }
    @Override
    public int delete(int TaskID) {
        int status = 1;
        try {
        	deleteTaskEvaluation(TaskID);
        	deleteTaskRequest(TaskID);
            if (deleteTaskExecution(TaskID) == 0) { status = 0;};
            if (deleteStaffTask(TaskID) == 0) { status = 0;};
            if (deleteTask(TaskID) == 0){ status = 0;};
            
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
