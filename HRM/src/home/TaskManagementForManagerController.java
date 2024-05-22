package home;
/**
* @author Duc Linh
*/
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPTaskPerform;
import dap.DAPTask;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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


public class TaskManagementForManagerController implements Initializable{

    @FXML
    private Label lbNumberMess;
    
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
    private TableView<RequestItem> tableTaskRequires;

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
    private TableColumn<RequestItem, String> ttrAction;

    @FXML
    private TableColumn<RequestItem, String> ttrContents;

    @FXML
    private TableColumn<RequestItem, String> ttrFrom;

    @FXML
    private TableColumn<RequestItem, Integer> ttrStatus;

    @FXML
    private TableColumn<RequestItem, Integer> ttrTaskID;

    @FXML
    private TableColumn<RequestItem, Date> ttrTime;

    @FXML
    private TableColumn<RequestItem, String> ttrTitle;

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
    ObservableList<RequestItem> requestList = FXCollections.observableArrayList();
    ArrayList<RequestItem> messList = new ArrayList<RequestItem>();
    private Tile donutChartTile;
    int totalTasks = 0;
    int tasksComplete = 0;
    int tasksDoing = 0;
    int tasksDelay = 0;
    TaskItem tit = null;
    
	int rsCount=0;
	int messUnChecked = 0;
	
    @FXML
    void handleManagementTask(ActionEvent event) {
    	if(event.getSource() ==btnAddTask) {
    		setDisplayMode("Add Task");   		
    		loadItemsPane("/home/CreateTask.fxml","Add Task", null);
    	}
    	else if(event.getSource() ==btnApply) {
    		setTaskInterface();
    		setRequestInterface();
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
		setRequestTableProperty();
		setTaskInterface();
		setPersonInterface();
		setRequestInterface();
		setDonutChartInterface();
		
		intervalCheckMess();
		checkMessage();
		lbNumberMess.setVisible(!Boolean.valueOf(Main.obSettings.getValue("notification")));
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
										setTaskInterface();
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
		
		
		tableManagementPersonnel.setRowFactory(tv ->{
			TableRow<StaffItem> row = new TableRow<StaffItem>();
			row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                	StaffItem rowData = row.getItem();
                	setDisplayMode("Personal Management");                	
            		loadPersonalItem(rowData);
                }
            });
            return row;
		});
	}
	
	/**
	 * @setRequestTableProperty() đặt thuộ tính cho các trường trong bảng @tableManagementPersonnel
	 */
	private void setRequestTableProperty() {
		ttrTaskID.setCellValueFactory(new PropertyValueFactory<RequestItem, Integer>("requestid"));
		ttrTitle.setCellValueFactory(new PropertyValueFactory<RequestItem, String>("title"));
		ttrContents.setCellValueFactory(new PropertyValueFactory<RequestItem, String>("request"));
		ttrTime.setCellValueFactory(new PropertyValueFactory<RequestItem, Date>("timestamp"));
		ttrStatus.setCellValueFactory(new PropertyValueFactory<RequestItem, Integer>("seem"));
		//ttrAction.setCellValueFactory(new PropertyValueFactory<RequestItem, String>("taskComplete"));
		ttrFrom.setCellValueFactory(new PropertyValueFactory<RequestItem, String>("name"));
		
	    Callback<TableColumn<RequestItem, Integer>, TableCell<RequestItem, Integer>> newCellFactory = arg0 ->  {
	    	return new TableCell<RequestItem, Integer>(){
	    		
	    		public void updateItem(Integer item, boolean empty) {
	    			super.updateItem(item, empty);
	    			if(empty) {
	    				setGraphic(null);
						setText(null);
	    			}
	    			else {
	    				String str ;
	    				if(item ==1 ) {
	    					str = "Seen";
	    				}else {
	    					str = "UnSeen";
	    				}
						
						Button newButton = new Button();
						newButton.setText(str);
						if(str.equals("UnSeen")) {
							newButton.setStyle("-fx-background-color:#FFC2B8; -fx-text-fill:#FF4343;-fx-pref-width: 70px; "
									+ "-fx-font-size: 12px;-fx-font-weight:bold; -fx-background-radius:5px;");
						}
						else {
							newButton.setStyle("-fx-background-color:#BBF7D0; -fx-text-fill:#166534;-fx-pref-width: 70px; "
									+ "-fx-font-size: 12px;-fx-font-weight:bold; -fx-background-radius:5px;");
						}
						HBox managebtn = new HBox(newButton);
						managebtn.setFillHeight(false);
                        managebtn.setStyle("-fx-alignment:center;");
                        setGraphic(managebtn);
	    			}
	    			
	    		}
	    		
	    	};
	    };
		
	    ttrStatus.setCellFactory(newCellFactory);
	    
		tableTaskRequires.setRowFactory(tv ->{
			TableRow<RequestItem> row = new TableRow<RequestItem>();
			row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                	RequestItem rowData = row.getItem();
                	 	               	
                	TaskItem taskItem =null;
                	for (TaskItem item : taskList) {
						if(item.getId()==rowData.getTaskid()) {
							taskItem = item;
							
						}
					}
            		loadItemsPane("/home/TaskManagement.fxml","Task Management", taskItem);
            		setDisplayMode("Task Management");  
            		DAPTaskPerform dap = new DAPTaskPerform();
            		dap.updateRequestSeem(rowData.getRequestid());
            		dap.closeCnn();
                }
            });
            return row;
		});
		
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
				StaffItem sti = new StaffItem(rs.getInt("ID"), rs.getString("First_Name")+" " + rs.getString("Last_Name"), 				rs.getString("Email"), rs.getString("Phone_Number"), rs.getInt("TasksCompleted") ,rs.getInt("TotalTasks") , 				rs.getInt("TasksDelay"), rs.getString("Position"));
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
	 * Phương thức @setRequestInterface dùng để load dữ liệu của Tab Personnel
	 * 
	 */
	private void setRequestInterface() {
		String[] dateSelected = choiceBoxDate.getValue().split(" ");
		DAPTaskPerform dap = new DAPTaskPerform();
		Date dateBegin = Date.valueOf(pickDate.getValue().plusDays(-1));
		Date dateEnd = Date.valueOf(pickDate.getValue().plusDays(Integer.parseInt(dateSelected[0])+1));
		ResultSet rs = dap.selectAllRequest(Main.userLogin.getId(), dateBegin, dateEnd);
		requestList.clear();
		int countRequest = 0;
		try {
			while(rs.next()) {
				RequestItem rqi = new RequestItem(rs.getInt("ID"), rs.getInt("From"), rs.getString("Request"), 
						rs.getDate("Timestamp"), rs.getInt("Seem"), rs.getString("Title"), rs.getString("First_Name")+" "+
						rs.getString("Last_Name"), rs.getInt("TaskID"));
				System.out.println(rs.getString("Title"));
				requestList.add(rqi);
				countRequest++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tableTaskRequires.setItems(requestList);

		dap.close();
	}
	/**
	 * Phương thức @setDisplayMode thiết lập on-off các Component của giao diện bao gồm:
	 * @createTask : tạo/ edit Task
	 * @taskRequires: Yêu cầu cháp thuận.
	 */
	private void setDisplayMode(String mode) {
		homeVboxCenter.setVisible(mode.equals("All Tasks"));
		homeVboxItems.setVisible(mode.equals("Add Task")|| mode.equals("Edit Task")||  mode.equals("Task Management")
				|| mode.equals("Personal Management"));
		
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
	
	private void loadPersonalItem(StaffItem staffitem) {
		homePane.getChildren().clear();
		//homePaneTop.setPrefHeight();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/TaskManagementForStaff.fxml"));
		try {
			homePane.setCenter(loader.load());
			TaskManagementForStaffController controller = loader.getController();
			controller.setUserRetrive(staffitem);
			controller.getButtonBackToTasks().setOnAction(event ->{
				BorderPane home =  (BorderPane) homePane.getParent();
				try {
					home.setCenter(FXMLLoader.load(getClass().getResource("/home/TaskManagementForManager.fxml")));
				} catch (IOException e) {

					e.printStackTrace();
				}
			});
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
				data2 = new ChartData("Tasks Doing", rs.getInt("TotalRows")- rs.getInt("CompeledRows") - 				rs.getInt("DelayedRows"),Color.rgb(67, 81, 133));
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
	
	private void checkMessage() {
		
		lbNumberMess.setOnMouseClicked(e ->{
			VBox vbox = new VBox();	
			
			for (RequestItem item : messList) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/MessageItem.fxml"));
				
				try {
					vbox.getChildren().add(loader.load());
					MessageItemController controller = loader.getController();
					controller.getMessValue(item);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
						 
			ScrollPane srp = new ScrollPane();
			srp.setContent(vbox);
			srp.setPrefHeight(300);
			srp.setPrefWidth(380);
			srp.getStylesheets().add(getClass().getResource("/CSS/TaskManagementForManager.css").toExternalForm());
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(4));
			
			PopOver popOver = new PopOver(srp);
			popOver.setTitle("Notification:");
			popOver.setArrowLocation(ArrowLocation.TOP_LEFT);
			popOver.setHeaderAlwaysVisible(true);
			popOver.show(lbNumberMess);

		});
	}
		
	private void intervalCheckMess() {
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
		Runnable task = new Runnable() {
            public void run() {
            	getMessageData();
            }
        };
        
        
        int initialDelay = 0; // delay in seconds
        int period = 5; // period in seconds
        
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ex) {
                scheduler.shutdownNow();
            }
        }));
		
		
	}
	private void getMessageData() {
    	DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs = dap.selectAllRequest(Main.userLogin.getId());
		int rowCount =0;
		System.out.println("Luồng vẫn đang chạy");
		try {
			while(rs.next()) {
				rowCount++;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
	        try {
	            if (rs != null) rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

		if(rowCount != rsCount) {
			ResultSet rss = dap.selectAllRequest(Main.userLogin.getId());
			messList.clear();
			rsCount=0;
			messUnChecked =0;
			try {
				while(rss.next()) {
					RequestItem rqItem = new RequestItem(rss.getInt("ID"), rss.getInt("From"), rss.getString("Request"), 
							rss.getDate("Timestamp"), rss.getInt("Seem"), rss.getString("Title"), rss.getString("First_Name")+" "+
							rss.getString("Last_Name"), rss.getInt("TaskID"));
					messList.add(rqItem);
					if(rqItem.getSeem()==0 ) {messUnChecked++;}
					rsCount++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
	            try {
	                if (rss != null) rss.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
			Platform.runLater(() -> lbNumberMess.setText(String.valueOf(messUnChecked)));
		}
		dap.close();
	}
}
