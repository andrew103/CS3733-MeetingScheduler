package com.amazonaws.lambda.openAllSlotsDay;

public class OpenAllSlotsDayResponse {
	
	String scheduleCode;
	String secretCode;
	String date;
	int httpCode; 
	
	public OpenAllSlotsDayResponse(String scheduleCode, String secretCode, String date, int hc)
	{
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.date = date;
		this.httpCode = hc;
	}

	public OpenAllSlotsDayResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString()
	{
		return "All slots have been opened on " + this.date;
	}

}
