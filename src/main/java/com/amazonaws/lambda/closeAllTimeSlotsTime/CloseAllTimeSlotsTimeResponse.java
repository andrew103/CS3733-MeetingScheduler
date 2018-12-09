package com.amazonaws.lambda.closeAllTimeSlotsTime;

public class CloseAllTimeSlotsTimeResponse {
	
	String scheduleCode;
	String secretCode;
	int time;
	int httpCode; 
	
	public CloseAllTimeSlotsTimeResponse(String scheduleCode, String secretCode, int time, int hc)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.httpCode = hc;
	}

	public CloseAllTimeSlotsTimeResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = 200;
	}
	
	public String toString()
	{
		return "All slots have been closed at " + this.time;
	}

}
