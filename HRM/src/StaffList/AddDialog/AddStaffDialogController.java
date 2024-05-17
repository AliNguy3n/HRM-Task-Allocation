package StaffList.AddDialog;

import Models.DTO.StaffDTO;
import StaffList.StaffListController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private TextField firstNameField, lastNameField, phoneNumberField, emailField, departmentField, positionField, usernameField, salaryField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton, cancelButton, addImage;

    private StaffListController staffListController;
    private StaffDTO staff;
    private boolean confirmed = false;

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

    @FXML
    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Xác định thư mục 'avatars' ngay trong thư mục gốc của project
                Path projectRoot = Paths.get(System.getProperty("user.dir"));
                Path dest = Paths.get(projectRoot.toString(), "src", "asset", "avatar", file.getName());

                // Đảm bảo thư mục tồn tại
                if (!Files.exists(dest.getParent())) {
                    Files.createDirectories(dest.getParent());
                }

                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                staff.setAvatarPath(dest.toString()); // Lưu đường dẫn tương đối vào đối tượng staff
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okButton.setOnAction(event -> {
            updateStaffData();
            confirmed = true;
            closeWindow();
        });
        addImage.setOnAction(e -> browseImage());
        cancelButton.setOnAction(event -> closeWindow());
    }
}
