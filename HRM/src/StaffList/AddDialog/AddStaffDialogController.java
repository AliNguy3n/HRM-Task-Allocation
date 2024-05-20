package StaffList.AddDialog;

import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class AddStaffDialogController implements Initializable {
    @FXML
    private TextField firstNameField, lastNameField, phoneNumberField, emailField, usernameField, salaryField;
    @FXML
    private ComboBox<String> departmentComboBox, positionComboBox;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private Button okButton, cancelButton, addImage;

    private StaffDTO staff;
    private StaffListDAO staffListDAO;  // Thêm DAO để kiểm tra username

    private boolean confirmed = false;

    public void setStaffData(StaffDTO staff) {
        this.staff = staff;
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        phoneNumberField.setText(staff.getPhoneNumber());
        emailField.setText(staff.getEmail());
        departmentComboBox.setValue(staff.getDepartment());
        positionComboBox.setValue(staff.getPosition());
        usernameField.setText(staff.getUserName());
        passwordField.setText(staff.getPassword());
        confirmPasswordField.setText(staff.getPassword());
        salaryField.setText(staff.getSalary() != null ? staff.getSalary().toString() : "0.0");
    }

    // Thêm phương thức để thiết lập DAO
    public void setStaffListDAO(StaffListDAO staffListDAO) {
        this.staffListDAO = staffListDAO;
    }

    private boolean validateInput() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() ||
                emailField.getText().isEmpty() || departmentComboBox.getValue() == null || positionComboBox.getValue() == null ||
                usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() ||
                salaryField.getText().isEmpty()) {
            showAlert("Validation Error", "All fields must not be empty.");
            return false;
        }
        if (!phoneNumberField.getText().matches("\\d{10}")) {
            showAlert("Validation Error", "Phone number must be 10 digits.");
            return false;
        }
        if (!emailField.getText().contains("@")) {
            showAlert("Validation Error", "Email must be in correct format.");
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }
        if (staffListDAO.isUsernameExist(usernameField.getText())) {
            showAlert("Validation Error", "Username already exists.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateStaffData() {
        staff.setFirstName(firstNameField.getText());
        staff.setLastName(lastNameField.getText());
        staff.setPhoneNumber(phoneNumberField.getText());
        staff.setEmail(emailField.getText());
        staff.setDepartment(departmentComboBox.getValue());
        staff.setPosition(positionComboBox.getValue());
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

    @FXML
    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Path projectRoot = Paths.get(System.getProperty("user.dir"));
                Path dest = Paths.get(projectRoot.toString(), "src", "asset", "avatar", file.getName());

                if (!Files.exists(dest.getParent())) {
                    Files.createDirectories(dest.getParent());
                }

                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                staff.setAvatarPath(dest.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okButton.setOnAction(event -> {
            if (validateInput()) {
                updateStaffData();
                confirmed = true;
                closeWindow();
            }
        });
        addImage.setOnAction(e -> browseImage());
        cancelButton.setOnAction(event -> closeWindow());
    }
}
