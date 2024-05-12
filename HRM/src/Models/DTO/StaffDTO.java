package Models.DTO;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.io.InputStream;

public class StaffDTO {
    private Long id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String position;
    private String userName;
    private String password;
    private Long permission;
    private String status;
    private Float salary;
    private ObjectProperty<Image> avatar = new SimpleObjectProperty<>();
    private InputStream avatarStream;

    @Override
    public String toString() {
        return "StaffDTO{" +
                "Id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", status='" + status + '\'' +
                ", salary=" + salary +
                ", image=" + avatar +
                ", avatarStream=" + avatarStream +
                '}';
    }

    public StaffDTO(Long id, InputStream avatarStream, String firstName, String lastName, String email, String phoneNumber, String department, String position, String userName, String password, Long permission, String status, Float salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.position = position;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.salary = salary;
        this.avatarStream = avatarStream;
        this.fullName = String.valueOf(new SimpleStringProperty(firstName + " " + lastName));
    }

    public StaffDTO(Long id, String fullName, String firstName, String lastName, String email, String phoneNumber, String department, String position, String userName, String password, Long permission, String status, Float salary) {
        this.id = id;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.position = position;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.salary = salary;
    }

    public StaffDTO() {
    }

    public Image getImage() {
        return avatar.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return avatar;
    }

    public void setImage(Image image) {
        this.avatar.set(image);
    }

    public Long getId() {
        return this.id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPermission() {
        return permission;
    }

    public void setPermission(Long permission) {
        this.permission = permission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public ObservableValue<Image> avatarProperty() {
        return avatar;
    }
    // Getter và setter cho avatarStream
    public InputStream getAvatarStream() {
        return avatarStream;
    }

    public void setAvatarStream(InputStream avatarStream) {
        this.avatarStream = avatarStream;
    }
    public Image getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Image avatar) {
        this.avatar.set(avatar);
    }

    public StringProperty fullNameProperty() {
        return new SimpleStringProperty(this.getFirstName() + " " + this.getLastName());
    }

    // Phương thức getter cho fullName
    public String getFullName() {
        return this.fullName;
    }

    // Phương thức setter cho fullName
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
