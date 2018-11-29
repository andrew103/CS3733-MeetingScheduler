package entity;

import java.util.ArrayList;

public class Day 
{
	int dayStart;
	int dayEnd;
	ArrayList<Timeslot> timeSlots;
	
	public Day(int start, int end) {
		this.dayStart = start;
		this.dayEnd = end;
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
	
	public void addTimeslot(Timeslot timeslot) {
		this.timeSlots.add(timeslot);
	}
	
	public ArrayList<Timeslot> getTimeSlots() {
		return timeSlots;
	}
	
}
