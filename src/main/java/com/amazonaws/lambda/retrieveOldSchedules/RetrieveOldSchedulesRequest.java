package com.amazonaws.lambda.retrieveOldSchedules;

public class RetrieveOldSchedulesRequest {

	int days;
	
	public RetrieveOldSchedulesRequest(int days)
	{
		this.days = days;
	}
	
	public String toString()
	{
		return "Getting all schedules that were made within the last " + days+ " days";
	}

}

