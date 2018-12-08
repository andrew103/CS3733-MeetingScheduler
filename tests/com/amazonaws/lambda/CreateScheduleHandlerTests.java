package com.amazonaws.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.createSchedule.CreateScheduleHandler;
import com.amazonaws.lambda.createSchedule.CreateScheduleRequest;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateScheduleHandlerTests {


	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    /**
     * Basically verifies that a schedule is created
     * @throws IOException
     */
    @Test
    public void testLambdaFunctionHandler() throws IOException {
    	Context c = createContext("test");
    	
    	CreateScheduleRequest req = new CreateScheduleRequest("toBeDeleted", 60, "2018-10-26", "2018-10-27", 300, 400);
    	CreateScheduleHandler handler = new CreateScheduleHandler();
    	JSONObject reqJson = new JSONObject();
    	reqJson.put("body", new Gson().toJson(req));
    	
    	
        InputStream input = new ByteArrayInputStream(reqJson.toJSONString().getBytes());;
        OutputStream output = new ByteArrayOutputStream();
                
        handler.handleRequest(input, output, c);

        String sampleOutputString = output.toString();
        System.out.println("output");
        System.out.println(sampleOutputString);
        System.out.println("/output");

    }
}