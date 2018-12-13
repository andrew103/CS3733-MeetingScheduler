package com.amazonaws.lambda.deleteOldSchedules;

public class DeleteOldSchedulesRequest {

	int days;
	
	public DeleteOldSchedulesRequest(int days)
	{
		this.days = days;
	}
	
	public String toString()
	{
		return "Deleting Schedules that were made within the last " + days+ " days";
	}

}

