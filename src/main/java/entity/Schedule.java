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

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public Schedule(String name, GregorianCalendar startDate, GregorianCalendar endDate, int duration, int startTime, int endTime) {
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.meetingDuration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organizerCode = randomAlphaNumeric(10);
		this.shareCode = randomAlphaNumeric(10);
	}
	
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}

		return builder.toString();
	}
	
	public GregorianCalendar getStartDate()	{
		return startDate;
	}
	
	public GregorianCalendar getEndDate() {
		return startDate;
	}
	
	public String getOrganizerCode() {
		return organizerCode;
	}
	
	public String getScheduleName()	{
		return scheduleName;
	}
	
	public String getShareCode() {
		return shareCode;
	}
	
	public int getDuration() {
		return meetingDuration;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public ArrayList<Day> getDays()	{
		return days;
	}
}
