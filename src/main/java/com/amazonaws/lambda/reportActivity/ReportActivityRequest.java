package com.amazonaws.lambda.reportActivity;

public class ReportActivityRequest {

	int time;
	
	public ReportActivityRequest(int time)
	{
		this.time = time;
	}
	
	public String toString()
	{
		return "Getting Schedules that were made within the last " + time+ " hours";
	}

}

