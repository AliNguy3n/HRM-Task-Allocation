package Account.ChangePasswordDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import user.UserLogin;

public class ChangePasswordDialogController {
    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private Stage dialogStage;
    private boolean okClicked = false;

    private UserLogin user;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleChangePassword() {
        if (isInputValid()) {
            user.setPassword(newPasswordField.getText());
            okClicked = true;
            showSuccessAlert(); // Hiển thị thông báo thành công
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (currentPasswordField.getText().isEmpty()) {
            errorMessage += "Current password cannot be empty!\n";
        } else if (!currentPasswordField.getText().equals(user.getPassword())) {
            errorMessage += "Current password is incorrect!\n";
        }

        if (newPasswordField.getText().isEmpty()) {
            errorMessage += "New password cannot be empty!\n";
        }

        if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
            errorMessage += "New passwords do not match!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(errorMessage);
            return false;
        }
    }

    private void showAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Password Updated");
        alert.setHeaderText("Success");
        alert.setContentText("Your password has been updated successfully.");
        alert.showAndWait();
    }
}
