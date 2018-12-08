package com.amazonaws.lambda.closeAllSlotsDay;

public class CloseAllSlotsDayResponse {
	
	String scheduleCode;
	String secretCode;
	String date;
	int httpCode; 
	
	public CloseAllSlotsDayResponse(String scheduleCode, String secretCode, String date, int hc)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.date = date;
		this.httpCode = hc;
	}

	public CloseAllSlotsDayResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = 200;
	}
	
	public String toString()
	{
		return "All slots have been closed on " + this.date;
	}

}
