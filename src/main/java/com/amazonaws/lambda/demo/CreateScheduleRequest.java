package com.amazonaws.lambda.demo;


import java.util.GregorianCalendar;

class CreateScheduleRequest {
	String scheduleName;
	int meetingDuration;
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	int startTime;
	int endTime;
	
	public CreateScheduleRequest(String name, int duration, GregorianCalendar sd, GregorianCalendar ed, int st, int et) {
		this.scheduleName = name;
		this.meetingDuration = duration;
		this.startDate = sd;
		this.endDate = ed;
		this.startTime = st;
		this.endTime = et;
	}
	
	public String toString() {
		return "Creating schedule " + scheduleName + " from " + startDate.getTime().toString() + " to " + endDate.getTime().toString() +
				"\n Meetings start at " + startTime + " ends at " + endTime + " for " + meetingDuration + " minutes each.";
	}
}
