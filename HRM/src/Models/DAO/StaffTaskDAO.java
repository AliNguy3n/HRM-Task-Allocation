package Models.DAO;

import Models.DTO.StaffTaskDTO;
import user.UserLogin;

import java.util.List;

public interface StaffTaskDAO {
    List<StaffTaskDTO> getStaffTasksById(int id);
    List<StaffTaskDTO> getAllStaff();
    List<StaffTaskDTO> getStaffTasksByName(String name);
}
