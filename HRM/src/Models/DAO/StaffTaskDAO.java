package Models.DAO;

import Models.DTO.StaffTaskDTO;
import user.UserLogin;

import java.util.List;

public interface StaffTaskDAO {
    List<StaffTaskDTO> getStaffTasksById(int id);
}
