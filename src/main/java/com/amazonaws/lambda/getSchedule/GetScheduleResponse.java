package com.amazonaws.lambda.getSchedule;

import entity.Schedule;

public class GetScheduleResponse {
	
	Schedule schedule;
	int httpCode;
	String message;
	
	public GetScheduleResponse(Schedule s) {
		this.schedule = s;
		this.httpCode = 200;
		this.message = "Found Schedule";
	}
	
	public GetScheduleResponse(String message, int httpCode) {
		this.httpCode = httpCode;
		this.schedule = null;
		this.message = message;
	}
	
	
	
	public String toString() {
		if(schedule == null) {
			return "Did not get schedule, error code: " + httpCode;
		}
		return "I got a schedule with name " + schedule.getScheduleName();
	}

}