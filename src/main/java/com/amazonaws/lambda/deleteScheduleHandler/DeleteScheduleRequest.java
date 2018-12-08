package com.amazonaws.lambda.deleteScheduleHandler;

import java.util.GregorianCalendar;

public class DeleteScheduleRequest {
	
	String scheduleCode = "";
	String secretCode = "";
	
	public DeleteScheduleRequest(String scheduleCode, String secretCode) {
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
	}
	
	public String toString() {
		return "Deleting schedule. Schedule Code: " + this.scheduleCode + ", Secret Code: " + this.secretCode;
	}
}