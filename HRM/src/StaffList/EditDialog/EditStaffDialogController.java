package StaffList.EditDialog;

import Models.DAO.Impl.StaffDAOImpl;
import Models.DTO.StaffDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStaffDialogController {
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
    private Button okButton;
    @FXML
    private Button cancelButton;

    private StaffDTO staff;
    private boolean confirmed = false;
    private StaffDAOImpl staffDAO;

    @FXML
    public void initialize() {
        okButton.setOnAction(event -> {
            updateStaffData();
            confirmed = true;
            closeWindow();
        });

        cancelButton.setOnAction(event -> closeWindow());
    }

    public void setStaffData(StaffDTO staff) {
        this.staff = staff;
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        phoneNumberField.setText(staff.getPhoneNumber());
        emailField.setText(staff.getEmail());
        departmentField.setText(staff.getDepartment());
        positionField.setText(staff.getPosition());
    }

    private void updateStaffData() {

        staff.setFirstName(firstNameField.getText());
        staff.setLastName(lastNameField.getText());
        staff.setPhoneNumber(phoneNumberField.getText());
        staff.setEmail(emailField.getText());
        staff.setDepartment(departmentField.getText());
        staff.setPosition(positionField.getText());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void closeWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
