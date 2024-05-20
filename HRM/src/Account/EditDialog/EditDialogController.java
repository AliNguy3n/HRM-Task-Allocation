package Account.EditDialog;

import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.UserLogin;

import java.net.URL;
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

    private Stage dialogStage;
    private UserLogin user;
    private boolean okClicked = false;

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
}
