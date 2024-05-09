package home;

/**
* @author Duc Linh
*/
public class StaffItem {
	int id;
	String name;
	String email;
	String phone;
	int taskComplete;
	int totalTask;
	int taskDelay;
	
	public StaffItem(int id, String name, String email, String phone, int totalTask, int taskComplete,  int taskDelay) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.taskComplete = taskComplete;
		this.totalTask = totalTask;
		this.taskDelay = taskDelay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getTaskComplete() {
		return taskComplete;
	}
	public void setTaskComplete(int taskComplete) {
		this.taskComplete = taskComplete;
	}
	public int getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(int totalTask) {
		this.totalTask = totalTask;
	}
	public int getTaskDelay() {
		return taskDelay;
	}
	public void setTaskDelay(int taskDelay) {
		this.taskDelay = taskDelay;
	}
	
	
	
}
