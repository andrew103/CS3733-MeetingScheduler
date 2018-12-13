package com.amazonaws.lambda.extendEndDate;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ExtendEndDateResponse {
	
	String shareCode;
	String organizerCode;
	GregorianCalendar newEndDate;
	int httpCode;
	
	public ExtendEndDateResponse(String shareCode, String organizerCode, GregorianCalendar newEndDate, int hc)
	{
		this.shareCode = shareCode;
		this.organizerCode = organizerCode;
		this.newEndDate = newEndDate;
		this.httpCode = hc;
	}
	
	public ExtendEndDateResponse(String message, int code)
	{
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString()
	{
		return "Extended end date";
	}

}
