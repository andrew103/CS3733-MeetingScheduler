package com.amazonaws.lambda.getSchedule;

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
public class OrganizerGetScheduleHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;

	// handle to our s3 storage
	private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
			.withRegion("us-east-2").build();

	boolean useRDS = true;


	
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

		GetScheduleResponse response = null;
		
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
				response = new GetScheduleResponse("Requested options", 200);  // OPTIONS needs a 200 response
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
			response = new GetScheduleResponse("Unable to parse input",400);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) 
		{
			GetScheduleRequest req = new Gson().fromJson(body, GetScheduleRequest.class);
			GetScheduleResponse resp;
			logger.log("***"+req.toString()+"***");

			// compute proper response
			try{
				SchedulerDAO dao = new SchedulerDAO();	
				try {
					if (req.secretCode.length() > 0 && req.shareCode.length() > 0) {
						resp = new GetScheduleResponse("Cannot pass in both a secret code and a share code", 400);
					}
					else if (req.secretCode.length() == 0 && req.shareCode.length() == 0) {
						resp = new GetScheduleResponse("Must pass in a code", 400);
					}
					else {
						Schedule s;
						if (req.secretCode.length() > 0) {
							s = dao.organizerGetSchedule(req.secretCode);
						}
						else {
							s =  dao.getSchedule(req.shareCode);							
						}
						logger.log(" ***we found a schedule*** ");
						resp = new GetScheduleResponse(s);
						logger.log("\nname:" + s.getScheduleName());						
					}
				}
				catch(Exception e)
				{
					logger.log("Could not find a schedule");
					resp = new GetScheduleResponse("The schedule was not found", 404);
				}
			}
			catch(Exception e){
				logger.log("DAO could not connect to database" + e.getMessage());
				resp = new GetScheduleResponse(req.shareCode, 500);					
			}	
			
			responseJson.put("body", new Gson().toJson(resp));
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}