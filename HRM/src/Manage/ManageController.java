package Manage;

import Config.DBConnection;
import Models.DAO.Impl.StaffTaskDAOImpl;
import Models.DAO.StaffTaskDAO;
import Models.DTO.StaffTaskDTO;
import application.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ManageController implements Initializable {

    @FXML
    private ImageView avatarImage;

    @FXML
    private TableColumn<StaffTaskDTO, String> AssignedByCol;

    @FXML
    private Text chartTitle;

    @FXML
    private TableColumn<StaffTaskDTO, Date> endedCol;

    @FXML
    private TableView<StaffTaskDTO> evaluationTable;

    @FXML
    private TableColumn<StaffTaskDTO, Float> markCol;

    @FXML
    private TableColumn<StaffTaskDTO, String> monthCol;

    @FXML
    private Text staffDepartment;

    @FXML
    private PieChart staffMonthlyMark;

    @FXML
    private Text staffName;

    @FXML
    private Text staffPosition;

    @FXML
    private Text staffTotalMark;

    @FXML
    private TableColumn<StaffTaskDTO, Date> startedCol;

    @FXML
    private TableColumn<StaffTaskDTO, String> taskNameCol;

    @FXML
    private Text totalMarkTitle;

    @FXML
    private ComboBox<String> monthFilterComboBox;

    private ObservableList<StaffTaskDTO> list;
    private ObservableList<StaffTaskDTO> filteredList;
    private StaffTaskDAO staffDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = DBConnection.getConnection();
        staffDAO = new StaffTaskDAOImpl(connection);

        list = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();

        loadStaffData();

        taskNameCol.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        startedCol.setCellValueFactory(new PropertyValueFactory<>("taskStartedDate"));
        endedCol.setCellValueFactory(new PropertyValueFactory<>("taskEndedDate"));
        AssignedByCol.setCellValueFactory(new PropertyValueFactory<>("AssignedBy"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("Mark"));
        monthCol.setCellValueFactory(cellData -> {
            LocalDate startedDate = cellData.getValue().getTaskStartedDate().toLocalDate();
            return new ReadOnlyObjectWrapper<>(startedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        });

        evaluationTable.setItems(filteredList);

        monthFilterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> filterByMonth(newValue));
    }

    private void loadStaffData() {
        List<StaffTaskDTO> staffData = staffDAO.getStaffTasksById(Main.userLogin.getId());
        if (!staffData.isEmpty()) {
            StaffTaskDTO firstStaffTask = staffData.get(0);
            setStaffDetails(firstStaffTask);
            calculateAndSetAverageMark(staffData);
            populateMonthlyMarkChart(staffData);
        }
        list.setAll(staffData);
        populateMonthFilterComboBox(staffData);
        filterByMonth(monthFilterComboBox.getValue());
    }

    private void setStaffDetails(StaffTaskDTO staffTask) {
        staffName.setText(staffTask.getFullName());
        staffDepartment.setText(staffTask.getDepartment());
        staffPosition.setText(staffTask.getPosition());
        setAvatarImage(staffTask.getAvatarPath());
    }

    private void setAvatarImage(String avatarPath) {
        if (avatarPath != null && !avatarPath.isEmpty()) {
            try {
                Image image = new Image(new File(avatarPath).toURI().toString());
                avatarImage.setImage(image);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid URL or resource not found: " + avatarPath);
                // You might want to set a default image here
            }
        }
    }

    private void calculateAndSetAverageMark(List<StaffTaskDTO> staffData) {
        double averageMark = staffData.stream()
                .mapToDouble(StaffTaskDTO::getMark)
                .average()
                .orElse(0);
        staffTotalMark.setText(String.format("%.2f", averageMark));
        updatePieChart(averageMark);
    }

    private void updatePieChart(double mark) {
        // Ensure the mark does not exceed 10
        if (mark > 10) {
            mark = 10;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Achieved", mark),
                new PieChart.Data("Remaining", 10 - mark)
        );

        // Update the PieChart data
        staffMonthlyMark.setData(pieChartData);
        pieChartData.forEach(data -> {
            data.nameProperty().bind(data.pieValueProperty().asString("%.2f"));
        });

        // Force a layout update
        staffMonthlyMark.layout();
    }

    private void populateMonthlyMarkChart(List<StaffTaskDTO> staffData) {
        Map<String, Double> monthlyAverageMarks = staffData.stream()
                .collect(Collectors.groupingBy(
                        task -> task.getTaskStartedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                        Collectors.averagingDouble(StaffTaskDTO::getMark)
                ));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        monthlyAverageMarks.forEach((month, avgMark) ->
                pieChartData.add(new PieChart.Data(month, avgMark))
        );

        staffMonthlyMark.setData(pieChartData);
        if (!monthlyAverageMarks.isEmpty()) {
            String currentMonth = monthlyAverageMarks.keySet().iterator().next();
            chartTitle.setText("Monthly total Evaluation mark for " + currentMonth);
        }

        // Force a layout update
        staffMonthlyMark.layout();
    }

    private void populateMonthFilterComboBox(List<StaffTaskDTO> staffData) {
        Set<String> months = staffData.stream()
                .map(task -> task.getTaskStartedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()))
                .collect(Collectors.toSet());
        monthFilterComboBox.setItems(FXCollections.observableArrayList(months));
    }

    private void filterByMonth(String month) {
        if (month == null) {
            filteredList.setAll(list);
            updatePieChart(filteredList);
            return;
        }
        List<StaffTaskDTO> filtered = list.stream()
                .filter(task -> task.getTaskStartedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()).equals(month))
                .collect(Collectors.toList());
        filteredList.setAll(filtered);
        updatePieChart(filtered);
    }

    private void updatePieChart(List<StaffTaskDTO> tasks) {
        if (tasks.isEmpty()) {
            staffMonthlyMark.setData(FXCollections.emptyObservableList());
            chartTitle.setText("Monthly total Evaluation mark");
            return;
        }

        double averageMark = tasks.stream()
                .mapToDouble(StaffTaskDTO::getMark)
                .average()
                .orElse(0);

        // Ensure the average mark does not exceed 10
        if (averageMark > 10) {
            averageMark = 10;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Achieved", averageMark),
                new PieChart.Data("Remaining", 10 - averageMark)
        );

        // Update the PieChart data
        staffMonthlyMark.setData(pieChartData);
        chartTitle.setText("Monthly total Evaluation mark for " + tasks.get(0).getTaskStartedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        // Ensure data is displayed properly
        pieChartData.forEach(data -> {
            data.nameProperty().bind(data.pieValueProperty().asString("%.2f"));
        });

        // Force a layout update
        staffMonthlyMark.layout();
    }
}
