package home;

import javafx.fxml.FXML;

import javafx.scene.layout.HBox;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

public class MessageItemController {
	@FXML
	private HBox messHbox;
	@FXML
	private VBox messVbox;
	@FXML
	private Label messTitle;
	@FXML
	private Label messContent;

	
	public void getMessValue(RequestItem Item) {
		messTitle.setText(Item.getTitle());
		messContent.setText(Item.getRequest());
	}
}
