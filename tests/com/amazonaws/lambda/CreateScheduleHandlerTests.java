package com.amazonaws.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.createSchedule.CreateScheduleHandler;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateScheduleHandlerTests {

    private static final String SAMPLE_SCHEDULE_PARAMS = "";

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
    	
    	CreateScheduleHandler handler = new CreateScheduleHandler();

        InputStream input = new ByteArrayInputStream(SAMPLE_SCHEDULE_PARAMS.getBytes());;
        OutputStream output = new ByteArrayOutputStream();
                
        handler.handleRequest(input, output, c);

        String sampleOutputString = output.toString();
        System.out.println("output");
        System.out.println(sampleOutputString);
        System.out.println("/output");

    }
}