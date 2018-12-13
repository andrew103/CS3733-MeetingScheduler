package com.amazonaws.lambda.deleteOldSchedules;

public class DeleteOldSchedulesResponse {
	
	int httpCode;
	int days;
	String message;
	
	public DeleteOldSchedulesResponse(int days, int hc) {
		this.httpCode = hc;
		this.message = "Found Schedules";
		this.days = days;
	}
	
	public DeleteOldSchedulesResponse(String message, int httpCode) {
		this.httpCode = httpCode;
		this.message = message;
	}
	
	public String toString() {
		return "Deleted schedules that were " + days + " days old.";
	}

}
