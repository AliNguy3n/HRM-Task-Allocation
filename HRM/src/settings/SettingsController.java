package settings;

/**
* @author Duc Linh
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import fio.FIOCore;

public class SettingsController implements Initializable {

    @FXML
    private Button btnApplySettings;

    @FXML
    private CheckBox checkBoxHideNotifications;

    @FXML
    private ChoiceBox<String> choiceBoxStartup;

    @FXML
    private GridPane gridPaneDatabase;

    @FXML
    private GridPane gridPaneGeneral;

    @FXML
    private FontIcon iKonSettings;

    @FXML
    private Label lbDatabaseDesc;

    @FXML
    private Label lbDatabaseName;

    @FXML
    private Label lbHideNotifications;

    @FXML
    private Label lbPageStartup;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbPort;

    @FXML
    private Label lbServerName;

    @FXML
    private Label lbTitleDatabase;

    @FXML
    private Label lbTitleGeneral;

    @FXML
    private Label lbTitleSettings;

    @FXML
    private Label lbUsername;

    @FXML
    private AnchorPane settingPaneTop;

    @FXML
    private BorderPane settingsPane;

    @FXML
    private VBox settingsPaneVBox;

    @FXML
    private TextField txtDatabaseName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtServerName;

    @FXML
    private TextField txtUserName;
    /**
	 * 1.General:
	 *	pageStartup
	 *	notification
	 * 2.Database:
	 * 	databaseType;
	 *	serverName;
	 *	usernameServer;
	 *	passwordServer;
	 *	databaseName;
	 *	port;
	 */
    String[] pageChoice = {"Task Management","Manage","Accounts","Settings"};
    @FXML
    void handleSettings(ActionEvent event) {
    	if(event.getSource()==btnApplySettings) { 		
    		
    		Alert newAlert = new Alert(AlertType.CONFIRMATION);
    		newAlert.setTitle("Save Your Changes?");
    		newAlert.setContentText("Would you like to confirm that all the above changes will be saved!");
    		ButtonType buttonTypeYes = new ButtonType("Yes");
    		ButtonType buttonTypeNo = new ButtonType("No");
    		newAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
    		
    		Optional<ButtonType> result = newAlert.showAndWait();
    		if(result.isPresent() && result.get().equals(buttonTypeYes)) {
    			ObSettings newObSettings = new ObSettings();
        		newObSettings.setValue("pageStartup", choiceBoxStartup.getValue());
        		newObSettings.setValue("notification", checkBoxHideNotifications.selectedProperty().getValue().toString());
        		newObSettings.setValue("serverName", txtServerName.getText());
        		newObSettings.setValue("usernameServer", txtUserName.getText());
        		newObSettings.setValue("passwordServer", txtPassword.getText());
        		newObSettings.setValue("databaseName", txtDatabaseName.getText());
        		newObSettings.setValue("port", txtPort.getText());
        		if(new FIOCore().write("src/data/settings.dat", false, newObSettings.getSettings())) {
        			Alert alertIO = new Alert(AlertType.INFORMATION);
    				alertIO.setTitle("Congratulation!");
    				alertIO.setContentText("Save complete! Restart program to Apply new settings");
    				alertIO.show();
        		}else {
        			Alert alertIO = new Alert(AlertType.ERROR);
    				alertIO.setTitle("ERROR");
    				alertIO.setContentText("Error occurred when saving settings!");
    				alertIO.show();
        		}
    		}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choiceBoxStartup.getItems().addAll(pageChoice);
		choiceBoxStartup.setValue(Main.obSettings.getValue("pageStartup"));
		checkBoxHideNotifications.setSelected(Boolean.parseBoolean(Main.obSettings.getValue("notification")));
		txtDatabaseName.setText(Main.obSettings.getValue("databaseName"));
		txtServerName.setText(Main.obSettings.getValue("serverName"));
		txtUserName.setText(Main.obSettings.getValue("usernameServer"));
		txtPassword.setText(Main.obSettings.getValue("passwordServer"));
		txtDatabaseName.setText(Main.obSettings.getValue("databaseName"));
		txtPort.setText(Main.obSettings.getValue("port"));
}
}

