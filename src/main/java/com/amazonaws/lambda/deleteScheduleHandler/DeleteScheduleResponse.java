package com.amazonaws.lambda.deleteScheduleHandler;

public class DeleteScheduleResponse {
	String secretCode;
	String shareCode;
	int httpCode;
	
	public DeleteScheduleResponse(String shareCode, String secretCode, int hc) {
		this.secretCode = secretCode;
		this.shareCode = shareCode;
		this.httpCode = hc;
	}
	
	// return success if not specified 
	public DeleteScheduleResponse(String sc) {
		this.secretCode = sc;
		this.httpCode = 200;
	}
	
	public DeleteScheduleResponse(String message, int code) {
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString() {
		return "Your schedule has been deleted, share code: " + shareCode;
	}
}