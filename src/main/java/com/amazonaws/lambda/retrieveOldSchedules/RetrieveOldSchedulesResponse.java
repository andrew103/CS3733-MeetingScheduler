package com.amazonaws.lambda.retrieveOldSchedules;

import java.util.ArrayList;

public class RetrieveOldSchedulesResponse {
	
	ArrayList<String> schedules;
	int httpCode;
	String message;
	
	public RetrieveOldSchedulesResponse(ArrayList<String> s, int hc) {
		this.schedules = s;
		this.httpCode = hc;
		this.message = "Found Schedules";
	}
	
	public RetrieveOldSchedulesResponse(String message, int httpCode) {
		this.httpCode = httpCode;
		this.schedules = null;
		this.message = message;
	}
	
	public String toString() {
		if(schedules == null) {
			return "Did not get any schedules, error code: " + httpCode;
		}
		else
		{
			String resp = "Found schedule names: ";
			for (String s : schedules)
			{
				resp = resp + s + ", ";
			}
			return resp;
		}
	}

}
