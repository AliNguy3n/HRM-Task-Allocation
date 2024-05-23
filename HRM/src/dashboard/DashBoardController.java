package dashboard;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.kordamp.ikonli.javafx.FontIcon;

import application.Main;
import dap.DAPTaskPerform;
import home.RequestItem;

public class DashBoardController implements Initializable {

	@FXML
	private ToggleGroup NavBar;

	@FXML
	private ToggleGroup NavBar1;

	@FXML
	private ToggleButton btnAccount;

	@FXML
	private ToggleButton btnHome;

	@FXML
	private ToggleButton btnManage;

	@FXML
	private ToggleButton btnSettings;

	@FXML
	private ToggleButton btnStaffList;

	@FXML
	private BorderPane dashBoardPane;

	@FXML
	private BorderPane dashboardView;

	@FXML
	private FontIcon iKonAccounts2;

	@FXML
	private FontIcon iKonHome;

	@FXML
	private FontIcon iKonManage;

	@FXML
	private FontIcon iKonSettings;

	@FXML
	private FontIcon iKonbtnAccounts1;

	@FXML
	private ImageView imageLogo;

	@FXML
	private ImageView imageTitle;

	@FXML
	private VBox navDashBoardLeft;

	@FXML
	private HBox paneTitleNav;

	@FXML
	private Button btnLogout;

	@FXML
	private FontIcon iKonLogout;

	
	
	public static ArrayList<RequestItem> messList = new ArrayList<RequestItem>();
	int rsCount=0;
	public static IntegerProperty messUnChecked = new SimpleIntegerProperty(0);
	private ScheduledExecutorService scheduler;
	
	@FXML
    void handleDashBoardNav(ActionEvent event) {
    	if(event.getSource()==btnHome) {  		
    		if(Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() ==2) {
    			displayPaneElement("/home/TaskManagementForManager.fxml");
    		}
    		else {
    			displayPaneElement("/home/TaskManagementForStaff.fxml");
    		}
    	}
    	if(event.getSource()==btnManage && (Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() ==2)) {
    		displayPaneElement("/Manage/ManagerManage.fxml");
    	}else if(event.getSource()==btnManage && !(Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() ==2)) {
            displayPaneElement("/Manage/Manage.fxml");
        }
    	if(event.getSource()==btnAccount) {
    		displayPaneElement("/account/Account.fxml");
    	}
    	if(event.getSource()==btnSettings) {
    		displayPaneElement("/settings/Settings.fxml");
    	}
        if(event.getSource()==btnStaffList) {
            displayPaneElement("/StaffList/StaffList.fxml");
        }
    }

    
    private void displayPaneElement(String pane) {
    	
    	try {
			dashboardView.setCenter(FXMLLoader.load(getClass().getResource(pane)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() == 2) {
			btnStaffList.setVisible(true);
		} else {
			btnStaffList.setVisible(false);
		}

		switch (Main.obSettings.getValue("pageStartup")) {
		case "Accounts" -> displayPaneElement("/account/Account.fxml");
		case "Manage" -> displayPaneElement("/manage/Manage.fxml");
		case "Settings" -> displayPaneElement("/settings/Settings.fxml");
		case "StaffList" -> displayPaneElement("/StaffList/StaffList.fxml");
		default -> {
			if (Main.userLogin.getPermission() == 1 || Main.userLogin.getPermission() == 2) {
				displayPaneElement("/home/TaskManagementForManager.fxml");
			} else {
				displayPaneElement("/home/TaskManagementForStaff.fxml");
			}
		}
		}
		intervalCheckMess();
	}
	
	public Button getButtonLogout() {
		return btnLogout;
	}
	
	
	private void intervalCheckMess() {
		
		scheduler = Executors.newScheduledThreadPool(1);
		
		Runnable task = new Runnable() {
            public void run() {
            	Platform.runLater(() -> getMessageData());  // Chạy trên luồng JavaFX
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
	
    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ex) {
                scheduler.shutdownNow();
            }
        }
    }
	
	
	private void getMessageData() {
    	DAPTaskPerform dap = new DAPTaskPerform();
		ResultSet rs = dap.selectAllRequest(Main.userLogin.getId());
		int rowCount =0;
		System.out.println("Threading");
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
			int tempmessUnChecked =0;
			try {
				while(rss.next()) {
					RequestItem rqItem = new RequestItem(rss.getInt("ID"), rss.getInt("From"), rss.getString("Request"), 
							rss.getDate("Timestamp"), rss.getInt("Seem"), rss.getString("Title"), rss.getString("First_Name")+" "+
							rss.getString("Last_Name"), rss.getInt("TaskID"));
					messList.add(rqItem);
					if(rqItem.getSeem()==0 ) {tempmessUnChecked++;}
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
			messUnChecked.setValue(tempmessUnChecked);
			
		}
		dap.close();
	}
}
