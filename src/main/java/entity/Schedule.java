package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Schedule 
{
	ArrayList<Day> days;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	int meetingDuration;
	String organizerCode;
	String scheduleName;
	String shareCode;
	
	public Schedule(String name, GregorianCalendar startDate, GregorianCalendar endDate, String shareCode)
	{
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareCode = shareCode;
	}
	
	public GregorianCalendar getStartDate()
	{
		return startDate;
	}
	
	public GregorianCalendar getEndDate()
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
