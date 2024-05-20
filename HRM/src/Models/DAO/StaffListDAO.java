package Models.DAO;



import Models.DTO.StaffDTO;
import user.UserLogin;

import java.util.List;

public interface StaffListDAO {
    void addStaff(StaffDTO staffs);
    void updateStaff(StaffDTO staffs);
    void deleteStaff(Long StaffId);
    StaffDTO getStaffById(Long StaffId);
    List<StaffDTO> getAllStaffs();
    void updateStaffbyUserLogin(UserLogin user);
    boolean isUsernameExist(String username);
    void updateStaffPassword(UserLogin staff);
}
