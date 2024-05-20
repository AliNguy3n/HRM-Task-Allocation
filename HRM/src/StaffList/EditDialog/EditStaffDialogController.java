package StaffList.EditDialog;

import Models.DTO.StaffDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    private ComboBox<String> departmentComboBox;
    @FXML
    private ComboBox<String> positionComboBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addImage;

    private StaffDTO staff;
    private boolean confirmed = false;

    @FXML
    public void initialize() {
        okButton.setOnAction(event -> {
            updateStaffData();
            confirmed = true;
            closeWindow();
        });

        cancelButton.setOnAction(event -> closeWindow());
        addImage.setOnAction(event -> browseImage());
    }

    public void setStaffData(StaffDTO staff) {
        this.staff = staff;
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        phoneNumberField.setText(staff.getPhoneNumber());
        emailField.setText(staff.getEmail());
        departmentComboBox.setValue(staff.getDepartment());
        positionComboBox.setValue(staff.getPosition());
        System.out.println(staff.getAvatarPath());
    }

    private void updateStaffData() {
        staff.setFirstName(firstNameField.getText());
        staff.setLastName(firstNameField.getText());
        staff.setPhoneNumber(phoneNumberField.getText());
        staff.setEmail(emailField.getText());
        staff.setDepartment(departmentComboBox.getValue());
        staff.setPosition(positionComboBox.getValue());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void closeWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

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
                System.out.println(dest.toString());
                staff.setAvatarPath(dest.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
