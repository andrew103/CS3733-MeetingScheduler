package com.amazonaws.lambda.closeAllSlotsDay;

public class CloseAllSlotsDayRequest {
	
	String scheduleCode;
	String secretCode;
	String date;
	
	public CloseAllSlotsDayRequest(String scheduleCode, String secretCode, String date)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.date = date;
	}
	
	public String toString()
	{
		return "Closing all timeslots on " + this.date;
	}

}
