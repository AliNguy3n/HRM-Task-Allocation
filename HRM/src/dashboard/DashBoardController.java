package dashboard;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;

public class DashBoardController implements Initializable{
    @FXML
    private Button btnLogout;
    
    @FXML
    private FontIcon iKonLogout;
    
    @FXML
    private ToggleGroup NavBar;

    @FXML
    private ToggleButton btnAccounts;

    @FXML
    private ToggleButton btnHome;

    @FXML
    private ToggleButton btnManage;

    @FXML
    private ToggleButton btnSettings;

    @FXML
    private BorderPane dashBoardPane;

    @FXML
    private BorderPane dashboardView;

    @FXML
    private FontIcon iKonAccounts;

    @FXML
    private FontIcon iKonHome;

    @FXML
    private FontIcon iKonManage;

    @FXML
    private FontIcon iKonSettings;

    @FXML
    private ImageView imageLogo;

    @FXML
    private ImageView imageTitle;

    @FXML
    private VBox navDashBoardLeft;

    @FXML
    private HBox paneTitleNav;

    @FXML
    void handleDashBoardNav(ActionEvent event) {
    	if(event.getSource()==btnHome) {
    		if(Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() ==2) {
    			displayPaneElement("/home/TaskManagementForManager.fxml");
    		}
    		else {
    			displayPaneElement("/home/TaskManagementForStaff.fxml");
    		}
    		
    	}
    	if(event.getSource()==btnManage) {
    		displayPaneElement("/manage/Manage.fxml");
    	}
    	if(event.getSource()==btnAccounts) {
    		displayPaneElement("/accounts/Accounts.fxml");
    	}
    	if(event.getSource()==btnSettings) {
    		displayPaneElement("/settings/Settings.fxml");
    	}
    }
    private void displayPaneElement(String pane) {
    	try {
			dashboardView.setCenter(FXMLLoader.load(getClass().getResource(pane)));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		switch (Main.obSettings.getValue("pageStartup")) {
		case "Accounts" -> displayPaneElement("/accounts/Accounts.fxml");
		case "Manage" -> displayPaneElement("/manage/Manage.fxml");
		case "Settings" -> displayPaneElement("/settings/Settings.fxml");
		default -> {
			if(Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() ==2) {
    			displayPaneElement("/home/TaskManagementForManager.fxml");
    		}
    		else {
    			displayPaneElement("/home/TaskManagementForStaff.fxml");
    		}
		}	
		}
	}
	public Button getButtonLogout() {
		return btnLogout;
	}
}
