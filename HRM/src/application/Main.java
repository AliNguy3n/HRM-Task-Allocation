package application;
	
import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
//		testDatabaseDAO();


	}
//	private void testDatabaseDAO(){
//		try {
//			// Kết nối vào cơ sở dữ liệu
//			Connection connection = DBConnection.getConnection();
//
////			Kiêm tra kết nối
//            if (connection != null) {
//                System.out.println("Connected to the database");
//            } else {
//                System.out.println("Failed to connect to the database");
//            }
//
//			// Tạo một DAO
//			StaffListDAO staffDAO = new StaffDAOImpl(connection);
////			File imageFile = new File("C:\\Users\\Admin\\Desktop\\ava.jpg");
////			FileInputStream fis = new FileInputStream(imageFile);
////			String sql = "INSERT INTO staff (first_name, last_name, email, phone_number, department, position,user_name, password, permission, status, salary) VALUES (?, ?, ? , ? , ? ,? , ? , ? , ? , ? , ?)";
//
////			 Thêm một user mới
//			StaffDTO staffDTO = new StaffDTO();
////			staffDTO.setAvatarStream(fis);
//			staffDTO.setFirstName("user1");
//			staffDTO.setLastName("password123");
//			staffDTO.setEmail("@gmail.com");
//			staffDTO.setPhoneNumber("2345");
//			staffDTO.setDepartment("hhh");
//			staffDTO.setPosition("sdfsdf");
//			staffDTO.setUserName("eee");
//			staffDTO.setPassword("password123");
//			staffDTO.setPermission(1L);
//			staffDTO.setStatus("1");
//			staffDTO.setSalary(345F);
//
//			System.out.println(staffDTO);
//
//			staffDAO.addStaff(staffDTO);
////
////			// Update một user
////			User user = userDAO.getUserById(1);
////			user.setPassword("newpassword456");
////			userDAO.updateUser(user);
//////
//////            // Delete một user
//////            userDAO.deleteUser(1);
//////
////			// Lấy tất cả users
//			List<StaffDTO> staffDTOS = staffDAO.getAllStaffs();
//			for (StaffDTO u : staffDTOS) {
//				System.out.println(u);
//			}
//
//			connection.close();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		} catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
