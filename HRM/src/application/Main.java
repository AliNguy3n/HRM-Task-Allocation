package application;
	
import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.Impl.StaffEvaluationDAOImpl;
import Models.DAO.StaffEvaluationDAO;
import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;
import Models.DTO.StaffEvaluationDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.UserLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main extends Application {

	public static UserLogin userLogin = new UserLogin();

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
	}

	public static void main(String[] args) {
		launch(args);
	}
}
