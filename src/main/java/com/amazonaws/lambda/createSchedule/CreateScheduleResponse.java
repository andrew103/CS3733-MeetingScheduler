package com.amazonaws.lambda.createSchedule;

class CreateScheduleResponse {
	String secretCode;
	String shareCode;
	int httpCode;
	
	public CreateScheduleResponse(String secretCode, String shareCode, int hc) {
		this.secretCode = secretCode;
		this.shareCode = shareCode;
		this.httpCode = hc;
	}
	
	// return success if not specified 
	public CreateScheduleResponse(String sc) {
		this.secretCode = sc;
		this.httpCode = 200;
	}
	
	public CreateScheduleResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString() {
		return "Your meeting is created, secret code: " + secretCode + ", share code: " + shareCode;
	}
}
