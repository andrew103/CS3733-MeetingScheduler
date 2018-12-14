package com.amazonaws.lambda.extendStartDate;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ExtendStartDateResponse {
	
	String shareCode;
	String organizerCode;
	String newStartDate;
	int httpCode;
	
	public ExtendStartDateResponse(String shareCode, String organizerCode, String newStartDate, int hc)
	{
		this.shareCode = shareCode;
		this.organizerCode = organizerCode;
		this.newStartDate = newStartDate;
		this.httpCode = hc;
	}
	
	public ExtendStartDateResponse(String message, int code)
	{
		System.out.println(message);
		this.httpCode = code;
	}
	
	public String toString()
	{
		return "Extended start date to " + newStartDate;
	}

}
