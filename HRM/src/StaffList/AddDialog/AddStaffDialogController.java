package StaffList.AddDialog;

import Models.DTO.StaffDTO;
import StaffList.StaffListController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStaffDialogController implements Initializable {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField salaryField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private StaffListController staffListController;
    private StaffDTO staff;
    private boolean confirmed = false;

//    @FXML
//    public void initialize() {
//        okButton.setOnAction(event -> {
//            updateStaffData();
//            confirmed = true;
//            closeWindow();
//        });
//
//        cancelButton.setOnAction(event -> closeWindow());
//    }
    public void setStaffListController(StaffListController controller) {
        this.staffListController = controller;
    }

    public void setStaffData(StaffDTO staff) {
        this.staff = staff;
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        phoneNumberField.setText(staff.getPhoneNumber());
        emailField.setText(staff.getEmail());
        departmentField.setText(staff.getDepartment());
        positionField.setText(staff.getPosition());
        usernameField.setText(staff.getUserName());
        passwordField.setText(staff.getPassword());
        salaryField.setText(staff.getSalary() != null ? staff.getSalary().toString() : "0.0");
    }

    private void updateStaffData() {
        staff.setFirstName(firstNameField.getText());
        staff.setLastName(lastNameField.getText());
        staff.setPhoneNumber(phoneNumberField.getText());
        staff.setEmail(emailField.getText());
        staff.setDepartment(departmentField.getText());
        staff.setPosition(positionField.getText());
        staff.setUserName(usernameField.getText());
        staff.setPassword(passwordField.getText());
        staff.setSalary(Float.parseFloat(salaryField.getText()));
        staff.setPermission(1L);
        staff.setStatus("1");
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void closeWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okButton.setOnAction(event -> {
            updateStaffData();
            confirmed = true;
            closeWindow();
        });

        cancelButton.setOnAction(event -> closeWindow());
    }
}
