package StaffList;

import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;
import StaffList.AddDialog.AddStaffDialogController;
import StaffList.EditDialog.EditStaffDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StaffListController implements Initializable {
    @FXML
    private TableColumn<StaffDTO, Image> avatarCol;
    @FXML
    private TableColumn<StaffDTO, String> departmentCol;
    @FXML
    private TableColumn<StaffDTO, String> emailCol;
    @FXML
    private TableColumn<StaffDTO, String> fullNameCol;
    @FXML
    private TableColumn<StaffDTO, String> phoneNumberCol;
    @FXML
    private TableColumn<StaffDTO, String> positionCol;
    @FXML
    private TableColumn<StaffDTO, Long> staffIdCol;
    @FXML
    private TableView<StaffDTO> staffTable;
    @FXML
    private TableColumn<StaffDTO, Void> editCol; // Chỉnh kiểu dữ liệu thành Void
    @FXML
    private TableColumn<StaffDTO, Void> deleteCol; // Chỉnh kiểu dữ liệu thành Void
    @FXML
    private Button addButton;

    private ObservableList<StaffDTO> staffList;
    private StaffListDAO staffDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo kết nối đến cơ sở dữ liệu
        Connection connection = DBConnection.getConnection();
        staffDAO = new StaffDAOImpl(connection);

        // Khởi tạo danh sách
        staffList = FXCollections.observableArrayList();

        // Load dữ liệu từ cơ sở dữ liệu
        loadStaffData();

        // Cấu hình các cột
        avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        avatarCol.setCellFactory(column ->{
             ImageView imageView = new ImageView();
             return new TableCell<StaffDTO, Image>(){
                 @Override
                 protected void updateItem(Image item, boolean empty) {
                     super.updateItem(item, empty);
                     if(empty || item == null) {
                         setGraphic(null);
                     }else {
                         imageView.setImage(item);
                         setGraphic(imageView);
                     }
                 }
             };

        });
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Cấu hình cột "Edit"
        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    StaffDTO staff = getTableView().getItems().get(getIndex());
                    if (staff != null) {
                        // Hiển thị hộp thoại chỉnh sửa với dữ liệu nhân viên hiện tại
                        showEditDialog(staff);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Cấu hình cột "Delete"
        // Cột "Delete" sử dụng kiểu Void
        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    StaffDTO staff = getTableView().getItems().get(getIndex());
                    if (staff != null) {
                        // Tạo cảnh báo xác nhận
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Confirmation");
                        alert.setHeaderText("Are you sure to delete " + staff.getLastName() + " " + staff.getFirstName() + " from your staff list?");
                        alert.setContentText("This action cannot be undone!!");

                        // Xử lý lựa chọn của người dùng
                        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                        if (result == ButtonType.OK) {
                            // Nếu người dùng chọn "OK" -> xoá
                            staffDAO.deleteStaff(staff.getId());
                            staffList.remove(staff);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        //AddButton Handler
        addButton.setOnAction(event -> showAddDialog());
        staffTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void loadStaffData() {
        // Lấy danh sách nhân viên từ DAO
        List<StaffDTO> staffData = staffDAO.getAllStaffs();
        staffList.setAll(staffData);
        staffTable.setItems(staffList);
    }

    private void showEditDialog(StaffDTO staff) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDialog/EditStaffDialog.fxml"));
            AnchorPane dialogPane = loader.load();
            EditStaffDialogController controller = loader.getController();
            controller.setStaffData(staff);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Staff Information");
            // Không cho tương tác cho đến khi đóng dialog
            dialogStage.initModality(Modality.WINDOW_MODAL);
            // Cái này cho thằng tableview phải nằm dưới
            dialogStage.initOwner(staffTable.getScene().getWindow());
            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                // Cập nhật lại thông tin nhân viên sau khi chỉnh sửa
                staffDAO.updateStaff(staff);
                staffTable.refresh(); // Làm mới TableView
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void showAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDialog/AddStaffDialog.fxml"));
            AnchorPane dialogPane = loader.load();
            AddStaffDialogController controller = loader.getController();

            // Tạo đối tượng StaffDTO mới
            StaffDTO newStaff = new StaffDTO();
            controller.setStaffData(newStaff);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Thêm nhân viên mới");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(staffTable.getScene().getWindow());
            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                // Thêm nhân viên mới vào cơ sở dữ liệu
                staffDAO.addStaff(newStaff);
                staffList.add(newStaff); // Cập nhật lại TableView

                loadStaffData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


