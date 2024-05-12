package Models.DAO.Impl;


import Models.DAO.StaffListDAO;
import Models.DTO.StaffDTO;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAOImpl implements StaffListDAO {

    private final Connection connection;

    public StaffDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addStaff(StaffDTO staffs) {
        try {

//            String sql2 = "INSERT INTO hrmandtaskallocation.dbo.staff (id, first_name, last_name, email, phone_number, department, position,\n" +
//                    "                                            user_name, password, permission, status, salary)";
            String sql = "INSERT INTO staff ( avatar, first_name, last_name, email, phone_number, department, position,user_name, password, permission, status, salary) VALUES (?, ?, ?, ? , ? , ? ,? , ? , ? , ? , ? , ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBinaryStream(1, staffs.getAvatarStream());
            statement.setString(2, staffs.getFirstName());
            statement.setString(3, staffs.getLastName());
            statement.setString(4, staffs.getEmail());
            statement.setString(5, staffs.getPhoneNumber());
            statement.setString(6, staffs.getDepartment());
            statement.setString(7, staffs.getPosition());
            statement.setString(8, staffs.getUserName());
            statement.setString(9, staffs.getPassword());
            statement.setLong(10, staffs.getPermission());
            statement.setString(11, staffs.getStatus());
            statement.setFloat(12, staffs.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStaff(StaffDTO staff) {
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, phone_number = ?, email = ?, department = ?, position = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getPhoneNumber());
            pstmt.setString(4, staff.getEmail());
            pstmt.setString(5, staff.getDepartment());
            pstmt.setString(6, staff.getPosition());
            pstmt.setLong(7, staff.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Thêm xử lý lỗi thích hợp nếu cần
        }
    }

    @Override
    public void deleteStaff(Long StaffId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM staff WHERE id=?");
            statement.setLong(1, StaffId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StaffDTO getStaffById(Long StaffId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE id=?");
//            statement.setInt(1, userId);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        List<StaffDTO> staffList = new ArrayList<>();

        // Tạo đối tượng StaffDTO và thêm vào danh sách
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM staff");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Lấy dữ liệu hình ảnh từ ResultSet
                //InputStream avatarStream = resultSet.getBinaryStream("avatar");
                // Tạo đối tượng Image từ InputStream
                //Image avatarImage = new Image(avatarStream);
                StaffDTO staff = new StaffDTO();
                staff.setId(resultSet.getLong("id"));

                byte[] varBinaryData = resultSet.getBytes("avatar");
                for(byte x : varBinaryData){
                    System.out.println(x);
                }
//                String convertedString = new String(varBinaryData);
                InputStream inputStream = new ByteArrayInputStream(varBinaryData);
                staff.setAvatarStream(inputStream);

                staff.setFirstName(resultSet.getString("first_name"));
                staff.setLastName(resultSet.getString("last_name"));
                staff.setEmail(resultSet.getString("email"));
                staff.setPhoneNumber(resultSet.getString("phone_number"));
                staff.setDepartment(resultSet.getString("department"));
                staff.setPosition(resultSet.getString("position"));
                staff.setUserName(resultSet.getString("user_name"));
                staff.setPassword(resultSet.getString("password"));
                staff.setPermission(resultSet.getLong("permission"));
                staff.setStatus(resultSet.getString("status"));
                staff.setSalary(resultSet.getFloat("salary"));
                System.out.println(staff.getId());
                //staffList.add(new StaffDTO(resultSet.getLong("id"), avatarStream  , resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("department"), resultSet.getString("position"), resultSet.getString("user_name"), resultSet.getString("password"), resultSet.getLong("permission"), resultSet.getString("status"), resultSet.getFloat("salary")));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

}