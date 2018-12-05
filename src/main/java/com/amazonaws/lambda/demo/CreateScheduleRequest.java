package com.amazonaws.lambda.demo;


import java.util.GregorianCalendar;

class CreateScheduleRequest {
	String scheduleName;
	int meetingDuration;
	GregorianCalendar sd;
	GregorianCalendar ed;
	int startTime;
	int endTime;
	String startDate;
	String endDate;
	
	public CreateScheduleRequest(String name, int duration, String startDate, String endDate, int st, int et) {
		this.scheduleName = name;
		this.meetingDuration = duration;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sd = parseDate(endDate);
		this.ed = parseDate(endDate);
		this.startTime = st;
		this.endTime = et;
	}
	
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(5, 7));
		int day = Integer.valueOf(date.substring(8));
		return new GregorianCalendar(year, month, day);
	}
	
	public String toString() {
		return "Creating schedule " + this.scheduleName + " from " + this.sd + " to " + this.ed +
				"\n Meetings start at " + this.startTime + " ends at " + this.endTime + " for " + this.meetingDuration + " minutes each.";
	}
}
