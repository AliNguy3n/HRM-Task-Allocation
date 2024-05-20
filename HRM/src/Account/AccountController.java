package Account;

import Account.ChangePasswordDialog.ChangePasswordDialogController;
import Account.EditDialog.EditDialogController;
import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.UserLogin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private ImageView avatarImageView;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField positionField;

    @FXML
    private Button changePassBtn;

    private UserLogin user;

    Connection conn = DBConnection.getConnection();
    StaffListDAO staffListDAO = new StaffDAOImpl(conn);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUserData();
    }

    private void loadUserData() {
        user = getCurrentUser();

        if (user != null) {
            firstNameField.setText(user.getFirstname());
            lastNameField.setText(user.getLastname());
            emailField.setText(user.getEmail());
            phoneNumberField.setText(user.getPhonenumber());
            departmentField.setText(user.getDepartment());
            positionField.setText(user.getPosition());

            String avatarPath = user.getAvatarPath();
            if (avatarPath != null && !avatarPath.isEmpty() && new File(avatarPath).exists()) {
                avatarImageView.setImage(new Image(new File(avatarPath).toURI().toString()));
            } else {
                // Sử dụng ảnh mặc định nếu avatarPath không hợp lệ
                avatarImageView.setImage(new Image(getClass().getResource("/asset/BossAvatar01.png").toExternalForm()));
            }
            // Thiết lập hình ảnh thành hình tròn
            avatarImageView.setClip(new javafx.scene.shape.Circle(100, 100, 100));
        }
    }

    private UserLogin getCurrentUser() {
        return Main.userLogin;
    }

    @FXML
    private void handleEdit() {
        showEditDialog();
    }

    @FXML
    private void handleSave() {
        saveUserData();
        enableEditing(false);
    }

    private void enableEditing(boolean enable) {
        firstNameField.setEditable(enable);
        lastNameField.setEditable(enable);
        emailField.setEditable(enable);
        phoneNumberField.setEditable(enable);
        departmentField.setEditable(enable);
        positionField.setEditable(enable);
    }

    private void saveUserData() {
        user.setFirstname(firstNameField.getText());
        user.setLastname(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setPhonenumber(phoneNumberField.getText());
        user.setDepartment(departmentField.getText());
        user.setPosition(positionField.getText());

        updateUserInDatabase(user);
    }

    private void updateUserInDatabase(UserLogin user) {
        staffListDAO.updateStaffPassword(user);
    }

    private void showEditDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/EditDialog/EditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit User Information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(avatarImageView.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the user into the controller
            EditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage); // Thiết lập dialogStage
            controller.setUser(user);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                loadUserData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChangePassword(ActionEvent actionEvent) {
        showChangePasswordDialog();
    }

    private void showChangePasswordDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/ChangePasswordDialog/ChangePasswordDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Password");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(avatarImageView.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ChangePasswordDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user); // Thiết lập người dùng hiện tại cho controller

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                handleSave();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
