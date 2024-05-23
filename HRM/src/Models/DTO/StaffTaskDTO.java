package Models.DTO;

import java.sql.Date;

public class StaffTaskDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String department;
    private String position;
    private String avatarPath;
    private String taskTitle;
    private Date taskStartedDate;
    private Date taskEndedDate;
    private int assignedBy;
    private float mark;
    private float averageMark;
    private float monthlyMark;
    private float totalMark;

    public float getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(float totalMark) {
        this.totalMark = totalMark;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public float getMonthlyMark() {
        return monthlyMark;
    }

    public void setMonthlyMark(float monthlyMark) {
        this.monthlyMark = monthlyMark;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }
}
