package login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;

import dap.DAPLogin;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;

public class ForgotPasswordController {
	@FXML
	private BorderPane paneForgotPass;
	@FXML
	private TextField txtUsername;
	@FXML
	private Label lbUsername;
	@FXML
	private TextField txtEmail;
	@FXML
	private Label lbEmail;
	@FXML
	private TextField txtSecrect;
	@FXML
	private Label lbSecrect;
	@FXML
	private Label lbTitleForgotPass;
	@FXML
	private Label lbContentForgotPass;
	@FXML
	private Button btnReset;

	private String email="";
	private String username="";
	private String secrect="";
	
	// Event Listener on Button[#btnReset].onAction
	@FXML
	public void actionhandleResetPassword(ActionEvent event) {
		if(event.getSource() ==btnReset) {
			email = txtEmail.getText();
			username = txtUsername.getText();
			secrect = txtSecrect.getText();
			if(validateValue()) {
				DAPLogin dap = new DAPLogin();
				try {
					if(dap.selectUser(username, email, Float.parseFloat(secrect)).next()) {
						int ramdomPass =(int) Math.round(Math.random()*10000000);
						int count =dap.updatePass(username, String.valueOf(ramdomPass));
						if(count != 0) {
							Alert newAlert = new Alert(AlertType.INFORMATION);
							newAlert.setTitle("Congratilation!");
							newAlert.setHeaderText("Your new Password is:");
							newAlert.setContentText(String.valueOf(ramdomPass));
							newAlert.showAndWait();
							txtEmail.clear();
							txtSecrect.clear();
							txtUsername.clear();
						}
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}
	private boolean validateValue() {
		boolean check = true;
		// Validate cho username;
		if(username.isEmpty() || username.isBlank()) {
			lbUsername.setText("Username: is not Empty");
			check = false;
		}else {
			lbUsername.setText("Username:");
		}
		
		// Validate cho username;
		if(email.isEmpty() || email.isBlank()) {
			lbEmail.setText("Email: is not Empty");
			check = false;
		}
		else if(!email.matches("^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$")) {
			lbEmail.setText("Email: is invalid");
		}
		else {
			lbEmail.setText("Email:");
		}
		
		if(secrect.isEmpty() || secrect.isBlank()) {
			lbSecrect.setText("Secrect question: is not Empty");
			check = false;
		}else {
			lbSecrect.setText("Secrect question: ");
		}

		
		return check;
	}
}
