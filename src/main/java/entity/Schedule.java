package entity;

import java.util.ArrayList;

public class Schedule 
{
	private ArrayList<Day> days;
	private Date startDate;
	private Date endDate;
	private int meetingDuration;
	private String organizerCode;
	private String scheduleName;
	private String shareCode;
	
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
		return organizerCode;
	}
	
	public String getShareCode()
	{
		return organizerCode;
	}
	
	public ArrayList<Day> getDays()
	{
		return days;
	}
}
