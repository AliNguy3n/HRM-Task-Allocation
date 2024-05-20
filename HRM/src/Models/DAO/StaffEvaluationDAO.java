package Models.DAO;

import Models.DTO.StaffEvaluationDTO;

import java.util.List;

public interface StaffEvaluationDAO {
    List<StaffEvaluationDTO> getAllEvaluation();
    List<StaffEvaluationDTO> getEvaluationByStaffId(Long staffId);
}
