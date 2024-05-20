package dap;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* @author Duc Linh
* 
* @DAPTaskReport lớp này cung cấp dữ liệu cho @TaskReportController
* hoạt động chủ yếu trên bảng @Task_Execution
*/
public class DAPTaskReport extends DAPCore{

	
	/**
	 * @select Phương thức này được sử dụng để select các Task Execution từ bảng @Task_Execution
	 * giá trị nhận được được sử dụng để nạp vào phần tử @TaskReport
	 * @param staffID : ID của nhân viên được giao Task
	 * @param taskID  : ID của task
	 * @return giá trị trả về gồm [ID],[TaskID],[StaffID],[Status],[Report]
	 */
	public ResultSet select(int staffID, int taskID) {
		String query ="SELECT * FROM [Task_Execution] WHERE [StaffID] = ? AND [TaskID] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);

			st.setInt(1, staffID);
			st.setInt(2, taskID);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @selectTaskRequest phương thức này được sủ dụng để select tất cả các Request từ nhân một nhân sự dựa trên các tham số
	 * @param staffIDFrom : ID của Người gửi Request
	 * @param staffIDTo	  : ID của Người nhận Requst
	 * @param taskID	  : ID của Task đang thực hiện
	 * @return giá trị trả về gồm TaskRequest.ID, TaskRequest.TaskID, TaskRequest.[From] ,TaskRequest.[To], TaskRequest.Request,
	 *  TaskRequest.[Timestamp], TaskRequest.Seem, Staff.First_Name,Staff.Last_Name
	 */
	public ResultSet selectTaskRequest(int staffIDFrom,int staffIDTo, int taskID) {
		String query ="SELECT TR.ID, TR.TaskID, TR.[From] ,TR.[To], TR.Request, TR.[Timestamp],\r\n"
				+ "TR.Seem, ST.First_Name,ST.Last_Name\r\n"
				+ "FROM [TaskRequest] TR \r\n"
				+ "INNER JOIN [Staff] ST ON ST.[ID] = TR.[From]\r\n"
				+ "WHERE TaskID = ?\r\n"
				+ "AND [FROM] IN (?, ?) \r\n"
				+ "AND [TO] IN (?, ?) \r\n"
				+ "ORDER BY [Timestamp]";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);

			st.setInt(1, taskID);
			st.setInt(2, staffIDFrom);
			st.setInt(3, staffIDTo);
			st.setInt(4, staffIDFrom);
			st.setInt(5, staffIDTo);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Phương thức @selectEvaluation được sử dụng để lấy thông tin về đánh giá của Task thông qua
	 * @param taskID
	 * @param staffID
	 * @return
	 */
	public ResultSet selectEvaluation(int taskID, int staffID) {
		String query ="SELECT * FROM [Evaluation] WHERE [TaskID] = ? AND [StaffID] =?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);

			st.setInt(1, taskID);
			st.setInt(2, staffID);
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
	
	/**
	 * @insertRequest Phương thức này được sử dụng để gửi đi một request trong phần tử @TaskReport
	 * @param staffIDFrom :ID của Người gửi Request
	 * @param staffIDTo   :ID của Người nhận Requst
	 * @param taskID	  :ID của Task đang thực hiện
	 * @param request	  : Nội dung của Request
	 * @return
	 */
	public int insertRequest(int staffIDFrom, int staffIDTo, int taskID, String request) {
		String query ="INSERT INTO [TaskRequest] ([TaskID],[From],[To],[Request])\r\n"
					 + "VALUES(?,?,?,?)";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setInt(1, taskID);
			st.setInt(2, staffIDFrom);
			st.setInt(3, staffIDTo);
			st.setString(4, request);						
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Phương thức @insertEvaluation được sử dụng để chèn một đánh giá công việc vào bảng Evaluation 
	 * @param taskID
	 * @param staffID
	 * @param mark
	 * @param comment
	 * @return
	 */
	public int insertEvaluation(int taskID, int staffID, double mark, String comment) {
		String query ="INSERT INTO Evaluation([TaskID],[StaffID],[Mark],[Comment]) \r\n"
				+ "  VALUES(?,?,?,?)";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setInt(1, taskID);
			st.setInt(2, staffID);
			st.setFloat(3,(float) mark);
			st.setString(4, comment);						
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateReport(int executionID, String status, String report) {
		String query ="UPDATE [Task_Execution] \r\n"
				+ "  SET [Status] = ?, [Report] =?\r\n"
				+ "  WHERE ID =?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, status);
			st.setString(2, report);
			st.setInt(3, executionID);
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateAccepting(int executionID, String status) {
		String query ="UPDATE [Task_Execution] \r\n"
				+ "  SET [Status] = ?\r\n"
				+ "  WHERE ID =?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setString(1, status);
	
			st.setInt(2, executionID);
			count = st.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateEvaluation(int taskID, int staffID, double mark, String comment) {
		String query ="UPDATE [Evaluation] \r\n"
				+ "  SET [Mark] = ?, [Comment] = ?\r\n"
				+ "  WHERE [TaskID] =? AND [StaffID] = ?";
		cnn = DBConnect.makeConnection(serverName, port, database, usernameServer, passwordServer);
		try {
			st= cnn.prepareStatement(query);
			st.setInt(3, taskID);
			st.setInt(4, staffID);
			st.setDouble(1, mark);
			st.setString(2, comment);
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
	

	public void closeCnn() {
		try {
			st.close();
			cnn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
}
