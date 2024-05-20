package Models.DTO;

import java.sql.Date;

public class StaffTaskDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private String avatarPath;
    private String taskTitle;
    private Date taskStartedDate;
    private Date taskEndedDate;
    private Integer AssignedBy;
    private Float Mark;
    private String fullName;

    public StaffTaskDTO(Long id, String firstName, String lastName, String department, String position, String avatarPath, String taskTitle, Date taskStartedDate, Date taskEndedDate, Integer assignedBy, Float mark) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.avatarPath = avatarPath;
        this.taskTitle = taskTitle;
        this.taskStartedDate = taskStartedDate;
        this.taskEndedDate = taskEndedDate;
        AssignedBy = assignedBy;
        Mark = mark;
    }

    public StaffTaskDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Date getTaskStartedDate() {
        return taskStartedDate;
    }

    public void setTaskStartedDate(Date taskStartedDate) {
        this.taskStartedDate = taskStartedDate;
    }

    public Date getTaskEndedDate() {
        return taskEndedDate;
    }

    public void setTaskEndedDate(Date taskEndedDate) {
        this.taskEndedDate = taskEndedDate;
    }

    public Integer getAssignedBy() {
        return AssignedBy;
    }

    public void setAssignedBy(Integer assignedBy) {
        AssignedBy = assignedBy;
    }

    public Float getMark() {
        return Mark;
    }

    public void setMark(Float mark) {
        Mark = mark;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
