package home;

import java.sql.Date;


/**
* @author Duc Linh
*/
public class RequestItem {
	private int requestid;
	private int from;
	private String request;
	private Date timestamp;
	private int seem;
	private  String title;
	private String name;
	private int taskid;
	public RequestItem(int requestid, int from, String request, Date timestamp, int seem, String title, String name,
			int taskid) {
		super();
		this.requestid = requestid;
		this.from = from;
		this.request = request;
		this.timestamp = timestamp;
		this.seem = seem;
		this.title = title;
		this.name = name;
		this.taskid = taskid;
	}
	public int getRequestid() {
		return requestid;
	}
	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getSeem() {
		return seem;
	}
	public void setSeem(int seem) {
		this.seem = seem;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	
	
}
