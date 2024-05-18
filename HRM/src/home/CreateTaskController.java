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
import dap.DAPTask;

public class CreateTaskController implements Initializable {

	@FXML
	private BorderPane createTaskPane;

	@FXML
	private GridPane crt_GridPane;

	@FXML
	private FontIcon crt_IkonAddStaff;

	@FXML
	private FontIcon crt_IkonContents;

	@FXML
	private FontIcon crt_IkonFinish;

	@FXML
	private FontIcon crt_IkonFrom;

	@FXML
	private FontIcon crt_IkonStart;

	@FXML
	private FontIcon crt_IkonTitle;

	@FXML
	private VBox crt_PaneTop;

	@FXML
	private DatePicker crt_PickFinishDate;

	@FXML
	private HBox crt_PickFinishTime;

	@FXML
	private TextField crt_PickHoursFinish;

	@FXML
	private TextField crt_PickHoursStart;

	@FXML
	private TextField crt_PickMinutesFinish;

	@FXML
	private TextField crt_PickMinutesStart;

	@FXML
	private DatePicker crt_PickStartDate;

	@FXML
	private HBox crt_PickStartTime;

	@FXML
	private TextArea crt_TextArea;

	@FXML
	private HBox crt_bottomPane;

	@FXML
	private HBox crt_lbTitleTo;

	@FXML
	private Button crt_btnAddStaff;

	@FXML
	private Button crt_btnCancel;

	@FXML
	private Button crt_btnCreateTask;

	@FXML
	private Label crt_lbContents;

	@FXML
	private Label crt_lbFrom;

	@FXML
	private Label crt_lbStart;

	@FXML
	private Label crt_lbTitleFrom;

	@FXML
	private Label crt_lbTo;

	@FXML
	private Label crt_lbTopTitle;

	@FXML
	private TextField crt_txtTitle;

	@FXML
	private FontIcon iKonClockFinish;

	@FXML
	private FontIcon iKonClockStart;

	@FXML
	private Button crt_btnClearStaff;

	/**
	 * Khai báo các biến instance
	 */
	ArrayList<String> selectedItems = new ArrayList<String>();
	String mode = "Add Tasks";
	TaskItem taskItem = null;

	@FXML
	void hanleActionCreateTask(ActionEvent event) {
		if (event.getSource() == crt_btnAddStaff) {
			popupOverAddUser();
		} else if (event.getSource() == crt_btnClearStaff) {
			selectedItems.clear();

			crt_lbTitleTo.getChildren().clear();
		} else if (event.getSource() == crt_btnCreateTask) {
			if (checkValidation()) {
				createTask();
			}
		} else if (event.getSource() == crt_btnCancel) {
			
		}
	}

	public void setMode(String mode, TaskItem taskItem) {
		this.taskItem = taskItem;
		this.mode = mode;
		if (mode.equals("Edit Task")) {
			crt_lbTopTitle.setText(mode);
			crt_lbTopTitle.setStyle("-fx-text-fill:#FF4D00");
			crt_btnCreateTask.setText(mode);
			crt_PaneTop.setStyle("-fx-background-color:#FFDFB6");
			crt_txtTitle.setText(taskItem.getTitle());

			crt_PickStartDate.setValue(taskItem.getStartDate().toLocalDate());
			crt_PickHoursStart.setText(String.valueOf(taskItem.getStartTime().toLocalTime().getHour()));
			crt_PickMinutesStart.setText(String.valueOf(taskItem.getStartTime().toLocalTime().getMinute()));

			crt_PickFinishDate.setValue(taskItem.getFinishDate().toLocalDate());
			crt_PickHoursFinish.setText(String.valueOf(taskItem.getFinishTime().toLocalTime().getHour()));
			crt_PickMinutesFinish.setText(String.valueOf(taskItem.getFinishTime().toLocalTime().getMinute()));

			crt_TextArea.setText(taskItem.getContent());

			DAPTask dap = new DAPTask();
			ResultSet rs = dap.selectAssignees(taskItem.getId());
			selectedItems.clear();
			try {
				while (rs.next()) {
					String st = "ID:" + rs.getInt("StaffID") + "- Email:" + rs.getString("email");
					selectedItems.add(st);
					crt_lbTitleTo.getChildren().addAll(new Label(st));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dap.close();
		}
	}

	public Button returnBtnCancel() {
		return crt_btnCancel;
	}
	
	public Button returnBtnCreateTask() {
		return crt_btnCreateTask;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		crt_lbTitleFrom.setText(Main.userLogin.getEmail());

		crt_PickFinishDate.setValue(LocalDate.now());
		crt_PickStartDate.setValue(LocalDate.now());

		checkFormatTime(crt_PickHoursFinish, 23);
		checkFormatTime(crt_PickMinutesFinish, 59);

		checkFormatTime(crt_PickHoursStart, 23);
		checkFormatTime(crt_PickMinutesStart, 59);

		// checkFormatDate(crt_PickStartDate);
		// checkFormatDate(crt_PickFinishDate);

	}

	/**
	 * Phương thức @checkFormatTime dùng để kiểm tra giá trị nhập vào ô thời gian
	 * 
	 * @param textField:
	 * @param range      : nhận giá trị 23 đối với giờ hoặc 59 đối với phut
	 */
	private void checkFormatTime(TextField textField, int range) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d]", ""));
			}

			try {
				int value = Integer.parseInt(textField.getText());
				if (value < 0 || value > range) {
					textField.setText(oldValue); // Khôi phục giá trị cũ nếu không hợp lệ
				}
			} catch (NumberFormatException e) {
				// Không làm gì nếu giá trị không phải số
			}

		});
	}

	/**
	 * Phương thức @checkFormatDate dùng để kiểm tra giá trị nhập vào ô thời gian
	 * 
	 * @param date:
	 * @param range : nhận giá trị theo định dạng dd/MM/yyyy
	 */
	private void checkFormatDate(DatePicker datePicker) {
		datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				// Chuyển đổi giá trị nhập vào thành đối tượng LocalDate
				LocalDate date = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				// Kiểm tra xem giá trị có hợp lệ không
				if (!date.equals(datePicker.getValue())) {
					datePicker.setValue(date);
				}
			} catch (Exception e) {
				// Đặt giá trị của DatePicker về ngày hôm nay
				datePicker.setValue(null);
			}
		});
	}

	/**
	 * Phương thức @popupOverAddUser được sử dụng để hiển thị popup thêm nhân sự vào
	 * task Phương thức này được load lên từ @PopOverAddStaff Dữ liệu sau khi được
	 * chọn sẽ gán cho ArrayList @selectedItems
	 */
	private void popupOverAddUser() {

		PopOver popOver;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/PopOverAddStaff.fxml"));

			popOver = new PopOver(loader.load());
			popOver.setTitle("Add Staff");
			popOver.show(crt_btnAddStaff);
			PopOverAddStaffController controller = loader.getController();

			popOver.setOnHidden(e -> {
				String[] selected = controller.getStaffAddValue();
				// Xử lý giá trị đã lấy được
				if (selected != null) {
					for (String string : selected) {
						selectedItems.add(string);

						crt_lbTitleTo.getChildren().addAll(new Label(string));
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Phương thức @createTask được sử dụng để tạo và cập nhật Task Item cho nhân
	 * viên
	 */
	private void createTask() {
		int countTask = 0;
		int countStaff = 0;
		int insertedID = 0;
		DAPTask dap = new DAPTask();
		ResultSet rs;
		int count = 0;

		// Khối lệnh thực hiện việc Create Task
		if (crt_btnCreateTask.getText().equals("Create Task")) {
			rs = dap.insertTask(crt_txtTitle.getText(), Date.valueOf(crt_PickStartDate.getValue()),
					formatTime(crt_PickHoursStart.getText(), crt_PickMinutesStart.getText()),
					Date.valueOf(crt_PickFinishDate.getValue()),
					formatTime(crt_PickHoursFinish.getText(), crt_PickMinutesFinish.getText()), crt_TextArea.getText(),
					Main.userLogin.getId());
			try {
				while (rs.next()) {
					insertedID = rs.getInt(1);
					countTask += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if ((insertedID != 0) && (selectedItems != null)) {
				for (String str : selectedItems) {
					String[] st = str.split("[:-]");
					countStaff += dap.insertStaffTask(Integer.parseInt(st[1]), insertedID);
					dap.insertTaskExecution(Integer.parseInt(st[1]),insertedID,"Doing");
				}
			}

			if (countTask == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Create Task Failded");
				alert.show();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setTitle("Congratulation!");
				alert.setContentText("Create Task Result: \n" + "Tasks Added: " + countTask + "\n Staff Execution Task:"
						+ countStaff);				
				
				alert.show();
				
				if(alert.isShowing()) {
					System.out.println("Da show");
					crt_btnCreateTask.setText("Created");
					crt_btnCreateTask.setDisable(true);
					crt_btnCancel.setText("Back To Home");
				}
			}
			dap.close();
		}
		// Khối lệnh thực hiện việc cập nhật Task
		else if (crt_btnCreateTask.getText().equals("Edit Task")) {
			count = dap.updateTask(taskItem.getId(), crt_txtTitle.getText(), Date.valueOf(crt_PickStartDate.getValue()),
					formatTime(crt_PickHoursStart.getText(), crt_PickMinutesStart.getText()),
					Date.valueOf(crt_PickFinishDate.getValue()),
					formatTime(crt_PickHoursFinish.getText(), crt_PickMinutesFinish.getText()), crt_TextArea.getText());
			
			dap.deleteStaffTask(taskItem.getId());
			
			if (selectedItems.size() !=0) {
				for (String str : selectedItems) {
					String[] st = str.split("[:-]");
					
					countStaff += dap.insertStaffTask(Integer.parseInt(st[1]), taskItem.getId());
					dap.insertTaskExecution(Integer.parseInt(st[1]),taskItem.getId(),"Doing");
				}
			}

			if (count == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Edit Task Failded");
				alert.show();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Congratulation!");
				alert.setContentText("Update Task Result: \n" + "Tasks Added: " + countTask + "\n Staff Execution Task:"
						+ countStaff);
				alert.show();
			}
		}

		
	}

	/**
	 * @formatTime Phương thức này trả về định dạng Thời gian sql.Time
	 * @param hours
	 * @param minutes
	 * @return
	 */
	private Time formatTime(String hours, String minutes) {
		String timeString = hours + ":" + minutes;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		try {
			java.util.Date date = sdf.parse(timeString);
			Time sqlTime = new Time(date.getTime());
			return sqlTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Time.valueOf(LocalTime.now());
	}

	/**
	 * Phương thức @checkValidation được sử dụng để kiểm tra các trường trong
	 * CreateTask đã được nhập
	 * 
	 */
	private boolean checkValidation() {
		boolean check = true;
		if (crt_txtTitle.getText().isBlank() || crt_txtTitle.getText().isEmpty()) {
			checkValidationMessage(crt_txtTitle, "The Title must not be left EMPTY");
			check = false;
		}
		if (selectedItems.size() == 0) {
			checkValidationMessage(crt_lbTo, "Please select the Employee to perform the task");
			check = false;
		}
		if (crt_PickFinishDate.getValue().isBefore(LocalDate.now())) {
			checkValidationMessage(crt_PickFinishDate, "The end date is invalid.");
			check = false;
		}
		if (crt_PickFinishDate.getValue().isBefore(crt_PickStartDate.getValue())) {
			checkValidationMessage(crt_PickStartDate, "The start date is invalid.");
			check = false;
		}
		if (crt_PickHoursStart.getText().isEmpty()) {
			checkValidationMessage(crt_PickHoursStart, "Please enter the start hour of the task");
			check = false;
		}
		if (crt_PickMinutesStart.getText().isEmpty()) {
			checkValidationMessage(crt_PickHoursStart, "Please enter the start minute of the task");
			check = false;
		}
		if (crt_PickHoursFinish.getText().isEmpty()) {
			checkValidationMessage(crt_PickHoursFinish, "Please enter the finish hour of the task");
			check = false;
		}
		if (crt_PickMinutesFinish.getText().isEmpty()) {
			checkValidationMessage(crt_PickHoursFinish, "Please enter the Finish minute of the task");
			check = false;
		}
		if (crt_TextArea.getText().isEmpty()) {
			checkValidationMessage(crt_TextArea, "The Contents must not be left EMPTY");
			check = false;
		}
		
		return check;
	}

	private void checkValidationMessage(Control ctr, String mess) {

		Label lable = new Label(mess);
		lable.setStyle("-fx-font-size: 14; -fx-text-fill: #FF4343; -fx-padding: 0 0 0 10");
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color: transparent");
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setPrefSize(250, 16);
		vbox.getChildren().add(lable);
		try {
			PopOver popover = new PopOver(vbox);
			popover.setTitle("Validation Message");
			popover.setPrefHeight(12);

			popover.show(ctr);

		} catch (Exception e) {
			e.getStackTrace();
		}

	}
}