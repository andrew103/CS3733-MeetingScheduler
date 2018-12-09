package com.amazonaws.lambda.cancelMeetingParticipant;

public class CancelMeetingParticipantRequest {
	
	String scheduleCode;
	String secretCode;
	String meetingCode;

	
	public CancelMeetingParticipantRequest(String scheduleCode, String meetingCode)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.meetingCode = meetingCode;
	}

	
	public String toString() {
		return "Canceling meeting with code: " + meetingCode; 
	}

}
