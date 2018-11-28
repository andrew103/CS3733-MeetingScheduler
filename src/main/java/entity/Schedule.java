package entity;

import java.util.ArrayList;
import java.util.Date;

public class Schedule 
{
	ArrayList<Day> days;
	Date startDate;
	Date endDate;
	int meetingDuration;
	String organizerCode;
	String scheduleName;
	String shareCode;
	
	public Schedule(String name, Date startDate, Date endDate, String shareCode)
	{
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareCode = shareCode;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return startDate;
	}
	
	public String getOrganizerCode()
	{
		return organizerCode;
	}
	
	public String getScheduleName()
	{
		return scheduleName;
	}
	
	public String getShareCode()
	{
		return shareCode;
	}
	
	public ArrayList<Day> getDays()
	{
		return days;
	}
}
