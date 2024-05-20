
package login;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPLogin;

public class LoginController implements Initializable{

    @FXML
    private AnchorPane anchorPaneLogin;

    @FXML
    private BorderPane borderPaneLoginMain;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox checkboxRememberMe;

    @FXML
    private FontIcon iKonPassword;

    @FXML
    private FontIcon iKonUsername;

    @FXML
    private ImageView imageLogo;

    @FXML
    private Label lbTitleLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void handleLogin(ActionEvent event) throws SQLException {
    	if(event.getSource()==btnLogin) {
    		if(checkLogin()) {
    			try {
					Parent dashBoard = FXMLLoader.load(getClass().getResource("/dashboard/DashBoard.fxml"));
					Scene newScene = new Scene(dashBoard);
					Stage newStage = new Stage();
					newStage.setTitle("HRM & Task Allocation Appliction");
					newStage.getIcons().add(new Image(getClass().getResourceAsStream("/asset/LogoIconTitle.png")));
					newStage.setScene(newScene);
					newStage.show();
					borderPaneLoginMain.getScene().getWindow().hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    private boolean checkLogin() throws SQLException {
    	boolean check = false;
    	
    	if(txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Login Message");
    		alert.setContentText("Username or Password is Empty");
    		alert.showAndWait();
    	}else {
    		DAPLogin login = new DAPLogin();
    		ResultSet res = login.select(txtUsername.getText(), txtPassword.getText());
    		if(res.next()) {
    			Main.userLogin.setId(res.getInt("ID"));
    			Main.userLogin.setFirstname(res.getString("First_Name"));
    			Main.userLogin.setLastname(res.getString("Last_Name"));
    			Main.userLogin.setEmail(res.getString("Email"));
    			Main.userLogin.setUsername(res.getString("User_Name"));
    			Main.userLogin.setStatus(res.getString("Status"));
    			Main.userLogin.setDepartment(res.getString("Department"));
    			Main.userLogin.setPermission(res.getString("Permission"));
    			Main.userLogin.setPhonenumber(res.getString("Phone_Number"));
    			Main.userLogin.setPosition(res.getString("Position"));
    			Main.userLogin.setSalary(res.getFloat("Salary"));
				Main.userLogin.setAvatarPath(res.getString("Avatar"));
				Main.userLogin.setPassword(res.getString("password"));
        		check = true;
        		
        	}else {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Login Message");
        		alert.setContentText("Username or Password not Correct");
        		alert.showAndWait();
        	}
    		login.close();
    	}	
    	return check;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtUsername.setText("nghia@gamil.com");
		txtPassword.setText("123");
	}
    
}

