package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Schedule 
{
	ArrayList<Day> days;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	int startTime;
	int endTime;
	int meetingDuration;
	String organizerCode;
	String scheduleName;
	String shareCode;
	
	
	public Schedule(String name, GregorianCalendar startDate, GregorianCalendar endDate, String shareCode, int duration, int startTime, int endTime)
	{
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareCode = shareCode;
		this.meetingDuration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
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
