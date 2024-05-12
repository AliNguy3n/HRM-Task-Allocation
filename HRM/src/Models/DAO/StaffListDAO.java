package Models.DAO;



import Models.DTO.StaffDTO;

import java.util.List;

public interface StaffListDAO {
    void addStaff(StaffDTO staffs);
    void updateStaff(StaffDTO staffs);
    void deleteStaff(Long StaffId);
    StaffDTO getStaffById(Long StaffId);
    List<StaffDTO> getAllStaffs();
}
