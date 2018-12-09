package com.amazonaws.lambda.closeAllTimeSlotsTime;

public class CloseAllTimeSlotsTimeRequest {
	
	String scheduleCode;
	String secretCode;
	int time;
	
	public CloseAllTimeSlotsTimeRequest(String scheduleCode, String secretCode, int time)
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
		return "Closing all timeslots at " + this.time;
	}

}
 