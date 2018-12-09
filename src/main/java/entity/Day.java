package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Day 
{
	int dayStart;
	int dayEnd;
	GregorianCalendar date;
	String dateStr;
	ArrayList<Timeslot> timeSlots;
	
	public Day(int start, int end, GregorianCalendar date) {
		this.dayStart = start;
		this.dayEnd = end;
		this.date = date;
		this.timeSlots = new ArrayList<Timeslot>();
	}
	
	public void cancelMeeting(String code) {
//		for (Timeslot slot: timeSlots)
//		{
//			if (slot.m.getMeetingCode() == code)
//			{
//				slot.m =d null; 
//			}
//		}
	}
	
	public void makeDateStr() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		fmt.setCalendar(this.date);
	    this.dateStr = fmt.format(this.date.getTime());
	}
	
	public void addTimeslot(Timeslot timeslot) {
		timeSlots.add(timeslot);
	}
	
	public ArrayList<Timeslot> getTimeSlots() {
		return timeSlots;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray timeSlotsJSON = new JSONArray();
        for (Timeslot ts: this.timeSlots) {
        	timeSlotsJSON.add(ts.toJSON());
        }
     
		obj.put("date", fmt.format(this.date.getTime()));
		obj.put("dateStr", this.dateStr);
		obj.put("dayStart", this.dayStart);
		obj.put("dayEnd", this.dayEnd);
		obj.put("timeslots", timeSlotsJSON);
		
		return obj;
	}
	
}
