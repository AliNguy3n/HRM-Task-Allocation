package home;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPStaff;
import dap.DAPTask;

public class TaskManagementController implements Initializable {

	@FXML
	private BorderPane TaskManagementPane;

	@FXML
	private GridPane tmt_GridPane;

	@FXML
	private FontIcon tmt_IkonAddStaff;

	@FXML
	private FontIcon tmt_IkonContents;

	@FXML
	private FontIcon tmt_IkonFinish;

	@FXML
	private FontIcon tmt_IkonFrom;

	@FXML
	private FontIcon tmt_IkonStart;

	@FXML
	private FontIcon tmt_IkonTitle;

	@FXML
	private AnchorPane tmt_PaneTop;

	@FXML
	private DatePicker tmt_PickFinishDate;

	@FXML
	private HBox tmt_PickFinishTime;

	@FXML
	private TextField tmt_PickHoursFinish;

	@FXML
	private TextField tmt_PickHoursStart;

	@FXML
	private TextField tmt_PickMinutesFinish;

	@FXML
	private TextField tmt_PickMinutesStart;

	@FXML
	private DatePicker tmt_PickStartDate;

	@FXML
	private HBox tmt_PickStartTime;

	@FXML
	private TextArea tmt_TextArea;

	@FXML
	private HBox tmt_bottomPane;

	@FXML
	private HBox tmt_lbTitleTo;

	@FXML
	private Button tmt_btnAddStaff;

	@FXML
	private Button tmt_btnCancel;

	@FXML
	private Button tmt_btnCreateTask;

	@FXML
	private Label tmt_lbContents;

	@FXML
	private Label tmt_lbFrom;

	@FXML
	private Label tmt_lbStart;

	@FXML
	private Label tmt_lbTitleFrom;

	@FXML
	private Label tmt_lbTo;

	@FXML
	private Label tmt_lbTopTitle;

	@FXML
	private TextField tmt_txtTitle;

	@FXML
	private FontIcon iKonClockFinish;

	@FXML
	private FontIcon iKonClockStart;

	@FXML
	private Button tmt_btnClearStaff;

	/**
	 * Khai báo các biến instance
	 */
	ArrayList<String> selectedItems = new ArrayList<String>();
	TaskItem taskItem = null;

	@FXML
	void hanleActionCreateTask(ActionEvent event) {

	}

	public void setMode(String mode, TaskItem taskItem) {
		this.taskItem = taskItem;
		tmt_txtTitle.setText(taskItem.getTitle());
		
		DAPStaff dapStaff = new DAPStaff();
		String email="";
		ResultSet r = dapStaff.select(taskItem.getAssignedby());
		
		try {
			while(r.next()) {
				email = r.getString("Email");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		tmt_lbTitleFrom.setText(email);
		dapStaff.close();
		
		tmt_PickStartDate.setValue(taskItem.getStartDate().toLocalDate());
		tmt_PickHoursStart.setText(String.valueOf(taskItem.getStartTime().toLocalTime().getHour()));
		tmt_PickMinutesStart.setText(String.valueOf(taskItem.getStartTime().toLocalTime().getMinute()));

		tmt_PickFinishDate.setValue(taskItem.getFinishDate().toLocalDate());
		tmt_PickHoursFinish.setText(String.valueOf(taskItem.getFinishTime().toLocalTime().getHour()));
		tmt_PickMinutesFinish.setText(String.valueOf(taskItem.getFinishTime().toLocalTime().getMinute()));

		tmt_TextArea.setText(taskItem.getContent());

		DAPTask dap = new DAPTask();
		ResultSet rs = dap.selectAssignees(taskItem.getId());
		selectedItems.clear();
		try {
			while (rs.next()) {
				String st = "ID:" + rs.getInt("StaffID") + "- Email:" + rs.getString("email");
				selectedItems.add(st);
				tmt_lbTitleTo.getChildren().addAll(new Label(st));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dap.close();
	}

	public Button returnBtnCancel() {
		return tmt_btnCancel;
	}
	
	public Button returnBtnCreateTask() {
		return tmt_btnCreateTask;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tmt_txtTitle.setEditable(false);
		tmt_PickStartDate.setEditable(false);
		tmt_PickHoursStart.setEditable(false);
		tmt_PickMinutesStart.setEditable(false);

		tmt_PickFinishDate.setEditable(false);
		tmt_PickHoursFinish.setEditable(false);
		tmt_PickMinutesFinish.setEditable(false);

		tmt_TextArea.setEditable(false);
	}



}