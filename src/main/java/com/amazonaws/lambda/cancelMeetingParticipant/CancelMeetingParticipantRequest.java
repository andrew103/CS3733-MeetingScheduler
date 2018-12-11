package com.amazonaws.lambda.cancelMeetingParticipant;

public class CancelMeetingParticipantRequest {
	
	String scheduleCode;
	String meetingCode;
	
	public CancelMeetingParticipantRequest(String scheduleCode, String meetingCode)
	{
		this.scheduleCode = scheduleCode;
		this.meetingCode = meetingCode;
	}

	
	public String toString() {
		return "Canceling meeting with code: " + meetingCode; 
	}

}
