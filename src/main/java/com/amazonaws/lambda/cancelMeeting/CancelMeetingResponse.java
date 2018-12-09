package com.amazonaws.lambda.cancelMeeting;

public class CancelMeetingResponse {
	
	String scheduleCode;
	String secretCode;
	int time;
	String day;
	int httpCode;

	public CancelMeetingResponse(String scheduleCode, String secretCode, int time, String day, int hc) {
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.day = day;
		this.httpCode = hc;
	}
	
	public CancelMeetingResponse(String scheduleCode, String secretCode, int time, String day)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.day = day;
		this.httpCode = 200;
	}
	
	public CancelMeetingResponse(String message, int code)
	{
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String printTime()
	{
		System.out.println(""+time);
		return ""+time;
	}
	
	public String toString()
	{
		return "Your meeting has been cancelled on " + this.day + " at " + this.time;
	}

}
