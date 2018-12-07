package com.amazonaws.lambda.createSchedule;


import java.util.GregorianCalendar;

class CreateScheduleRequest {
	String name;
	int meetingDuration;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	int startTime;
	int endTime;
	String sd;
	String ed;
	
	public CreateScheduleRequest(String name, int duration, String sd, String ed, int st, int et) {
		this.name = name;
		this.meetingDuration = duration;
		this.sd = sd;
		this.ed = ed;
		this.startTime = st;
		this.endTime = et;
	}
	
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8));
		return new GregorianCalendar(year, month, day);
	}
	
	public String toString() {
		return "Creating schedule " + this.name + " from " + this.sd + " to " + this.ed +
				"\n Meetings start at " + this.startTime + " ends at " + this.endTime + " for " + this.meetingDuration + " minutes each.";
	}
	
	public GregorianCalendar getStartDate() {
		return(parseDate(sd));
	}
	
	public GregorianCalendar getEndDate() {
		return(parseDate(ed));
	}
	
}