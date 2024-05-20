package Models.DTO;

public class StaffEvaluationDTO {
    private Integer id;
    private Long staffId;
    private Long taskId;
    private Float mark;
    private String comment;

    public StaffEvaluationDTO() {}

    public StaffEvaluationDTO(Long taskId, Float mark, String comment, Long staffId, Integer id) {
        this.taskId = taskId;
        this.mark = mark;
        this.comment = comment;
        this.staffId = staffId;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaskId() {
        return taskId;
    }

    public void setStaskId(Long staskId) {
        this.taskId = staskId;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "StaffEvaluationDTO{" +
                "id=" + id +
                ", staffId=" + staffId +
                ", taskId=" + taskId +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                '}';
    }
}
