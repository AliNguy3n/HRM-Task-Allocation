package Models.DAO.Impl;

import Models.DAO.StaffTaskDAO;
import Models.DTO.StaffTaskDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffTaskDAOImpl implements StaffTaskDAO {

    private Connection conn;
    public StaffTaskDAOImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<StaffTaskDTO> getAllStaff() {
        List<StaffTaskDTO> staffTasks = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT s.id, s.avatar, s.first_name, s.last_name, s.department, s.position, " +
                    "t.title, t.started_date, t.ended_date, t.assignedby, e.mark " +
                    "FROM staff s " +
                    "JOIN evaluation e ON s.id = e.staffid " +
                    "JOIN task t ON e.taskid = t.id " +
                    "JOIN stafftask st ON t.id = st.taskid";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StaffTaskDTO staffTask = new StaffTaskDTO();
                staffTask.setId(rs.getLong("id"));
                staffTask.setAvatarPath(rs.getString("avatar"));
                staffTask.setFirstName(rs.getString("first_name"));
                staffTask.setLastName(rs.getString("last_name"));
                staffTask.setFullName(staffTask.getFirstName() + " " + staffTask.getLastName());
                staffTask.setDepartment(rs.getString("department"));
                staffTask.setPosition(rs.getString("position"));
                staffTask.setTaskTitle(rs.getString("title"));
                staffTask.setTaskStartedDate(rs.getDate("started_date"));
                staffTask.setTaskEndedDate(rs.getDate("ended_date"));
                staffTask.setAssignedBy(rs.getInt("assignedby"));
                staffTask.setMark(rs.getFloat("mark"));
                staffTasks.add(staffTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffTasks;
    }


    @Override
    public List<StaffTaskDTO> getStaffTasksById(int id) {
        List<StaffTaskDTO> staffTasks = new ArrayList<>();

        try {
            String query = "SELECT s.id, s.avatar, s.first_name, s.last_name, s.department, s.position, " +
                    "t.title, t.started_date, t.ended_date, t.assignedby, e.mark " +
                    "FROM staff s " +
                    "JOIN evaluation e ON s.id = e.staffid " +
                    "JOIN task t ON e.taskid = t.id " +
                    "JOIN stafftask st ON t.id = st.taskid " +
                    "WHERE s.id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StaffTaskDTO staffTask = new StaffTaskDTO();
                staffTask.setId(rs.getLong("id"));
                staffTask.setAvatarPath(rs.getString("avatar"));
                staffTask.setFirstName(rs.getString("first_name"));
                staffTask.setLastName(rs.getString("last_name"));
                staffTask.setFullName(staffTask.getFirstName() + " " + staffTask.getLastName());
                staffTask.setDepartment(rs.getString("department"));
                staffTask.setPosition(rs.getString("position"));
                staffTask.setTaskTitle(rs.getString("title"));
                staffTask.setTaskStartedDate(rs.getDate("started_date"));
                staffTask.setTaskEndedDate(rs.getDate("ended_date"));
                staffTask.setAssignedBy(rs.getInt("assignedby"));
                staffTask.setMark(rs.getFloat("mark"));
                staffTasks.add(staffTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffTasks;
    }

    @Override
    public List<StaffTaskDTO> getStaffTasksByName(String name) {
        List<StaffTaskDTO> staffTasks = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT s.id, s.avatar, s.first_name, s.last_name, s.department, s.position, " +
                    "t.title, t.started_date, t.ended_date, t.assignedby, e.mark " +
                    "FROM staff s " +
                    "JOIN evaluation e ON s.id = e.staffid " +
                    "JOIN task t ON e.taskid = t.id " +
                    "JOIN stafftask st ON t.id = st.taskid " +
                    "WHERE s.first_name + ' ' + s.last_name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StaffTaskDTO staffTask = new StaffTaskDTO();
                staffTask.setId(rs.getLong("id"));
                staffTask.setAvatarPath(rs.getString("avatar"));
                staffTask.setFirstName(rs.getString("first_name"));
                staffTask.setLastName(rs.getString("last_name"));
                staffTask.setFullName(staffTask.getFirstName() + " " + staffTask.getLastName());
                staffTask.setDepartment(rs.getString("department"));
                staffTask.setPosition(rs.getString("position"));
                staffTask.setTaskTitle(rs.getString("title"));
                staffTask.setTaskStartedDate(rs.getDate("started_date"));
                staffTask.setTaskEndedDate(rs.getDate("ended_date"));
                staffTask.setAssignedBy(rs.getInt("assignedby"));
                staffTask.setMark(rs.getFloat("mark"));
                staffTasks.add(staffTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffTasks;
    }

}
