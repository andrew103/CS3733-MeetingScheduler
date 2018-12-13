package com.amazonaws.lambda.createMeeting;

public class CreateMeetingRequest {
	
	String scheduleCode;
	String participantInfo;
	int time;
	String day;
	
	public CreateMeetingRequest(String scheduleCode, String participantInfo, int time, String day)
	{
		this.scheduleCode = scheduleCode;
		this.participantInfo = participantInfo;
		this.time = time;
		this.day = day; 
	}
	
	public String printTime()
	{
		System.out.println(""+time);
		return ""+time;
	}
	
	public String toString() {
		return "Creating meeting on " + this.day + " at " + this.time; 
	}

}
