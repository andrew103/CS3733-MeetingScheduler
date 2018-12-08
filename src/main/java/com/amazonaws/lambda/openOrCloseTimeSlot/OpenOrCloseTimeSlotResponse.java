package com.amazonaws.lambda.openOrCloseTimeSlot;

public class OpenOrCloseTimeSlotResponse {
	String secretCode;
	String shareCode;
	int time;
	String day; 
	int httpCode;
	
	public OpenOrCloseTimeSlotResponse(String secretCode, String shareCode, int time, String day, int hc) {
		this.secretCode = secretCode;
		this.shareCode = shareCode;
		this.httpCode = hc;
	}
	
	// return success if not specified 
	public OpenOrCloseTimeSlotResponse(String secretCode, int time, String day) {
		this.secretCode = secretCode;
		this.time = time;
		this.day = day;
		this.httpCode = 200;
	}
	
	public OpenOrCloseTimeSlotResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString() {
		return "Timeslot has been toggled on " + this.day + " at " + this.time;
	}

}
