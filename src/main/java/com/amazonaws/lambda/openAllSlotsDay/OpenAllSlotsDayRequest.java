package com.amazonaws.lambda.openAllSlotsDay;

public class OpenAllSlotsDayRequest {
	
	String scheduleCode;
	String secretCode;
	String date;
	
	public OpenAllSlotsDayRequest(String scheduleCode, String secretCode, String date)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.date = date;
	}
	
	public String toString()
	{
		return "Opening all timeslots on " + this.date;
	}

}
