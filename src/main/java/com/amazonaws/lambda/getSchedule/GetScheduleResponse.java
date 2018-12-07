package com.amazonaws.lambda.getSchedule;

public class GetScheduleResponse {
	
	String scheduleCode;
	String secretCode;
	int httpCode; 
	
	public GetScheduleResponse(String scheduleCode, String secretCode, int hc) {
		this.scheduleCode = scheduleCode;
		this.secretCode = secretCode;
		this.httpCode = hc;
	}

	public GetScheduleResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = 200;
	}
	
	public String toString() {
		return "I'm trying to get" + scheduleCode + "here is the secret code" + secretCode;
	}

}