package Models.DAO.Impl;

import Models.DAO.StaffEvaluationDAO;
import Models.DTO.StaffEvaluationDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffEvaluationDAOImpl implements StaffEvaluationDAO {

    private final Connection connection;

    public StaffEvaluationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<StaffEvaluationDTO> getAllEvaluation() {
        List<StaffEvaluationDTO> staffEvaluations = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM evaluation");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                StaffEvaluationDTO staffEvaluation = new StaffEvaluationDTO();
                staffEvaluation.setId(rs.getInt("id"));
                staffEvaluation.setStaffId(rs.getLong("staffid"));
                staffEvaluation.setStaskId(rs.getLong("taskid"));
                staffEvaluation.setMark(rs.getFloat("mark"));
                staffEvaluation.setComment(rs.getString("comment"));
                staffEvaluations.add(staffEvaluation);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return staffEvaluations;
    }

    @Override
    public List<StaffEvaluationDTO> getEvaluationByStaffId(Long staffId) {
        List<StaffEvaluationDTO> staffEvaluations = new ArrayList<>();
        try{
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM evaluation WHERE staffid = ?");
            pstm.setLong(1, staffId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                StaffEvaluationDTO staffEvaluation = new StaffEvaluationDTO();
                staffEvaluation.setId(rs.getInt("id"));
                staffEvaluation.setStaffId(rs.getLong("staffid"));
                staffEvaluation.setStaskId(rs.getLong("taskid"));
                staffEvaluation.setMark(rs.getFloat("mark"));
                staffEvaluation.setComment(rs.getString("comment"));
                staffEvaluations.add(staffEvaluation);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return staffEvaluations;
    }
}
