package com.amazonaws.lambda.extendStartDate;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ExtendStartDateRequest {
	
	String shareCode;
	String organizerCode;
	String newStartDate;
	
	public ExtendStartDateRequest(String shareCode, String organizerCode, String newStartDate)
	{
		this.shareCode = shareCode;
		this.organizerCode = organizerCode;
		this.newStartDate = newStartDate;
	}
	
	public String toString()
	{
		return "Extending startDate date to " + newStartDate;
	}

}
