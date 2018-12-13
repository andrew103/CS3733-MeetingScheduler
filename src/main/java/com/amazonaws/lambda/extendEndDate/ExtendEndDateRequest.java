package com.amazonaws.lambda.extendEndDate;

import java.sql.Date;
import java.util.GregorianCalendar;

public class ExtendEndDateRequest {
	
	String shareCode;
	String organizerCode;
	String newEndDate;
	
	public ExtendEndDateRequest(String shareCode, String organizerCode, String newEndDate)
	{
		this.shareCode = shareCode;
		this.organizerCode = organizerCode;
		this.newEndDate = newEndDate;
	}
	
	public String toString()
	{
		return "Extending end date to "+ newEndDate;
	}

}
