package home;

import java.sql.Date;


/**
* @author Duc Linh
*/
public class RequestItem {
	private int id;
	private int from;
	private String request;
	private Date timestamp;
	private int seem;
	private  String title;
	private String name;

	public RequestItem(int id, int from, String request, Date timestamp, int seem, String title, String name) {
		super();
		this.id = id;
		this.from = from;
		this.request = request;
		this.timestamp = timestamp;
		this.seem = seem;
		this.title = title;
		this.name = name;

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
}
