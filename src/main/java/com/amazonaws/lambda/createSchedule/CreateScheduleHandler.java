package com.amazonaws.lambda.createSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;

import entity.Schedule;
import db.SchedulerDAO;


/**
 * Found gson JAR file from
 * https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar
 */
public class CreateScheduleHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;
	public String tempShareCode = "";
	public String tempOrganizerCode = "";

	// handle to our s3 storage
	private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
			.withRegion("us-east-2").build();


	boolean createSchedule(Schedule schedule) throws Exception {
		SchedulerDAO dao = new SchedulerDAO();		
		return dao.createSchedule(schedule);
	}	
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestStreamHandler");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		CreateScheduleResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		String body;
		boolean processed = false;
		
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());
			
			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				response = new CreateScheduleResponse("Option", "schedule not created", 200);  // OPTIONS needs a 200 response
		        responseJson.put("body", new Gson().toJson(response));
		        processed = true;
		        body = null;
			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new CreateScheduleResponse("Failure", "schedule not created", 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CreateScheduleRequest req = new Gson().fromJson(body, CreateScheduleRequest.class);
			logger.log(req.toString());

			Schedule createdSchedule = new Schedule(req.name, req.getStartDate(), req.getEndDate(), req.meetingDuration, req.startTime, req.endTime);
			String secretCode = createdSchedule.getOrganizerCode();
			String shareCode = createdSchedule.getShareCode();
			logger.log("finished initing\n");

			// compute proper response
			CreateScheduleResponse resp;
			try {
				if (createSchedule(createdSchedule)) {
					resp = new CreateScheduleResponse(secretCode, shareCode, 200);	
					tempShareCode = resp.shareCode;
					tempOrganizerCode = resp.secretCode;
					logger.log("created a proper response\n");
				}
				else {
					logger.log("could not create a schedule\n");
					resp = new CreateScheduleResponse("The new schedule couldn't be created", 422);					
				}
			} catch (Exception e) {
				logger.log("got an exception "+ e.getMessage());
				resp = new CreateScheduleResponse("Something went wrong in the database", 500);					
			}
			logger.log("to creating the response");

			responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
	
	public String getShareCode()
	{
		return tempShareCode;
	}
	
	public String getOrganizerCode()
	{
		return tempOrganizerCode;
	}
}