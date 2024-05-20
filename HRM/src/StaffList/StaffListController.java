package StaffList;

import Config.DBConnection;
import Models.DAO.Impl.StaffDAOImpl;
import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;
import StaffList.AddDialog.AddStaffDialogController;
import StaffList.EditDialog.EditStaffDialogController;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StaffListController implements Initializable {
    @FXML
    private TableColumn<StaffDTO, ImageView> avatarCol;
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
    private TableColumn<StaffDTO, Void> editCol;
    @FXML
    private TableColumn<StaffDTO, Void> deleteCol;
    @FXML
    private Button addButton;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> departmentFilter;
    @FXML
    private ComboBox<String> positionFilter;

    private ObservableList<StaffDTO> staffList;
    private ObservableList<StaffDTO> filteredList;
    private StaffListDAO staffDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = DBConnection.getConnection();
        staffDAO = new StaffDAOImpl(connection);

        staffList = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();

        loadStaffData();

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        avatarCol.setCellValueFactory(cellData -> {
            String avatarPath = cellData.getValue().getAvatarPath();
            ImageView imageView = new ImageView();
            if (avatarPath != null && !avatarPath.isEmpty() && new File(avatarPath).exists()) {
                imageView.setImage(new Image(new File(avatarPath).toURI().toString(), true));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                imageView.setClip(new Circle(25, 25, 25));
                imageView.getStyleClass().add("image-view");
            }
            return new SimpleObjectProperty<>(imageView);
        });

        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    StaffDTO staff = getTableView().getItems().get(getIndex());
                    if (staff != null) {
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

        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    StaffDTO staff = getTableView().getItems().get(getIndex());
                    if (staff != null) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Confirmation");
                        alert.setHeaderText("Are you sure to delete " + staff.getLastName() + " " + staff.getFirstName() + " from your staff list?");
                        alert.setContentText("This action cannot be undone!!");

                        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                        if (result == ButtonType.OK) {
                            staffDAO.deleteStaff(staff.getId());
                            staffList.remove(staff);
                            loadStaffData();
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

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterStaffList());
        departmentFilter.valueProperty().addListener((observable, oldValue, newValue) -> filterStaffList());
        positionFilter.valueProperty().addListener((observable, oldValue, newValue) -> filterStaffList());

        addButton.setOnAction(event -> showAddDialog());
        staffTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void loadStaffData() {
        List<StaffDTO> staffData = staffDAO.getAllStaffs();
        staffList.setAll(staffData);
        filteredList.setAll(staffData);
        staffTable.setItems(filteredList);
    }

    private void filterStaffList() {
        String searchKeyword = searchField.getText().toLowerCase();
        String selectedDepartment = departmentFilter.getValue();
        String selectedPosition = positionFilter.getValue();

        Predicate<StaffDTO> namePredicate = staff -> staff.getFullName().toLowerCase().contains(searchKeyword);
        Predicate<StaffDTO> departmentPredicate = staff -> selectedDepartment == null || selectedDepartment.equals("All") || staff.getDepartment().equalsIgnoreCase(selectedDepartment);
        Predicate<StaffDTO> positionPredicate = staff -> selectedPosition == null || selectedPosition.equals("All") || staff.getPosition().equalsIgnoreCase(selectedPosition);

        List<StaffDTO> filteredData = staffList.stream()
                .filter(namePredicate.and(departmentPredicate).and(positionPredicate))
                .collect(Collectors.toList());

        filteredList.setAll(filteredData);
    }

    private void showEditDialog(StaffDTO staff) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDialog/EditStaffDialog.fxml"));
            AnchorPane dialogPane = loader.load();
            EditStaffDialogController controller = loader.getController();
            controller.setStaffData(staff);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Staff Information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(staffTable.getScene().getWindow());
            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                staffDAO.updateStaff(staff);
                staffTable.refresh();
                loadStaffData();
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
            controller.setStaffListDAO(staffDAO);  // Thiết lập DAO cho AddStaffDialogController

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
                staffDAO.addStaff(newStaff);
                staffList.add(newStaff);
                loadStaffData();
                staffTable.refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
