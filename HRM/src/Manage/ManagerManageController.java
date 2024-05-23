package Manage;

import Config.DBConnection;
import Models.DAO.Impl.StaffTaskDAOImpl;
import Models.DAO.StaffTaskDAO;
import Models.DTO.StaffTaskDTO;
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
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerManageController implements Initializable {

    @FXML
    private Text chartTitle;

    @FXML
    private TableColumn<StaffTaskDTO, String> departmentCol;

    @FXML
    private TableView<StaffTaskDTO> evaluationTable;

    @FXML
    private TableColumn<StaffTaskDTO, String> monthCol;

    @FXML
    private ComboBox<String> monthFilterComboBox;

    @FXML
    private TableColumn<StaffTaskDTO, Float> monthlyMarkCol;

    @FXML
    private TableColumn<StaffTaskDTO, String> positionCol;

    @FXML
    private PieChart staffMonthlyMark;

    @FXML
    private TableColumn<StaffTaskDTO, String> staffNameCol;

    @FXML
    private TableColumn<StaffTaskDTO, Float> totalMarkCol;

    @FXML
    private ComboBox<String> employeeComboBox;

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

        staffNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        totalMarkCol.setCellValueFactory(new PropertyValueFactory<>("totalMark"));
        monthCol.setCellValueFactory(cellData -> {
            LocalDate endedDate = cellData.getValue().getTaskEndedDate() != null ? cellData.getValue().getTaskEndedDate().toLocalDate() : null;
            return new ReadOnlyObjectWrapper<>(endedDate != null ? endedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) : "");
        });
        monthlyMarkCol.setCellValueFactory(new PropertyValueFactory<>("monthlyMark"));

        evaluationTable.setItems(filteredList);

        monthFilterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> filterByMonth(newValue));
        employeeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> showEmployeeMarkDetails(newValue));
    }

    private void loadStaffData() {
        List<StaffTaskDTO> staffData = staffDAO.getAllStaff();
        Map<String, StaffTaskDTO> totalMarks = calculateTotalMarks(staffData);
        list.setAll(aggregateStaffDataByMonth(staffData, totalMarks));
        populateMonthFilterComboBox(staffData);
        populateEmployeeComboBox(staffData);
        filterByMonth(monthFilterComboBox.getValue());
    }

    private Map<String, StaffTaskDTO> calculateTotalMarks(List<StaffTaskDTO> staffData) {
        return staffData.stream()
                .collect(Collectors.groupingBy(StaffTaskDTO::getFullName))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            StaffTaskDTO representativeTask = entry.getValue().get(0);
                            float totalMark = (float) entry.getValue().stream().mapToDouble(StaffTaskDTO::getMark).average().orElse(0);
                            representativeTask.setTotalMark(totalMark);
                            return representativeTask;
                        }
                ));
    }

    private List<StaffTaskDTO> aggregateStaffDataByMonth(List<StaffTaskDTO> staffData, Map<String, StaffTaskDTO> totalMarks) {
        Map<String, List<StaffTaskDTO>> aggregatedData = new HashMap<>();

        for (StaffTaskDTO task : staffData) {
            if (task.getTaskEndedDate() == null) continue;

            String key = task.getFullName() + "_" + task.getTaskEndedDate().toLocalDate().getMonth().getValue();
            aggregatedData.computeIfAbsent(key, k -> new ArrayList<>()).add(task);
        }

        return aggregatedData.values().stream().map(tasks -> {
            StaffTaskDTO representativeTask = tasks.get(0);
            float averageMark = (float) tasks.stream().mapToDouble(StaffTaskDTO::getMark).average().orElse(0);
            representativeTask.setMonthlyMark(averageMark);
            representativeTask.setTotalMark(totalMarks.get(representativeTask.getFullName()).getTotalMark());
            return representativeTask;
        }).collect(Collectors.toList());
    }

    private void populateMonthFilterComboBox(List<StaffTaskDTO> staffData) {
        Set<String> months = staffData.stream()
                .map(task -> task.getTaskEndedDate() != null ? task.getTaskEndedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        monthFilterComboBox.setItems(FXCollections.observableArrayList(months));
    }

    private void populateEmployeeComboBox(List<StaffTaskDTO> staffData) {
        Set<String> employees = staffData.stream()
                .map(StaffTaskDTO::getFullName)
                .collect(Collectors.toSet());
        employeeComboBox.setItems(FXCollections.observableArrayList(employees));
    }

    private void filterByMonth(String month) {
        if (month == null) {
            filteredList.setAll(list);
            updatePieChart(filteredList);
            return;
        }
        List<StaffTaskDTO> filtered = list.stream()
                .filter(task -> task.getTaskEndedDate() != null && task.getTaskEndedDate().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()).equals(month))
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
                .mapToDouble(StaffTaskDTO::getMonthlyMark)
                .average()
                .orElse(0);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Achieved", averageMark),
                new PieChart.Data("Remaining", 100 - averageMark)
        );

        staffMonthlyMark.setData(pieChartData);
        chartTitle.setText("Monthly total Evaluation mark");

        pieChartData.forEach(data -> data.nameProperty().bind(data.pieValueProperty().asString("%.2f")));

        staffMonthlyMark.layout();
    }

    private void showEmployeeMarkDetails(String employeeName) {
        if (employeeName == null) {
            return;
        }

        List<StaffTaskDTO> tasks = staffDAO.getStaffTasksByName(employeeName);
        double averageMark = tasks.stream().mapToDouble(StaffTaskDTO::getMark).average().orElse(0);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Achieved", averageMark),
                new PieChart.Data("Remaining", 100 - averageMark)
        );

        staffMonthlyMark.setData(pieChartData);
        chartTitle.setText("Employee Mark Analysis for " + employeeName);

        pieChartData.forEach(data -> data.nameProperty().bind(data.pieValueProperty().asString("%.2f")));

        staffMonthlyMark.layout();
    }
}
