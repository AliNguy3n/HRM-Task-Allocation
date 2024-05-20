package home;

/**
* @author Duc Linh
*/
public class StaffItem {
	private int id;
	private String name;
	private String email;
	private String phone;
	private int taskComplete;
	private int totalTask;
	private int taskDelay;
	private String position;
	
	public StaffItem(int id, String name, String email, String phone, int taskComplete, int totalTask, int taskDelay,
			String position) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.taskComplete = taskComplete;
		this.totalTask = totalTask;
		this.taskDelay = taskDelay;
		this.position = position;
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

	public String getPosition() {
		return position;
	}

	public void setPoisition(String position) {
		this.position = position;
	}
	
	
	
}
