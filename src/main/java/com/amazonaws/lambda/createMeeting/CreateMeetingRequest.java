package com.amazonaws.lambda.createMeeting;

public class CreateMeetingRequest {
	
	String scheduleCode;
	String secretCode;
	String participantInfo;
	String meetingCode;
	int time;
	String day;
	
	public CreateMeetingRequest(String scheduleCode, String secretCode, int time, String day, String participantInfo, String meetingCode)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.participantInfo = participantInfo;
		this.meetingCode = meetingCode;
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
