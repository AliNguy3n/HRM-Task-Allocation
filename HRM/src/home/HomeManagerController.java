package home;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import dap.DAPHome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
* @author Duc Linh
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeManagerController implements Initializable{

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnApply;

    @FXML
    private ChoiceBox<String> choiceBoxDate;

    @FXML
    private HBox hboxAvatarUser;

    @FXML
    private BorderPane homePane;

    @FXML
    private ScrollPane homePaneCenter;

    @FXML
    private GridPane homePaneTop;

    @FXML
    private VBox homeVboxCenter;

    @FXML
    private ImageView imageUserName;

    @FXML
    private Label lbDepartment;

    @FXML
    private Label lbManagementPersonnel;

    @FXML
    private Label lbManagementTask;

    @FXML
    private Label lbPersonnelCount;

    @FXML
    private Label lbPoisition;

    @FXML
    private Label lbTaskComplete;

    @FXML
    private Label lbTaskRequires;

    @FXML
    private Label lbTasksMonth;

    @FXML
    private Label lbTitleDepartment;

    @FXML
    private Label lbTitlePersonnelCount;

    @FXML
    private Label lbTitleTasksComplete;

    @FXML
    private Label lbTitleTasksMonth;

    @FXML
    private Label lbUsername;

    @FXML
    private AnchorPane paneManagementPersonnel;

    @FXML
    private AnchorPane paneManagementTask;

    @FXML
    private AnchorPane paneTaskRequires;
    
    @FXML
    private StackPane homeStackCenter;
    
    @FXML
    private VBox homeVboxItems;    
    
    @FXML
    private DatePicker pickDate;

    @FXML
    private TableView<StaffItem> tableManagementPersonnel;

    @FXML
    private TableView<TaskItem> tableManagementTask;

    @FXML
    private TableView<?> tableTaskRequires;

    @FXML
    private TableColumn<StaffItem, String> tmpEmail;

    @FXML
    private TableColumn<StaffItem, Integer> tmpID;

    @FXML
    private TableColumn<StaffItem, String> tmpName;

    @FXML
    private TableColumn<StaffItem, String> tmpPhone;

    @FXML
    private TableColumn<StaffItem, Integer> tmpTaskComplete;

    @FXML
    private TableColumn<StaffItem, Integer> tmpTaskDelay;

    @FXML
    private TableColumn<StaffItem, Integer> tmpTotalTask;

    @FXML
    private TableColumn<?, ?> tmtAction;

    @FXML
    private TableColumn<TaskItem, String> tmtContents;

    @FXML
    private TableColumn<TaskItem, Date> tmtFinishDate;

    @FXML
    private TableColumn<TaskItem, Date> tmtStartDate;

    @FXML
    private TableColumn<TaskItem, Integer> tmtTaskID;

    @FXML
    private TableColumn<TaskItem, Time> tmtFinishTime;

    @FXML
    private TableColumn<TaskItem, Time> tmtStartTime;

    @FXML
    private TableColumn<TaskItem, String> tmtTitle;

    @FXML
    private TableColumn<TaskItem, Integer> tmtTo;

    @FXML
    private TableColumn<?, ?> ttrAction;

    @FXML
    private TableColumn<?, ?> ttrContents;

    @FXML
    private TableColumn<?, ?> ttrFrom;

    @FXML
    private TableColumn<?, ?> ttrStatus;

    @FXML
    private TableColumn<?, ?> ttrTaskID;

    @FXML
    private TableColumn<?, ?> ttrTime;

    @FXML
    private TableColumn<?, ?> ttrTitle;

    @FXML
    private VBox vBoxDepartments;

    @FXML
    private VBox vBoxPersonnelCount;

    @FXML
    private VBox vBoxTaskComplete;

    @FXML
    private VBox vBoxTasksAssigned;
    
    String[] date = {"7 days","14 days", "21 day", "30 days"};
    ObservableList<TaskItem> taskList = FXCollections.observableArrayList();
    ObservableList<StaffItem> staffList = FXCollections.observableArrayList();
    
    @FXML
    void handleManagementTask(ActionEvent event) {
    	if(event.getSource() ==btnAddTask) {
    		setDisplayMode("Add Task");   		
    		loadItemsPane("/home/CreateTask.fxml");
    	}
    	else if(event.getSource() ==btnApply) {
    		setTaskInterface();
    	}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		homeVboxItems.setVisible(false);
		lbUsername.setText(Main.userLogin.getFirstname()+" "+ Main.userLogin.getLastname());
		lbPoisition.setText(Main.userLogin.getPosition());
		lbDepartment.setText(Main.userLogin.getDepartment());
		choiceBoxDate.getItems().addAll(date);
		pickDate.setValue(LocalDate.now());;
		choiceBoxDate.setValue("7 days");
		setTaskTableProperty();
		setPersonTableProperty();
		setTaskInterface();
		setPersonInterface();
	}
	

	
	private void setTaskTableProperty() {
		tmtTaskID.setCellValueFactory( new PropertyValueFactory<TaskItem, Integer>("id"));
		tmtTitle.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("title"));
		tmtContents.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("content"));
		tmtTo.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("to"));
		tmtStartDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("startDate"));
		tmtStartTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
		tmtFinishDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("finishDate"));
		tmtFinishTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("finishTime"));
	}
	
	private void setPersonTableProperty() {
		tmpID.setCellValueFactory(new PropertyValueFactory<StaffItem, Integer>("id"));
		tmpName.setCellValueFactory(new PropertyValueFactory<StaffItem, String>("name"));
		tmpEmail.setCellValueFactory(new PropertyValueFactory<StaffItem, String>("email"));
		tmpPhone.setCellValueFactory(new PropertyValueFactory<StaffItem, String>("phone"));
		tmpTotalTask.setCellValueFactory(new PropertyValueFactory<StaffItem, Integer>("totalTask"));
		tmpTaskComplete.setCellValueFactory(new PropertyValueFactory<StaffItem, Integer>("taskComplete"));
		tmpTaskDelay.setCellValueFactory(new PropertyValueFactory<StaffItem, Integer>("taskDelay"));
	}
	/**
	 * Phương thức @setTaskInterface dùng để load dữ liệu của Tab Task
	 * 
	 */
	private void setTaskInterface() {
		String[] dateSelected = choiceBoxDate.getValue().split(" ");
		Date dateBegin = Date.valueOf(pickDate.getValue());
		Date dateEnd = Date.valueOf(pickDate.getValue().plusDays(Integer.parseInt(dateSelected[0])));
		DAPHome dap = new DAPHome();
		ResultSet rs = dap.selectTask(Main.userLogin.getId(), dateBegin, dateEnd);
		taskList.clear();
		try {
			while(rs.next()) {
				TaskItem item = new TaskItem(rs.getInt("ID"), rs.getString("Title"), rs.getString("Content"), 
								rs.getInt("DifferentStaffIDs"), rs.getDate("Started_Date"), rs.getTime("Started_Time"),
								rs.getDate("Ended_Date"), rs.getTime("Ended_Time"));
				taskList.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableManagementTask.setItems(taskList);
		dap.close();
	}
	
	/**
	 * Phương thức @setPersonInterface dùng để load dữ liệu của Tab Personnel
	 * 
	 */
	private void setPersonInterface() {
		DAPHome dap = new DAPHome();
		ResultSet rs = dap.selectPerson(Main.userLogin.getDepartment(),Main.userLogin.getId());
		staffList.clear();
		int countStaff = 0;
		try {
			while(rs.next()) {
				StaffItem sti = new StaffItem(rs.getInt("ID"), rs.getString("First_Name")+" " + rs.getString("Last_Name"), 				rs.getString("Email"), rs.getString("Phone_Number"), rs.getInt("TotalTasks"), rs.getInt("TasksCompleted"), 				rs.getInt("TotalTasks") - rs.getInt("TasksCompleted"));
				staffList.add(sti);
				countStaff++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableManagementPersonnel.setItems(staffList);
		lbPersonnelCount.setText(String.format("%d Employees", countStaff));
		dap.close();
	}
	/**
	 * Phương thức @setDisplayMode thiết lập on-off các Component của giao diện bao gồm:
	 * @createTask : tạo/ edit Task
	 * @taskRequires: Yêu cầu cháp thuận.
	 */
	private void setDisplayMode(String mode) {
		homeVboxCenter.setVisible(mode.equals("All Tasks"));
		homeVboxItems.setVisible(mode.equals("Add Task"));
	
	}
	
	private void loadItemsPane(String paneComponent) {
		homeVboxItems.getChildren().clear();
		try {
			homeVboxItems.getChildren().add(FXMLLoader.load(getClass().getResource(paneComponent)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
