package com.amazonaws.lambda.openOrCloseTimeSlot;

public class OpenOrCloseTimeSlotRequest {
	
	String scheduleCode = "";
	String secretCode = "";
	int time;
	String day;
	
	public OpenOrCloseTimeSlotRequest(String scheduleCode, String secretCode, int time, String day) {
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.time = time;
		this.day = day;
	}
	
	public String toString() {
		return "Timeslot will be toggled on " + this.day + " at " + this.time;
	}
}
