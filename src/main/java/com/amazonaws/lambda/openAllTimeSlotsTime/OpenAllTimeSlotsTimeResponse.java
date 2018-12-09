package com.amazonaws.lambda.openAllTimeSlotsTime;

public class OpenAllTimeSlotsTimeResponse {
	
	String scheduleCode;
	String secretCode;
	int time;
	int httpCode; 
	
	public OpenAllTimeSlotsTimeResponse(String scheduleCode, String secretCode, int time, int hc)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.httpCode = hc;
	}

	public OpenAllTimeSlotsTimeResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = 200;
	}
	
	public String toString()
	{
		return "All slots have been opened on " + this.time;
	}

}
