package com.amazonaws.lambda.demo;


import java.util.GregorianCalendar;

class CreateScheduleRequest {
	String scheduleName;
	int meetingDuration;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	int startTime;
	int endTime;
	String sd;
	String ed;
	
	public CreateScheduleRequest(String name, int duration, String sd, String ed, int st, int et) {
		this.scheduleName = name;
		this.meetingDuration = duration;
		this.startDate = parseDate(sd);
		this.endDate = parseDate(ed);
		this.sd = sd;
		this.ed = ed;
		this.startTime = st;
		this.endTime = et;
	}
	
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 3));
		int month = Integer.parseInt(date.substring(5, 6));
		int day = Integer.parseInt(date.substring(8, 9));
		return new GregorianCalendar(year, month, day);
	}
	
	public String toString() {
		return "Creating schedule " + scheduleName + " from " + sd + " to " + ed +
				"\n Meetings start at " + startTime + " ends at " + endTime + " for " + meetingDuration + " minutes each.";
	}
}
