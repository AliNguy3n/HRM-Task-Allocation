package home;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPTaskPerform;
import dap.DAPTask;
import displayperformance.SmoothScroll;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
* @author Duc Linh
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import threadapp.ThreadLoadDataHomeChart;


public class TaskManagementForManagerController implements Initializable{

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
    private TableColumn<TaskItem, String> tmtAction;

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
    
    @FXML
    private VBox vBoxTopPaneChart1;
    
    String[] date = {"7 days","14 days", "21 day", "30 days", "60 days"};
    ObservableList<TaskItem> taskList = FXCollections.observableArrayList();
    ObservableList<StaffItem> staffList = FXCollections.observableArrayList();
    private Tile donutChartTile;
    int totalTasks = 0;
    int tasksComplete = 0;
    int tasksDoing = 0;
    int tasksDelay = 0;
    TaskItem tit = null;
    @FXML
    void handleManagementTask(ActionEvent event) {
    	if(event.getSource() ==btnAddTask) {
    		setDisplayMode("Add Task");   		
    		loadItemsPane("/home/CreateTask.fxml","Add Task", null);
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
		pickDate.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),1));
		choiceBoxDate.setValue("30 days");
		setTaskTableProperty();
		setPersonTableProperty();
		setTaskInterface();
		setPersonInterface();
		setDonutChartInterface();
		
		
		//new SmoothScroll(homePaneCenter, 0.01);
        
	}
	
	
	/**
	 * @setTaskTableProperty đặt thuộ tính cho các trường trong bảng @tableManagementTask
	 */
	private void setTaskTableProperty() {
		tmtTaskID.setCellValueFactory( new PropertyValueFactory<TaskItem, Integer>("id"));
		tmtTitle.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("title"));
		tmtContents.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("content"));
		tmtTo.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("to"));
		tmtStartDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("startDate"));
		tmtStartTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
		tmtFinishDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("finishDate"));
		tmtFinishTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("finishTime"));
		
		Callback<TableColumn<TaskItem, String>, TableCell<TaskItem, String>> cellFactory = (TableColumn<TaskItem,String> param)
				->{
					return new TableCell<TaskItem, String>(){
						
						@Override
						public void updateItem(String item,boolean empty) {
							super.updateItem(item, empty);
							if(empty) {
								setGraphic(null);
								setText(null);
								
							}
							else {
								FontIcon edit = new FontIcon();
								FontIcon delete = new FontIcon();
								edit.setStyle("-fx-icon-color:#039BE5;-fx-icon-code:fltfal-document-edit-24;"
										+ "-fx-icon-size:20;-fx-cursor: hand ;");
								delete.setStyle("-fx-icon-color:#F4511E;-fx-icon-code:fltfal-delete-forever-24;"
										+ "-fx-icon-size:20;-fx-cursor: hand ;");
								HBox managebtn = new HBox(edit,delete);
		                        managebtn.setStyle("-fx-alignment:center");
		                        
		                        //Khi nhan nut Update
		                        edit.setOnMouseClicked((MouseEvent ev)->{
		                        	tit = tableManagementTask.getSelectionModel().getSelectedItem();
		                        	setDisplayMode("Add Task");
		                        	loadItemsPane("/home/CreateTask.fxml","Edit Task",tit);
		                        	
		                        });
		                        //Khi nhan nut Delete
		                        delete.setOnMouseClicked((MouseEvent ev)->{
		                        	tit = tableManagementTask.getSelectionModel().getSelectedItem();
		                        	DAPTask dap = new DAPTask();

									int status = dap.delete(tit.getId());
									if (status == 0) {
										Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setContentText("Delete Task Failded");
										alert.show();
									} else {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Congratulation!");
										alert.setContentText("Task has been Deleted!");
										alert.show();
									}
		                        });
		                        setGraphic(managebtn);

		                        setText(null);
							}
						}
					};
				};
				
		tmtAction.setCellFactory(cellFactory);
		tableManagementTask.setRowFactory(tv ->{
			TableRow<TaskItem> row = new TableRow<TaskItem>();
			row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                	TaskItem rowData = row.getItem();
                	setDisplayMode("Task Management");   		
            		loadItemsPane("/home/TaskManagement.fxml","Task Management", rowData);
                }
            });
            return row;
		});
			
	}
	
	/**
	 * @setPersonTableProperty() đặt thuộ tính cho các trường trong bảng @tableManagementPersonnel
	 */
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
		DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs = dap.selectTask(Main.userLogin.getId(), dateBegin, dateEnd);
		taskList.clear();
		try {
			while(rs.next()) {
				TaskItem item = new TaskItem(rs.getInt("ID"), rs.getString("Title"), rs.getString("Content"), 
								rs.getInt("DifferentStaffIDs"), rs.getDate("Started_Date"), rs.getTime("Started_Time"),
								rs.getDate("Ended_Date"), rs.getTime("Ended_Time"), rs.getInt("Assignedby"), null);
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
		DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs = dap.selectPerson(Main.userLogin.getDepartment(),Main.userLogin.getId());
		staffList.clear();
		int countStaff = 0;
		try {
			while(rs.next()) {
				StaffItem sti = new StaffItem(rs.getInt("ID"), rs.getString("First_Name")+" " + rs.getString("Last_Name"), 				rs.getString("Email"), rs.getString("Phone_Number"), rs.getInt("TotalTasks"), rs.getInt("TasksCompleted"), 				rs.getInt("TasksDelay"));
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
		homeVboxItems.setVisible(mode.equals("Add Task")|| mode.equals("Edit Task")||  mode.equals("Task Management"));
		
	}
	
	/**
	 * @loadItemsPane Phương thức này được sử dụng để load các Pane dẫn xuất và đặt nó vào Pane @homeVboxItems bao gồm 
	 * các Pane như @CreateTask, @editTask, @TaskManagement, @TaskRepuires
	 * @param paneComponent : chứa đường dẫn đến file @FXML cần nạp
	 * @param mode : tên của các Mode hiển thị như: "Add Task" , "Edit Task", "Task Management"
	 * @param tit: Đối tượng được truyền đi từ Component Cha để nạp sang Component Con
	 */
	private void loadItemsPane(String paneComponent, String mode, TaskItem taskItem) {
		homeVboxItems.getChildren().clear();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(paneComponent));
			homeVboxItems.getChildren().add(loader.load());
			if((mode.equals("Add Task") ||mode.equals("Edit Task"))) {
				CreateTaskController controller = loader.getController();
				controller.setMode(mode, taskItem);
				
				controller.returnBtnCancel().setOnAction(e->{
					setDisplayMode("All Tasks");
					setTaskInterface();
					setPersonInterface();
					setDonutChartInterface();
				});
			}else if(mode.equals("Task Management")){
				TaskManagementController controller = loader.getController();
				controller.setMode(mode, taskItem);
				controller.returnBtnCancel().setOnAction(e ->{
					setDisplayMode("All Tasks");
					setTaskInterface();
					setPersonInterface();
					setDonutChartInterface();
				});
				
				DAPTask dap = new DAPTask();
				ResultSet rs = dap.selectAssignees(taskItem.getId());

				try {
					while (rs.next()) {
						FXMLLoader taskReport = new  FXMLLoader(getClass().getResource("/home/TaskReport.fxml"));

						homeVboxItems.getChildren().add(taskReport.load());
						
						TaskReportController reportController = taskReport.getController();
						
						reportController.setTaskStaffID(rs.getInt("StaffID"), taskItem.getId(), rs.getString("First_Name"),
								rs.getString("Last_Name"), rs.getString("Email"),taskItem.getAssignedby());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dap.close();
			}

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @setDonutChartInterface Phương thức này được sử dụng để render Biểu đồ bánh donut
	 * Biểu đồ chứa thông tin hiển thị số lượng @taskComplete, @taskDelay, @TaskDoing
	 */
	private void setDonutChartInterface() {		
		ChartData data1 =null;
		ChartData data2 =null;
		ChartData data3 =null;		
		DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs =  dap.selectDataForChart(Main.userLogin.getId());
		try {
			while(rs.next()) {
				lbTasksMonth.setText(rs.getInt("TotalRows") + " Tasks");
				lbTaskComplete.setText(rs.getInt("CompeledRows") + " Tasks");
				data1 = new ChartData("Tasks Complete", rs.getInt("CompeledRows"),Color.rgb(81, 176, 157));
				data2 = new ChartData("Tasks Doing", rs.getInt("TotalRows")- rs.getInt("CompeledRows")- 				rs.getInt("DelayedRows"),Color.rgb(67, 81, 133));
				data3 = new ChartData("Tasks Delay", rs.getInt("DelayedRows"),Color.rgb(237, 187, 95));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		donutChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .backgroundColor(Tile.BACKGROUND.TRANSPARENT)
                .prefSize(300, 300)
                .textColor(Tile.GRAY)
                .chartData(
                    data1,
                    data2,
                    data3
                )
                .build();
		vBoxTopPaneChart1.getChildren().clear();
        vBoxTopPaneChart1.getChildren().add(donutChartTile);
        dap.close();
	}
	
	/**
	 * @getVerticalScrollBar Phương thức này trả về giá trị ScrollBar của scrollPane
	 * @param scrollPane
	 * @return
	 */
	private ScrollBar getVerticalScrollBar(ScrollPane scrollPane) {
		System.out.println(1);
        for (javafx.scene.Node node : scrollPane.lookupAll(".scroll-bar")) {
        	System.out.println(2);
            if (node instanceof ScrollBar) {
            	System.out.println(3);
                ScrollBar scrollBar = (ScrollBar) node;
                if (scrollBar.getOrientation() == javafx.geometry.Orientation.VERTICAL) {
                    return scrollBar;
                }
            }
        }
        return null;
    }
}
