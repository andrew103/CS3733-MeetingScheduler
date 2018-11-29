package com.amazonaws.lambda.demo;

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
	
	public String toString() {
		return "Your meeting is created, secret code: " + secretCode + ", share code: " + shareCode;
	}
}
