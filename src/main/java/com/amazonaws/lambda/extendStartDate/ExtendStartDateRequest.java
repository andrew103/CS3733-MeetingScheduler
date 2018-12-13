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
	
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8));
		return new GregorianCalendar(year, month-1, day);
	}

}
