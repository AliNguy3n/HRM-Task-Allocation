package Account;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import user.UserLogin;

import java.net.URL;
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

    private UserLogin user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Giả định rằng bạn có một phương thức để lấy thông tin người dùng hiện tại
        loadUserData();
    }

    private void loadUserData() {
        // Thay đổi bằng cách lấy dữ liệu người dùng từ cơ sở dữ liệu hoặc session
        user = getCurrentUser();

        if (user != null) {
            firstNameField.setText(user.getFirstname());
            lastNameField.setText(user.getLastname());
            emailField.setText(user.getEmail());
            phoneNumberField.setText(user.getPhonenumber());
            departmentField.setText(user.getDepartment());
            positionField.setText(user.getPosition());
            avatarImageView.setImage(new Image(user.getAvatarPath(), true));
        }
    }

    private UserLogin getCurrentUser() {
        // Trả về đối tượng UserLogin từ hệ thống
        // Ví dụ tạm thời
        //return new UserLogin(1, "file:/path/to/avatar.png", "John", "Doe", "john.doe@example.com", "123456789", "IT", "Developer", "johndoe", "User", "Active", 5000.0f);
        return null;
    }

    @FXML
    private void handleEdit() {
        // Kích hoạt chế độ chỉnh sửa
        enableEditing(true);
    }

    @FXML
    private void handleSave() {
        // Lưu các thay đổi vào cơ sở dữ liệu
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
        // Lấy thông tin từ các trường văn bản và cập nhật đối tượng người dùng
        user.setFirstname(firstNameField.getText());
        user.setLastname(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setPhonenumber(phoneNumberField.getText());
        user.setDepartment(departmentField.getText());
        user.setPosition(positionField.getText());

        // Cập nhật vào cơ sở dữ liệu
        updateUserInDatabase(user);
    }

    private void updateUserInDatabase(UserLogin user) {
        // Thực hiện cập nhật thông tin người dùng vào cơ sở dữ liệu
    }
}
