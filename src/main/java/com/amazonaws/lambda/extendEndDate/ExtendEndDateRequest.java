package com.amazonaws.lambda.extendEndDate;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ExtendEndDateRequest {
	
	String shareCode;
	String organizerCode;
	GregorianCalendar newEndDate;
	
	public ExtendEndDateRequest(String shareCode, String organizerCode, GregorianCalendar newEndDate)
	{
		this.shareCode = shareCode;
		this.organizerCode = organizerCode;
		this.newEndDate = newEndDate;
	}
	
	public String toString()
	{
		Date endDate = new Date(newEndDate.getTimeInMillis());
		return "Extending end date to " + endDate.toString();
	}

}
