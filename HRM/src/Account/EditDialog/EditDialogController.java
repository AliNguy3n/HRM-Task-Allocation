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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ResourceBundle;

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
