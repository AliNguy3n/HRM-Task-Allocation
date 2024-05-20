package application;

import java.io.IOException;

import fio.FIOCore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import settings.ObSettings;
import user.UserLogin;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	public static ObSettings obSettings = new ObSettings();
	public static UserLogin userLogin = new UserLogin();

	@Override
	public void start(Stage primaryStage) {

		/**
		 * Tìm nạp thông tin của ứng dụng
		 * Đối tượng @obSettings chứa tất cả các thông tin về dữ liệu đăng nhập vào Database và dữ liệu người dùng.
		 * Đối tượng @userLogin chứa các thông tin về người dùng sau khi đã đăng nhập thành công hệ thống.
		 * Sử dụng các trường đã khai báo trong lớp @ObSettings để nạp thông tin cho Package của bạn.
		 * Nếu không tìm thấy thông tin của ứng dụng (Trường hợp ứng dụng chạy lần đầu tiên), thì sẽ thực hiện một trong hai phương án
		 * Phương án 1:Áp dụng với Admin,chuyển hướng thẳng vào dashboard, vào mục Settings để cấu hình hệ thống (Đang xây dựng)
		 * Phương án 2:Áp dụng cho User Nạp thông tin hệ thống thông qua một đoạn mã Hash (Chưa xây dựng)
		 */
		try {
			obSettings.setSettings(new FIOCore().readMap("src/data/settings.dat"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent root=null;

		try {
			if(obSettings.getValue("serverName")!=null) {
				root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			}else {
				obSettings.setValue("pageStartup", "Settings");
				root = FXMLLoader.load(getClass().getResource("/dashboard/DashBoard.fxml"));
			}

			Scene scene = new Scene(root);

			primaryStage.setTitle("Login");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/asset/LogoIconTitle.png")));
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