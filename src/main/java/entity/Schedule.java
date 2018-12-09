package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Schedule 
{
	ArrayList<Day> days;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	String startDateStr;
	String endDateStr;
	int startTime;
	int endTime;
	int meetingDuration;
	String organizerCode;
	String scheduleName;
	String shareCode;

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public Schedule(String name, GregorianCalendar startDate, GregorianCalendar endDate, int duration, int startTime, int endTime) {
		/*
		 * Use this constructor when creating new schedules
		 */
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.meetingDuration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organizerCode = randomAlphaNumeric(10);
		this.shareCode = randomAlphaNumeric(10);
		this.days = new ArrayList<Day>();
	}

	public Schedule(String name, GregorianCalendar startDate, GregorianCalendar endDate, int duration, int startTime, int endTime, String organizerCode, String shareCode) {
		/*
		 * Use this constructor when dealing with existing schedules
		 */
		this.scheduleName = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.meetingDuration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organizerCode = organizerCode;
		this.shareCode = shareCode;
		this.days = new ArrayList<Day>();
	}

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}

		return builder.toString();
	}
	
	public void makeDateStr() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		fmt.setCalendar(this.startDate);
	    this.startDateStr = fmt.format(this.startDate.getTime());

		fmt.setCalendar(this.endDate);
	    this.endDateStr = fmt.format(this.endDate.getTime());
	}
	
	public void addDay(Day day) {
		this.days.add(day);
	}
	
	public GregorianCalendar getStartDate()	{
		return startDate;
	}
	
	public GregorianCalendar getEndDate() {
		return endDate;
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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray daysJSON = new JSONArray();
        for (Day d: this.days) {
        	daysJSON.add(d.toJSON());
        }
     
		obj.put("startDate", fmt.format(this.startDate.getTime()));
		obj.put("endDate", fmt.format(this.endDate.getTime()));
		obj.put("sDateStr", this.startDateStr);
		obj.put("eDateStr", this.endDateStr);
		obj.put("meetingDurating", this.meetingDuration);
		obj.put("name", this.scheduleName);
		obj.put("days", daysJSON);
		
		return obj;
	}
	
}
