package Account.EditDialog;

import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user.UserLogin;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditDialogController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button changeAvatarButton;

    private Stage dialogStage;
    private UserLogin user;
    private boolean okClicked = false;

    private String newAvatarPath;

    Connection connection = DBConnection.getConnection();
    StaffListDAO staffListDAO = new StaffDAOImpl(connection);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize if needed
    }

    public void setUser(UserLogin user) {
        this.user = user;

        firstNameField.setText(user.getFirstname());
        lastNameField.setText(user.getLastname());
        phoneNumberField.setText(user.getPhonenumber());
        emailField.setText(user.getEmail());
    }

    @FXML
    private void handleOk() {
        if (validateInput()) {
            user.setFirstname(firstNameField.getText());
            user.setLastname(lastNameField.getText());
            user.setPhonenumber(phoneNumberField.getText());
            user.setEmail(emailField.getText());

            if (newAvatarPath != null && !newAvatarPath.isEmpty()) {
                user.setAvatarPath(newAvatarPath);
            }

            saveUserData(user);

            okClicked = true;

            // Hiển thị thông báo cập nhật thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("User information updated successfully!");
            alert.showAndWait();

            dialogStage.close(); // Đóng hộp thoại sau khi lưu và hiển thị thông báo
        }
    }

    private boolean validateInput() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        if (isNullOrEmpty(firstName) || isNullOrEmpty(lastName) || isNullOrEmpty(phoneNumber) || isNullOrEmpty(email)) {
            showAlert("Validation Error", "All fields must not be empty.");
            return false;
        }

        if (!firstName.matches("[a-zA-Z]+")) {
            showAlert("Validation Error", "First name must not contain numbers or special characters.");
            return false;
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            showAlert("Validation Error", "Last name must not contain numbers or special characters.");
            return false;
        }

        if (!phoneNumber.matches("^0[1-9][0-9]{8}$")) {
            showAlert("Validation Error", "Phone number must be 10 digits long, start with 0, and the second digit must not be 0.");
            return false;
        }

        if (!isValidEmail(email)) {
            showAlert("Validation Error", "Email must be in correct format (e.g., user@example.com).");
            return false;
        }

        return true;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void saveUserData(UserLogin user) {
        staffListDAO.updateStaffbyUserLogin(user);
    }

    @FXML
    private void handleChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(dialogStage);

        if (file != null) {
            try {
                Path projectRoot = Paths.get(System.getProperty("user.dir"));
                Path dest = Paths.get(projectRoot.toString(), "src", "asset", "avatar", file.getName());

                if (!Files.exists(dest.getParent())) {
                    Files.createDirectories(dest.getParent());
                }

                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(dest.toString());
                newAvatarPath = dest.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
