package com.amazonaws.lambda.cancelMeeting;

public class CancelMeetingRequest {
	
	String scheduleCode;
	String secretCode;
	int time;
	String day;
	
	public CancelMeetingRequest(String scheduleCode, String secretCode, int time, String day)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.day = day; 
	}
	
	public String printTime()
	{
		System.out.println(""+time);
		return ""+time;
	}
	
	public String toString() {
		return "Canceling meeting on " + this.day + " at " + this.time; 
	}

}
