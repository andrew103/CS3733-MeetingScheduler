package com.amazonaws.lambda.getSchedule;

public class GetScheduleRequest {
	
	String shareCode;
	
	public GetScheduleRequest(String shareCode)
	{
		this.shareCode = shareCode;
	}
	
	public String toString()
	{
		return "Getting Schedule with code" + shareCode;
	}

}