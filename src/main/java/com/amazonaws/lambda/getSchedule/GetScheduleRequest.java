package com.amazonaws.lambda.getSchedule;

public class GetScheduleRequest {
	
	String shareCode;
	String secretCode;
	
	public GetScheduleRequest(String shareCode, String secretCode)
	{
		this.shareCode = shareCode;
		this.secretCode = secretCode;
	}
	
	public String toString()
	{
		return "Getting Schedule with code" + shareCode;
	}

}