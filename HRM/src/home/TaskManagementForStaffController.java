package home;

/**
* @author Duc Linh
*/
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.NumberAxis;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPTask;
import dap.DAPTaskPerform;
import eu.hansolo.tilesfx.Tile;

import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.util.Callback;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class TaskManagementForStaffController implements Initializable{

    @FXML
    private Button btnBackToTasks;
    
    @FXML
    private FontIcon iKonBackToTasks;
    
    @FXML
    private Label lbNumberMess;
    
    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnApply;

    @FXML
    private ChoiceBox<String> choiceBoxDate;

    @FXML
    private HBox hBoxChart;

    @FXML
    private ImageView imageUserName;

    @FXML
    private Label lbManagementTask;

    @FXML
    private Label lbPoisition;

    @FXML
    private Label lbTasksCompleted;

    @FXML
    private Label lbTasksDelay;

    @FXML
    private Label lbTasksDoing;

    @FXML
    private Label lbTitleTasksCompleted;

    @FXML
    private Label lbTitleTasksDelay;

    @FXML
    private Label lbTitleTasksDoing;

    @FXML
    private Label lbTitleTotalTasks;

    @FXML
    private Label lbTotalTasks;

    @FXML
    private Label lbUsername;

    @FXML
    private DatePicker pickDate;

    @FXML
    private BorderPane staffManagement;

    @FXML
    private AnchorPane staffManagementControlls;

    @FXML
    private ScrollPane staffManagementScroll;

    @FXML
    private AnchorPane staffManagementTasksComplete;

    @FXML
    private AnchorPane staffManagementTasksDoing;

    @FXML
    private GridPane staffManagementTopGrid;

    @FXML
    private VBox staffManagementVBox;

    @FXML
    private TableView<TaskItem> tableCompleted;

    @FXML
    private TableView<TaskItem> tableTasksDoing;

    @FXML
    private TableColumn<TaskItem, String> tsCompletedContents;

    @FXML
    private TableColumn<TaskItem, Date> tsCompletedFinishDate;

    @FXML
    private TableColumn<TaskItem, Time> tsCompletedFinishTime;

    @FXML
    private TableColumn<TaskItem, Date> tsCompletedStartDate;

    @FXML
    private TableColumn<TaskItem, Time> tsCompletedStartTime;

    @FXML
    private TableColumn<TaskItem, Integer> tsCompletedTaskID;

    @FXML
    private TableColumn<TaskItem, String> tsCompletedTitle;

    @FXML
    private Label tsCompletedTitlePane;

    @FXML
    private TableColumn<TaskItem, String> tsDoingContents;

    @FXML
    private TableColumn<TaskItem, Date> tsDoingFinishDate;

    @FXML
    private TableColumn<TaskItem, Time> tsDoingFinishTime;

    @FXML
    private TableColumn<TaskItem, Date> tsDoingStartDate;

    @FXML
    private TableColumn<TaskItem, Time> tsDoingStartTime;

    @FXML
    private TableColumn<TaskItem, Integer> tsDoingTaskID;

    @FXML
    private TableColumn<TaskItem, String> tsDoingTitle;
    
    @FXML
    private TableColumn<TaskItem, String> tsDoingStatus;
    
    
    @FXML
    private Label tsDoingTitlePane;

    @FXML
    private VBox vBoxTasksCompleted;

    @FXML
    private VBox vBoxTasksDelayed;

    @FXML
    private VBox vBoxTasksDoing;

    @FXML
    private VBox vBoxTotalTasks;

    @FXML
    private VBox vBoxUserName;
    
    @FXML
    private StackPane staffManagementStack;
    
    @FXML
    private VBox staffManagementVBoxItems;
    
    @FXML
    void handleManagementTask(ActionEvent event) {
    	if(event.getSource() == btnAddTask) {
    		setDisplayMode("Add Task");   		
    		loadItemsPane("/home/CreateTask.fxml","Add Task", null);
    	}
    	else if(event.getSource() ==btnApply) {
    		setProgressTaskInterface();
    		setCompletedTaskInterface();
    		setTaskInterface();
    	}
    }
    
    
    HashMap<Date, Integer> chartValueTotal = new HashMap<Date, Integer>();
    HashMap<Date, Integer> chartValueComplete = new HashMap<Date, Integer>();
    private String[] date = {"7 days","14 days", "21 day", "30 days", "60 days"};
    private  ObservableList<TaskItem> taskProgressList = FXCollections.observableArrayList();
    private ObservableList<TaskItem> taskCompletedList = FXCollections.observableArrayList();
    ArrayList<RequestItem> messList = new ArrayList<RequestItem>();
    
    int totalTask ;
    int delayTask ;
    int completeTask ;
    int doingTask ;
    private Tile donutChartTile;

	private ChartData data1 ;
	private ChartData data2 ;
	private ChartData data3 ;		
	
	private LocalDate localDate = LocalDate.now();
	private DateTimeFormatter myFormatLocalDate = DateTimeFormatter.ofPattern("dd-MM");
	
	StaffItem staffItem;
	int mode = 0;
	int rsCount=0;
	int messUnChecked = 0;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbUsername.setText(Main.userLogin.getFirstname()+" "+Main.userLogin.getLastname());
		lbPoisition.setText(Main.userLogin.getPosition());
		//imageUserName.setImage(new Image(getClass().getResourceAsStream(Main.userLogin.getAvatarPath())));
		btnAddTask.setVisible(false);
		lbNumberMess.setVisible(!Boolean.valueOf(Main.obSettings.getValue("notification")));
		setProgressTaskInterface();
		setCompletedTaskInterface();
		choiceBoxDate.getItems().addAll(date);
		pickDate.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1));
		choiceBoxDate.setValue("30 days");
		
		for(int i= -4; i<3; i++) {
			chartValueTotal.put(Date.valueOf(localDate.plusDays(i)), 0);
			chartValueComplete.put(Date.valueOf(localDate.plusDays(i)), 0);
		}
		intervalCheckMess();
		checkMessage();
		setTaskInterface();
		btnBackToTasks.setDisable(true);
		btnBackToTasks.setVisible(false);
		
		
	}
	
	public void setUserRetrive(StaffItem staffItem) {
		this.staffItem = staffItem;
		lbUsername.setText(staffItem.getName());
		lbPoisition.setText(staffItem.getPosition());
		for(int i= -4; i<3; i++) {
			chartValueTotal.put(Date.valueOf(localDate.plusDays(i)), 0);
			chartValueComplete.put(Date.valueOf(localDate.plusDays(i)), 0);
		}
		mode = 1;
		setTaskInterface();
		btnAddTask.setVisible(false);
		lbNumberMess.setVisible(false);
		btnBackToTasks.setDisable(false);
		btnBackToTasks.setVisible(true);
	}
	
	public Button getButtonBackToTasks() {
		return btnBackToTasks;
	}
	
	private void setTaskInterface() {
	    totalTask =0;
	    delayTask =0;
	    completeTask = 0;
	    doingTask = 0;
		taskCompletedList.clear();
		taskProgressList.clear();
		String[] dateSelected = choiceBoxDate.getValue().split(" ");
		Date dateBegin = Date.valueOf(pickDate.getValue());
		Date dateEnd  = Date.valueOf(pickDate.getValue().plusDays(Integer.parseInt(dateSelected[0])));
		DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs = null;
		if(mode == 1) {
			rs = dap.selectStaffTask(staffItem.getId(), dateBegin, dateEnd);
		}else {
			rs = dap.selectStaffTask(Main.userLogin.getId(), dateBegin, dateEnd);
		}
		try {
			while(rs.next()) {
				TaskItem item = new TaskItem(rs.getInt("ID"), rs.getString("Title"), rs.getString("Content"), 
						 1, rs.getDate("Started_Date"), rs.getTime("Started_Time"),
						rs.getDate("Ended_Date"), rs.getTime("Ended_Time"), rs.getInt("Assignedby"), rs.getString("Status"));
				totalTask++;
				if(rs.getString("Status").equals("Complete")) {
					taskCompletedList.add(item);
					completeTask++;
					
				}else if(rs.getString("Status").equals("Delay")) {
					taskProgressList.add(item);	
					delayTask++;
				}else {
					taskProgressList.add(item);	
					doingTask++;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		ResultSet rss =null;
		if(mode ==1 ) {
			rss = dap.selectTaskChart(staffItem.getId(), Date.valueOf(localDate.plusDays(-4)) ,
					Date.valueOf(localDate.plusDays(2)));
		}else {
			rss = dap.selectTaskChart(Main.userLogin.getId(), Date.valueOf(localDate.plusDays(-4)) ,
					Date.valueOf(localDate.plusDays(2)));
		}
		
		
		try {
			while(rss.next()) {
				chartValueTotal.replace(rss.getDate("Started_Date"),rss.getInt("TotalTask")) ;
				chartValueComplete.replace(rss.getDate("Started_Date"),rss.getInt("CompletedTask")) ;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tableTasksDoing.getItems().clear();
		tableCompleted.getItems().clear();
		tableTasksDoing.getItems().addAll(taskProgressList);
		tableCompleted.getItems().addAll(taskCompletedList);
		lbTotalTasks.setText(totalTask+" Tasks");
		lbTasksDelay.setText(delayTask+" Tasks");
		lbTasksDoing.setText(doingTask+" Tasks");
		lbTasksCompleted.setText(completeTask+" Tasks");
		
		setChartInterface();
		
		dap.close();
	}
	
	private void setProgressTaskInterface() {
	    tsDoingContents.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("content"));
	    tsDoingFinishDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("finishDate"));
	    tsDoingFinishTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
	    tsDoingStartDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("startDate"));
	    tsDoingStartTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
	    tsDoingTaskID.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("id"));
	    tsDoingTitle.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("title"));
	    tsDoingStatus.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("status"));
	    
	    
	    Callback<TableColumn<TaskItem, String>, TableCell<TaskItem, String>> newCellFactory = arg0 ->  {
	    	return new TableCell<TaskItem, String>(){
	    		
	    		public void updateItem(String item, boolean empty) {
	    			super.updateItem(item, empty);
	    			if(empty) {
	    				setGraphic(null);
						setText(null);
	    			}
	    			else {

						
						Button newButton = new Button();
						newButton.setText(item);
						if(item.equals("Delay")) {
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
	    tsDoingStatus.setCellFactory(newCellFactory);
	    
	    tableTasksDoing.setRowFactory(tv ->{
	    	TableRow<TaskItem> row = new TableRow<TaskItem>();
	    	row.setOnMouseClicked(event ->{
	    		if(mode == 0 && !row.isEmpty() && event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2) {
	    			setDisplayMode("Task Management");
	    			loadItemsPane("/home/TaskManagement.fxml","Task Management", row.getItem());
	    		}
	    	});
	    	 return row;
	    });
	   
	}
	
	private void setCompletedTaskInterface() {
	    tsCompletedContents.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("content"));
	    tsCompletedFinishDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("startDate"));
	    tsCompletedFinishTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
	    tsCompletedStartDate.setCellValueFactory(new PropertyValueFactory<TaskItem, Date>("finishDate"));
	    tsCompletedStartTime.setCellValueFactory(new PropertyValueFactory<TaskItem, Time>("startTime"));
	    tsCompletedTaskID.setCellValueFactory(new PropertyValueFactory<TaskItem, Integer>("id"));
	    tsCompletedTitle.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("title"));
	    
	    tableCompleted.setRowFactory(tv ->{
	    	TableRow<TaskItem> row = new TableRow<TaskItem>();
	    	row.setOnMouseClicked(event ->{
	    		if(mode == 0 && !row.isEmpty() && event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2) {
	    			setDisplayMode("Task Management");
	    			loadItemsPane("/home/TaskManagement.fxml","Task Management", row.getItem());
	    		}
	    	});
	    	 return row;
	    });
	}
	
	private void setDisplayMode(String mode) {
		staffManagementVBox.setVisible(mode.equals("All Tasks"));
		staffManagementVBoxItems.setVisible(mode.equals("Add Task")|| mode.equals("Edit Task")||  mode.equals("Task Management"));	
	}
	
	private void loadItemsPane(String paneComponent, String mode, TaskItem taskItem) {
		staffManagementVBoxItems.getChildren().clear();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(paneComponent));
			staffManagementVBoxItems.getChildren().add(loader.load());
			if((mode.equals("Add Task") ||mode.equals("Edit Task"))) {
				CreateTaskController controller = loader.getController();
				controller.setMode(mode, taskItem);
				
				controller.returnBtnCancel().setOnAction(e->{
					setDisplayMode("All Tasks");
					setTaskInterface();

				});
			}else if(mode.equals("Task Management")){
				TaskManagementController controller = loader.getController();
				controller.setMode(mode, taskItem);
				controller.returnBtnCancel().setOnAction(e ->{
					setDisplayMode("All Tasks");
					setTaskInterface();

				});
				
				DAPTask dap = new DAPTask();
				ResultSet rs = dap.selectAssignees(taskItem.getId());

				try {
					while (rs.next()) {
						if(rs.getInt("StaffID")==Main.userLogin.getId()) {
							FXMLLoader taskReport = new  FXMLLoader(getClass().getResource("/home/TaskReport.fxml"));

							staffManagementVBoxItems.getChildren().add(taskReport.load());
							
							TaskReportController reportController = taskReport.getController();
							
							reportController.setTaskStaffID(rs.getInt("StaffID"), taskItem.getId(), rs.getString("First_Name"),
									rs.getString("Last_Name"), rs.getString("Email"),taskItem.getAssignedby());
							
						}
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
	 * @setDonutChartInterface Phương thức này được sử dụng để render Biểu đồ bánh donut và biểu đồ cột
	 * Biểu đồ chứa thông tin hiển thị số lượng @taskComplete, @taskDelay, @TaskDoing
	 */

	@SuppressWarnings("unchecked")
	private void setChartInterface() {		
		

		data1 = new ChartData("Tasks Complete", completeTask ,Color.rgb(81, 176, 157));
		data2 = new ChartData("Tasks Doing", doingTask ,Color.rgb(67, 81, 133));
		data3 = new ChartData("Tasks Delay", delayTask,Color.rgb(237, 187, 95));
		
        
		donutChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .backgroundColor(Tile.BACKGROUND.TRANSPARENT)
                .prefSize(400, 400)
                .textColor(Tile.GRAY)
                .chartData(
                    data1,
                    data2,
                    data3
                )
                .build();
		
		
		
		// Dữ liệu cho tập số liệu 1
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Tasks Assigned");
		for(int i= -4; i<3; i++) {
			series1.getData().add(new XYChart.Data<>(myFormatLocalDate.format(localDate.plusDays(i)),
	        		chartValueTotal.get(Date.valueOf(localDate.plusDays(i)))));
		}
        


        // Dữ liệu cho tập số liệu 2
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Tasks Completed");
        for(int i= -4; i<3; i++) {
			series2.getData().add(new XYChart.Data<>(myFormatLocalDate.format(localDate.plusDays(i)),
	        		chartValueComplete.get(Date.valueOf(localDate.plusDays(i)))));
		}

        // Khởi tạo trục x và trục y
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        // Tạo biểu đồ cột và thêm dữ liệu
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setStyle("-fx-background-color: transparent;");
        barChart.getData().addAll(series1, series2);

		hBoxChart.getChildren().clear();
		hBoxChart.getChildren().addAll(donutChartTile,barChart);
       
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
