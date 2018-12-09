package com.amazonaws.lambda.cancelMeetingParticipant;

public class CancelMeetingParticipantResponse {
	
	String scheduleCode;
	String meetingCode;
	int httpCode;

	public CancelMeetingParticipantResponse(String scheduleCode, String meetingCode, int hc) {
		this.scheduleCode = scheduleCode;
		this.meetingCode = meetingCode;
		this.httpCode = hc;
	}
	
	public CancelMeetingParticipantResponse(String scheduleCode, String meetingCode)
	{
		this.scheduleCode = scheduleCode;
		this.meetingCode = meetingCode;
		this.httpCode = 200;
	}
	
	public CancelMeetingParticipantResponse(String message, int code)
	{
		System.out.println(message);
		this.httpCode = code;
	}
	
	
	public String toString()
	{
		return "Your meeting with the code " + meetingCode + " has been canceled.";
	}

}
