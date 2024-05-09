package home;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.ListSelectionView;

import application.Main;
import dap.DAPCreateTask;

public class PopOverAddStaffController implements Initializable{
	@FXML
	private BorderPane popOverpane;
	@FXML
	private Button popOverbtnAdd;
	@FXML
	private ListSelectionView<String> listSelectionViewPane;
	
	String[] staffAddValue;
	
	// Event Listener on Button[#popOverbtnAdd].onAction
	@FXML
	public void handlePopOverbtn(ActionEvent event) {
		System.out.println("Da nhan");
		staffAddValue = listSelectionViewPane.getTargetItems().toArray(new String[0]);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Complete!");
		alert.setContentText("Added Staff for the Task");
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> allItems = new ArrayList<String>();
		
		
		DAPCreateTask dap = new DAPCreateTask();
		ResultSet rs = dap.select(Main.userLogin.getDepartment(), String.valueOf(Main.userLogin.getId()));
		try {
			while(rs.next()) {
				allItems.add("ID:"+rs.getInt("ID")+"- Email:"+rs.getString("email"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dap.close();
		listSelectionViewPane.getSourceItems().addAll(allItems);
		
	}
	
	public String[] getStaffAddValue() {
		return staffAddValue;
	}
}
