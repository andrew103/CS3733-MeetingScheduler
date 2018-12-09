package com.amazonaws.lambda.openOrCloseTimeSlot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.GregorianCalendar;
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
public class OpenOrCloseTimeSlotHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;

	// handle to our s3 storage
	private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
			.withRegion("us-east-2").build();

	boolean useRDS = true;
	
//	// not yet connected to RDS, so comment this out...
//	public double loadConstant(String arg) {
//		if (useRDS) {
//			double val = 0;
//			try {
//				val = loadValueFromRDS(arg);
//				return val;
//			} catch (Exception e) {
//				return 0;
//			}
//		}
//		
//		return loadValueFromBucket(arg);
//	}
//
//	/** Load from RDS, if it exists
//	 * 
//	 * @throws Exception 
//	 */
//	double loadValueFromRDS(String arg) throws Exception {
//		if (logger != null) { logger.log("in loadValue"); }
//		ConstantsDAO dao = new ConstantsDAO();
//		Constant constant = dao.getConstant(arg);
//		return constant.value;
//	}
//	
//	/** Load up S3 Bucket with given key and interpret contents as double. */
//	double loadValueFromBucket(String arg) {
//		if (logger != null) { logger.log("load from bucket:" + arg); }
//		try {
//			S3Object pi = s3.getObject("cs3733/constants", arg);
//			if (pi == null) {
//				return 0;
//			} else {
//				S3ObjectInputStream pis = pi.getObjectContent();
//				Scanner sc = new Scanner(pis);
//				String val = sc.nextLine();
//				sc.close();
//				try { pis.close(); } catch (IOException e) { }
//				try {
//					return Double.valueOf(val);
//				} catch (NumberFormatException nfe) {
//					return 0.0;
//				}
//			}
//		} catch (SdkClientException sce) {
//			return 0;
//		}
//	}

	boolean openOrCloseTimeSlot(String scheduleCode, int time, String day) throws Exception {
		SchedulerDAO dao = new SchedulerDAO();
		GregorianCalendar date = parseDate(day);
		return dao.openOrCloseTimeSlot(scheduleCode, time, date);	
	}
	
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8));
		return new GregorianCalendar(year, month-1, day);
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

		OpenOrCloseTimeSlotResponse response = null;
		
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
				response = new OpenOrCloseTimeSlotResponse("Option", 200);  // OPTIONS needs a 200 response
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
			response = new OpenOrCloseTimeSlotResponse("Failure", 400);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) 
		{
			OpenOrCloseTimeSlotRequest req = new Gson().fromJson(body, OpenOrCloseTimeSlotRequest.class);
			logger.log(req.toString());
			
			logger.log("***"+req.toString()+"***");
			// compute proper response
			OpenOrCloseTimeSlotResponse resp;
			logger.log(" ***Request made succ*** ");
			try {
				logger.log(" **** In the Try loop *** ");
				logger.log(req.scheduleCode);
				logger.log(req.secretCode);
				logger.log(req.day);
				boolean del = openOrCloseTimeSlot(req.scheduleCode, req.time, req.day);
				if (del) {
					logger.log(" *** It definitely worked right? probably. *** ");
					resp = new OpenOrCloseTimeSlotResponse(req.scheduleCode, req.secretCode, req.time, req.day, 200);					
				}
				else {
					logger.log(" *** fuck it failed *** ");
					resp = new OpenOrCloseTimeSlotResponse("Timeslot could not be toggled", 400);					
					}
				logger.log("WTF");
				} 
			catch (Exception e) 
			{
				logger.log(" ***EXCEPTION*** " + e);
				resp = new OpenOrCloseTimeSlotResponse("Timeslot could not be toggled", 400);					
			}
	        
			logger.log(" ***something did happen*** ");
			logger.log(resp.toString());
			responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}