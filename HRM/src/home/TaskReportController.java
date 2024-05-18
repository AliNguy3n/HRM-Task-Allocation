package home;

/**
* @author Duc Linh
*/
import io.github.palexdev.materialfx.controls.MFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPTask;
import dap.DAPTaskReport;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;

import eu.hansolo.tilesfx.chart.RadarChartMode;

public class TaskReportController implements Initializable{

    @FXML
    private ToggleButton btnAcceptingTask;

    @FXML
    private Button btnSendReport;

    @FXML
    private Button btnSendRequest;

    @FXML
    private Button btnSubmitEvaluation;

    @FXML
    private MFXSlider sliderDealine;

    @FXML
    private BorderPane taskReportPane;

    @FXML
    private HBox taskReportTopPane;

    @FXML
    private Accordion trp_AccordionPane;

    @FXML
    private TitledPane trp_TitledAccepting;

    @FXML
    private Label trp_TitledAcceptingContent;

    @FXML
    private AnchorPane trp_TitledAccepting_Pane;

    @FXML
    private Label trp_TitledAccepting_lb;

    @FXML
    private TitledPane trp_TitledEvaluation;

    @FXML
    private AnchorPane trp_TitledEvaluationPane;

    @FXML
    private Label trp_TitledEvaluation_lbCooperation;

    @FXML
    private Label trp_TitledEvaluation_lbCreation;

    @FXML
    private Label trp_TitledEvaluation_lbDeadline;

    @FXML
    private Label trp_TitledEvaluation_lbProactivity;

    @FXML
    private Label trp_TitledEvaluation_lbQuality;

    @FXML
    private Label trp_TitledEvaluation_lbTotal;

    @FXML
    private TitledPane trp_TitledReporting;

    @FXML
    private AnchorPane trp_TitledReporting_Pane;

    @FXML
    private Label trp_TitledReporting_lbInput;

    @FXML
    private Label trp_TitledReporting_lbTitle;

    @FXML
    private TextArea trp_TitledReporting_txtFinal;

    @FXML
    private TextArea trp_TitledReporting_txtInput;

    @FXML
    private TitledPane trp_TitledRequesting;

    @FXML
    private AnchorPane trp_TitledRequesting_Pane;

    @FXML
    private Label trp_TitledRequesting_lb;

    @FXML
    private ScrollPane trp_TitledRequesting_scroll;

    @FXML
    private TextArea trp_TitledRequesting_txt;

    @FXML
    private VBox trp_TitledRequesting_vBox;

    @FXML
    private FontIcon trp_iKonSend;

    @FXML
    private FontIcon trp_iKonSend1;

    @FXML
    private Label trp_lbTitleTop;

    @FXML
    private Label trp_lbTitleUser;
    
    @FXML
    private VBox vBoxRadarChart;

    @FXML
    private MFXSlider slideQuality;

    @FXML
    private MFXSlider sliderCooperation;

    @FXML
    private MFXSlider sliderCreation;


    @FXML
    private MFXSlider sliderProactivity;
    
    @FXML
    private TextArea trp_TitledEvaluation_Comment;
    
    @FXML
    private Label trp_TitledRequesting_lbComment;

    int staffID;
    int taskID;
    int assignedby;
    int executionID;
    String firstName;
    String lastName;
    String email;
    String status;
    String report;
    
    double markDealine ;
	double markQuality ;
	double markCooperation ;
	double markProactivity ;
	double markCreation ;
	double total =0;
	
    private Tile            radarChart;
    
    private ChartData       chartData1;
    private ChartData       chartData2;
    private ChartData       chartData3;
    private ChartData       chartData4;
    private ChartData       chartData5;

    @FXML
    void handleTaskReporting(ActionEvent event) {
    	if(event.getSource() == btnSendRequest) {
    		if(sendRequest() !=0) {
    			trp_TitledRequesting_txt.clear();
    			loadRequest();
    		}
    	}
    	else if(event.getSource() == btnSendReport) {
    		if(sendReport() !=0) {
    			trp_TitledReporting_txtFinal.setText(trp_TitledReporting_txtInput.getText());
    			trp_TitledReporting_txtInput.clear();
    		}
    	}
    	else if(event.getSource() == btnAcceptingTask) {
    		if(!status.equals("Accepted") && staffID == Main.userLogin.getId()) {
    			acceptingTask();
        		
        		if(acceptingTask()!=0) {
        			btnAcceptingTask.setText("Accepted");
        		}
    		}
    	}
    	else if(event.getSource() == btnSubmitEvaluation) {
    		submitEvaluation();
    	}
    }
    
    public void setTaskStaffID(int staffID, int taskID,String firstName, String lastName, String email, int assignedby) {
    	this.staffID = staffID;
    	this.taskID = taskID;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.assignedby = assignedby;
    	trp_lbTitleUser.setText(String.format("%s %s (%s)", firstName, lastName, email));
    	
    	DAPTaskReport dap = new DAPTaskReport();
    	ResultSet rs= dap.select(staffID, taskID);
    	
    	try {
			while(rs.next()) {				
				status = rs.getString("Status");
				report = rs.getString("Report");
				executionID = rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	if(status.equals("Accepted") || status.equals("Reported")|| status.equals("Delay")) {
    		btnAcceptingTask.setText("Accepted");
    		btnAcceptingTask.setDisable(true);
    		btnAcceptingTask.setSelected(true);
    	}
    	
    	if(report != null) {
    		trp_TitledReporting_txtFinal.setText(report);
    	}
    	
    	loadRequest();
    	rs = dap.selectEvaluation(taskID, staffID);
    	
    	try {
			while(rs.next()) {
				trp_TitledEvaluation_Comment.setText(rs.getString("Comment"));
				total =Math.floor((rs.getDouble("Mark"))* 10)/10 ;
				btnSubmitEvaluation.setText("Update Mark");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	dap.close();
    	setRadarChartInterFace();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		trp_TitledReporting_txtFinal.setEditable(false);
		if(Main.userLogin.getPermission()==3) {
			sliderDealine.setDisable(true);
			slideQuality.setDisable(true);
			sliderCooperation.setDisable(true);
			sliderProactivity.setDisable(true);
			sliderCreation.setDisable(true);
			btnSubmitEvaluation.setDisable(true);
		}
		
		
	}
	
	private void loadRequest() {
		DAPTaskReport dap = new DAPTaskReport();
		ResultSet rq= dap.selectTaskRequest(staffID, assignedby, taskID);
		trp_TitledRequesting_vBox.getChildren().clear();
    	int n=0;
		try {
			while(rq.next()) {
				try {	
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/RequestItem.fxml"));
					HBox hbox = new HBox();
					hbox.getChildren().add(loader.load());
					if(rq.getInt("From") == Main.userLogin.getId()) {
						hbox.setAlignment(Pos.TOP_RIGHT);
					}else {
						hbox.setAlignment(Pos.TOP_LEFT);
					}
					trp_TitledRequesting_vBox.getChildren().add(hbox);
					RequestItemController control = loader.getController();
					control.setContent(rq.getString("First_Name").charAt(0)+""+rq.getString("Last_Name").charAt(0),
							rq.getString("Request"));
					control.setTime(rq.getTimestamp("Timestamp"));
				} catch (IOException e) {

					e.printStackTrace();
				}
				n++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private int sendRequest() {
		trp_TitledRequesting_txt.getText();
		DAPTaskReport dap = new DAPTaskReport();
		int i = dap.insertRequest(Main.userLogin.getId(), staffID, taskID, trp_TitledRequesting_txt.getText());
		dap.closeCnn();
		return i;
	}
	
	private int sendReport() {

		DAPTaskReport dap = new DAPTaskReport();
		int i = dap.updateReport(executionID, "Reported", trp_TitledReporting_txtInput.getText());
		dap.closeCnn();
		return i;
	}
	
	private int acceptingTask() {
		
		DAPTaskReport dap = new DAPTaskReport();
		int i = dap.updateAccepting(executionID, "Accepted");
		dap.closeCnn();
		return i;
	}
	
	private void submitEvaluation() {
		markDealine = sliderDealine.getValue();
		markQuality = slideQuality.getValue();
		markCooperation = sliderCooperation.getValue();
		markProactivity = sliderProactivity.getValue();
		markCreation = sliderCreation.getValue();
		total = (markDealine + markQuality + markCooperation +markProactivity + markCreation)/5;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you want to submit this evaluation!");
		alert.setTitle("Confirm Submit!");
		ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
		alert.getButtonTypes().setAll(btnYes, btnNo);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/asset/LogoIconOnly.png")));
		Optional<ButtonType> result =  alert.showAndWait();	
		int  i=0;
		if(result.isPresent() && result.get().equals(btnYes)) {
			DAPTaskReport dap = new DAPTaskReport();
			if(btnSubmitEvaluation.getText().equals("Submit Evaluation")) {
				i = dap.insertEvaluation(taskID, staffID, total, trp_TitledEvaluation_Comment.getText());
				
			}else {
				i = dap.updateEvaluation(taskID, staffID, total, trp_TitledEvaluation_Comment.getText());
				
			}
			dap.updateReport(executionID, "Complete", report);
			if(i != 0) {
				setRadarChartInterFace();			
			}
			dap.closeCnn();
		}
	}
	
	private void setRadarChartInterFace() {

		
		
        // Chart Data
        chartData1 = new ChartData("Dealine", markDealine, Tile.GREEN);
        chartData2 = new ChartData("Quality", markQuality, Tile.BLUE);
        chartData3 = new ChartData("Cooperation", markCooperation, Tile.RED);
        chartData4 = new ChartData("Proactivity", markProactivity, Tile.YELLOW_ORANGE);
        chartData5 = new ChartData("Creation", markCreation, Tile.BLUE);

        
        radarChart = TileBuilder.create().skinType(SkinType.RADAR_CHART)
        		.backgroundColor(Tile.BACKGROUND.TRANSPARENT)
        		.textColor(Color.web("#616161"))
        		.chartGridColor(Color.web("#9E9E9E"))
                .prefSize(300, 300)
                .minValue(0)
                .maxValue(100)
                .foregroundColor(Color.RED)
                .infoRegionForegroundColor(Color.RED)
                .unit(""+total)
                .unitColor(Color.web("#9E9E9E"))
                .radarChartMode(RadarChartMode.SECTOR)
                .gradientStops(new Stop(0.00000, Color.TRANSPARENT),
                               new Stop(0.00001, Color.web("#3552a0")),
                               new Stop(0.09090, Color.web("#456acf")),
                               new Stop(0.27272, Color.web("#45a1cf")),
                               new Stop(0.36363, Color.web("#30c8c9")),
                               new Stop(0.45454, Color.web("#30c9af")),
                               new Stop(0.50909, Color.web("#56d483")),
                               new Stop(0.72727, Color.web("#9adb49")),
                               new Stop(0.81818, Color.web("#efd750")),
                               new Stop(0.90909, Color.web("#ef9850")),
                               new Stop(1.00000, Color.web("#ef6050")))
                .text("Total Mark")
                .textAlignment(TextAlignment.CENTER)
                .chartData(chartData1, chartData2, chartData3, chartData4,
                           chartData5)
                .tooltipText("")
                .animated(true)
                .build();
        vBoxRadarChart.getChildren().clear();
		vBoxRadarChart.getChildren().add(radarChart);
	}
}

