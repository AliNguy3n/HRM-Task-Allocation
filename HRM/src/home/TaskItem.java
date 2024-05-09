package home;

import java.sql.Date;
import java.sql.Time;

/**
* @author Duc Linh
*/
public class TaskItem {
	int id;
	String title;
	String content;
	int to;
	Date startDate;
	Time startTime;
	Date finishDate;
	Time finishTime;
	public TaskItem(int id, String title, String content, int to, Date startDate, Time startTime, Date finishDate,
			Time finishTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.to = to;
		this.startDate = startDate;
		this.startTime = startTime;
		this.finishDate = finishDate;
		this.finishTime = finishTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Time getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Time finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
