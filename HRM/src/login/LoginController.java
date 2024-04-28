
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

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
    void handleLogin(ActionEvent event) {
    	if(event.getSource()==btnLogin) {
    		if(checkLogin()) {
    			try {
					Parent dashBoard = FXMLLoader.load(getClass().getResource("/dashboard/DashBoard.fxml"));
					Scene newScene = new Scene(dashBoard);
					Stage newStage = new Stage();
					newStage.setTitle("HRM & Task Allocation Appliction");
					newStage.setScene(newScene);
					newStage.show();
					borderPaneLoginMain.getScene().getWindow().hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    private boolean checkLogin() {
    	boolean check = true;
    	
    	return check;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
	}
    
}

