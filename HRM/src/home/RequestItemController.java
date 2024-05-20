package home;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.text.TextFlow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RequestItemController {
	
    @FXML
    private Label requestUsername;
    @FXML
    private HBox requestContainer;
	@FXML
	private VBox requestItem;
	@FXML
	private TextFlow txtFlow;
	@FXML
	private Label lbRequestContent;
	@FXML
	private Label lbTime;

	public void setContent(String name,String content) {
		requestUsername.setText(name);
		lbRequestContent.setText(content);
	}
	
	public void setTime(Timestamp timestamp) {
		lbTime.setText(timestamp.toString());
	}

}
