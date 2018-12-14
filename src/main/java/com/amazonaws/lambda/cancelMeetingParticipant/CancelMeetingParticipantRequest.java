package com.amazonaws.lambda.cancelMeetingParticipant;

public class CancelMeetingParticipantRequest {
	
	String scheduleCode;
	String meetingCode;
	int time;
	String day;
	
	public CancelMeetingParticipantRequest(String scheduleCode, String meetingCode, int time, String day)
	{
		this.scheduleCode = scheduleCode;
		this.meetingCode = meetingCode;
		this.time = time;
		this.day = day;
	}

	
	public String toString() {
		return "Canceling meeting with code: " + meetingCode; 
	}

}
