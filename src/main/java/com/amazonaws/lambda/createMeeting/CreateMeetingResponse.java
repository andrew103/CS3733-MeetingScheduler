package com.amazonaws.lambda.createMeeting;

public class CreateMeetingResponse {
	
	String scheduleCode;
	String participantInfo;
	String meetingCode;
	int time;
	String day;
	int httpCode;

	public CreateMeetingResponse(String scheduleCode, int time, String day, String participantInfo, String meetingCode, int hc) {
		this.scheduleCode = scheduleCode;
		this.participantInfo = participantInfo;
		this.meetingCode = meetingCode;
		this.time = time;
		this.day = day;
		this.httpCode = hc;
	}
	
	public CreateMeetingResponse(String scheduleCode, int time, String day, String participantInfo, String meetingCode)
	{
		this.scheduleCode = scheduleCode;
		this.participantInfo = participantInfo;
		this.meetingCode = meetingCode;
		this.time = time;
		this.day = day;
		this.httpCode = 200;
	}
	
	public CreateMeetingResponse(String message, int code)
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
		return "Your meeting has been created on " + this.day + " at " + this.time + " with the meeting code: "+ meetingCode;
	}

}
