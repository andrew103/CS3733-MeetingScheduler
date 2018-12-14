package com.amazonaws.lambda.openAllTimeSlotsTime;

public class OpenAllTimeSlotsTimeRequest {
	
	String scheduleCode;
	String secretCode;
	int time;
	
	public OpenAllTimeSlotsTimeRequest(String scheduleCode, String secretCode, int time)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
	}
	
	public String printTime()
	{
		return ""+time;
	}
	
	public String toString()
	{
		return "Opening all timeslots on " + this.time;
	}

}
 