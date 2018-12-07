package com.amazonaws.lambda.getSchedule;

public class GetScheduleRequest {
	
	String scheduleCode;
	
	public GetScheduleRequest(String scheduleCode)
	{
		this.scheduleCode = scheduleCode;
	}
	
	public String toString()
	{
		return "Getting Schedule with code" + scheduleCode;
	}

}