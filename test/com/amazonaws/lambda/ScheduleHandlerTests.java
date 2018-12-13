package com.amazonaws.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.cancelMeeting.CancelMeetingHandler;
import com.amazonaws.lambda.cancelMeeting.CancelMeetingRequest;
import com.amazonaws.lambda.cancelMeetingParticipant.CancelMeetingParticipantHandler;
import com.amazonaws.lambda.cancelMeetingParticipant.CancelMeetingParticipantRequest;
import com.amazonaws.lambda.closeAllSlotsDay.CloseAllSlotsDayHandler;
import com.amazonaws.lambda.closeAllSlotsDay.CloseAllSlotsDayRequest;
import com.amazonaws.lambda.closeAllTimeSlotsTime.CloseAllTimeSlotsTimeHandler;
import com.amazonaws.lambda.closeAllTimeSlotsTime.CloseAllTimeSlotsTimeRequest;
import com.amazonaws.lambda.createMeeting.CreateMeetingHandler;
import com.amazonaws.lambda.createMeeting.CreateMeetingRequest;
import com.amazonaws.lambda.createSchedule.CreateScheduleHandler;
import com.amazonaws.lambda.createSchedule.CreateScheduleRequest;
import com.amazonaws.lambda.deleteOldSchedules.DeleteOldSchedulesHandler;
import com.amazonaws.lambda.deleteOldSchedules.DeleteOldSchedulesRequest;
import com.amazonaws.lambda.deleteSchedule.DeleteScheduleHandler;
import com.amazonaws.lambda.deleteSchedule.DeleteScheduleRequest;
import com.amazonaws.lambda.extendEndDate.ExtendEndDateHandler;
import com.amazonaws.lambda.extendEndDate.ExtendEndDateRequest;
import com.amazonaws.lambda.extendStartDate.ExtendStartDateHandler;
import com.amazonaws.lambda.extendStartDate.ExtendStartDateRequest;
import com.amazonaws.lambda.getSchedule.GetScheduleRequest;
import com.amazonaws.lambda.getSchedule.OrganizerGetScheduleHandler;
import com.amazonaws.lambda.openAllSlotsDay.OpenAllSlotsDayHandler;
import com.amazonaws.lambda.openAllSlotsDay.OpenAllSlotsDayRequest;
import com.amazonaws.lambda.openAllTimeSlotsTime.OpenAllTimeSlotsTimeHandler;
import com.amazonaws.lambda.openAllTimeSlotsTime.OpenAllTimeSlotsTimeRequest;
import com.amazonaws.lambda.openOrCloseTimeSlot.OpenOrCloseTimeSlotHandler;
import com.amazonaws.lambda.openOrCloseTimeSlot.OpenOrCloseTimeSlotRequest;
import com.amazonaws.lambda.reportActivity.ReportActivityHandler;
import com.amazonaws.lambda.reportActivity.ReportActivityRequest;
import com.amazonaws.lambda.retrieveOldSchedules.RetrieveOldSchedulesHandler;
import com.amazonaws.lambda.retrieveOldSchedules.RetrieveOldSchedulesRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import db.DatabaseUtils;
import junit.framework.TestCase;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ScheduleHandlerTests extends TestCase{

	public String tempShareCode = "";
	public String tempOrganizerCode = "";
	public String tempMeetingCode = "";
	java.sql.Connection conn;

	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    /**
     * Basically verifies that a schedule is created
     * @throws Exception 
     */
    @Test
    public void testCreateDeleteScheduleHandler() throws Exception {// throws IOException {
    	
    	//*******************************************
    	//Create Schedule
    	Context c = createContext("test");
    	conn = DatabaseUtils.connect();
    	
    	CreateScheduleRequest req = new CreateScheduleRequest("bestScheduleEverrrrr", 60, "2018-10-26", "2018-10-27", 0300, 0400);
    	CreateScheduleHandler handler = new CreateScheduleHandler();
    	JSONObject reqJson = new JSONObject();
    	reqJson.put("body", new Gson().toJson(req));
    	
    	
        InputStream input = new ByteArrayInputStream(reqJson.toJSONString().getBytes());;
        OutputStream output = new ByteArrayOutputStream();
                
        handler.handleRequest(input, output, c);
        tempShareCode = handler.getShareCode();
        tempOrganizerCode = handler.getOrganizerCode();
        System.out.println("SHARE CODE: " + tempShareCode + ", ORGANIZER CODE: "+tempOrganizerCode);

        String sampleOutputString = output.toString();
        System.out.println("output");
        System.out.println(sampleOutputString);
        System.out.println("/output");
        //*******************************************
        //Create Meeting
    	CreateMeetingRequest req3 = new CreateMeetingRequest(tempShareCode, "Samuel Vimes", 0300, "2018-10-26");
    	CreateMeetingHandler handler3 = new CreateMeetingHandler();
    	JSONObject req3Json = new JSONObject();
    	req3Json.put("body", new Gson().toJson(req3));
    	
    	
        InputStream input3 = new ByteArrayInputStream(req3Json.toJSONString().getBytes());;
        OutputStream output3 = new ByteArrayOutputStream();
                
        handler3.handleRequest(input3, output3, c);

        String sampleOutputString3 = output3.toString();
        System.out.println("output3");
        System.out.println(sampleOutputString3);
        System.out.println("/output3");
        
        //*******************************************
        //Cancel Meeting
    	CancelMeetingRequest req4 = new CancelMeetingRequest(tempShareCode, tempOrganizerCode, 0300, "2018-10-26");
    	CancelMeetingHandler handler4 = new CancelMeetingHandler();
    	JSONObject req4Json = new JSONObject();
    	req4Json.put("body", new Gson().toJson(req4));
    	
    	
        InputStream input4 = new ByteArrayInputStream(req4Json.toJSONString().getBytes());;
        OutputStream output4 = new ByteArrayOutputStream();
                
        handler4.handleRequest(input4, output4, c);

        String sampleOutputString4 = output4.toString();
        System.out.println("output4");
        System.out.println(sampleOutputString4);
        System.out.println("/output4");
        
        //*******************************************
        //Cancel Meeting Participant
    	CreateMeetingRequest req31 = new CreateMeetingRequest(tempShareCode, "Samuel Vimes", 0300, "2018-10-26");
    	CreateMeetingHandler handler31 = new CreateMeetingHandler();
    	JSONObject req31Json = new JSONObject();
    	req31Json.put("body", new Gson().toJson(req31));
    	
    	
        InputStream input31 = new ByteArrayInputStream(req31Json.toJSONString().getBytes());;
        OutputStream output31 = new ByteArrayOutputStream();
                
        handler31.handleRequest(input31, output31, c);
        
        tempMeetingCode = handler31.getMeetingCode();
        CancelMeetingParticipantRequest req5 = new CancelMeetingParticipantRequest(tempShareCode, tempMeetingCode);
        CancelMeetingParticipantHandler handler5 = new CancelMeetingParticipantHandler();
    	JSONObject req5Json = new JSONObject();
    	req5Json.put("body", new Gson().toJson(req5));
    	
    	
        InputStream input5 = new ByteArrayInputStream(req5Json.toJSONString().getBytes());;
        OutputStream output5 = new ByteArrayOutputStream();
                
        handler5.handleRequest(input5, output5, c);

        String sampleOutputString5 = output5.toString();
        System.out.println("output5");
        System.out.println(sampleOutputString5);
        System.out.println("/output5");
        
        //*******************************************
        //Close Slots Day
        CloseAllSlotsDayRequest req6 = new CloseAllSlotsDayRequest(tempShareCode, tempOrganizerCode, "2018-10-26");
        CloseAllSlotsDayHandler handler6 = new CloseAllSlotsDayHandler();
    	JSONObject req6Json = new JSONObject();
    	req6Json.put("body", new Gson().toJson(req6));
    	
    	
        InputStream input6 = new ByteArrayInputStream(req6Json.toJSONString().getBytes());;
        OutputStream output6 = new ByteArrayOutputStream();
                
        handler6.handleRequest(input6, output6, c);

        String sampleOutputString6 = output6.toString();
        System.out.println("output6");
        System.out.println(sampleOutputString6);
        System.out.println("/output6");
        
        //*******************************************
        //Open Slots Day
        OpenAllSlotsDayRequest req7 = new OpenAllSlotsDayRequest(tempShareCode, tempOrganizerCode, "2018-10-26");
        OpenAllSlotsDayHandler handler7 = new OpenAllSlotsDayHandler();
    	JSONObject req7Json = new JSONObject();
    	req7Json.put("body", new Gson().toJson(req7));
    	
    	
        InputStream input7 = new ByteArrayInputStream(req7Json.toJSONString().getBytes());;
        OutputStream output7 = new ByteArrayOutputStream();
                
        handler7.handleRequest(input7, output7, c);

        String sampleOutputString7 = output7.toString();
        System.out.println("output7");
        System.out.println(sampleOutputString7);
        System.out.println("/output7");
        
        //*******************************************
        //Close Slots Time
        CloseAllTimeSlotsTimeRequest req8 = new CloseAllTimeSlotsTimeRequest(tempShareCode, tempOrganizerCode, 0300);
        CloseAllTimeSlotsTimeHandler handler8 = new CloseAllTimeSlotsTimeHandler();
    	JSONObject req8Json = new JSONObject();
    	req8Json.put("body", new Gson().toJson(req8));
    	
    	
        InputStream input8 = new ByteArrayInputStream(req8Json.toJSONString().getBytes());;
        OutputStream output8 = new ByteArrayOutputStream();
                
        handler8.handleRequest(input8, output8, c);

        String sampleOutputString8 = output8.toString();
        System.out.println("output8");
        System.out.println(sampleOutputString8);
        System.out.println("/output8");
        
        //*******************************************
        //Open Slots Time
        OpenAllTimeSlotsTimeRequest req9 = new OpenAllTimeSlotsTimeRequest(tempShareCode, tempOrganizerCode, 0300);
        OpenAllTimeSlotsTimeHandler handler9 = new OpenAllTimeSlotsTimeHandler();
    	JSONObject req9Json = new JSONObject();
    	req9Json.put("body", new Gson().toJson(req9));
    	
    	
        InputStream input9 = new ByteArrayInputStream(req9Json.toJSONString().getBytes());;
        OutputStream output9 = new ByteArrayOutputStream();
                
        handler9.handleRequest(input9, output9, c);

        String sampleOutputString9 = output9.toString();
        System.out.println("output9");
        System.out.println(sampleOutputString9);
        System.out.println("/output9");
        
        //*******************************************
        //Open/Close Timeslot
        OpenOrCloseTimeSlotRequest req10 = new OpenOrCloseTimeSlotRequest(tempShareCode, tempOrganizerCode, 0300, "2018-10-26");
    	//OpenOrCloseTimeSlotRequest req10 = new OpenOrCloseTimeSlotRequest("UAMH9LE21Y", "KTL849UY1Z", 1000, "2018-04-20");
        OpenOrCloseTimeSlotHandler handler10 = new OpenOrCloseTimeSlotHandler();
    	JSONObject req10Json = new JSONObject();
    	req10Json.put("body", new Gson().toJson(req10));
    	
    	
        InputStream input10 = new ByteArrayInputStream(req10Json.toJSONString().getBytes());;
        OutputStream output10 = new ByteArrayOutputStream();
                
        handler10.handleRequest(input10, output10, c);

        String sampleOutputString10 = output10.toString();
        System.out.println("output10");
        System.out.println(sampleOutputString10);
        System.out.println("/output10");
        
        //*******************************************
        //Get Schedule
        GetScheduleRequest req11 = new GetScheduleRequest(tempShareCode, tempOrganizerCode);
        OrganizerGetScheduleHandler handler11 = new OrganizerGetScheduleHandler();
    	JSONObject req11Json = new JSONObject();
    	req11Json.put("body", new Gson().toJson(req11));
    	
    	
        InputStream input11 = new ByteArrayInputStream(req11Json.toJSONString().getBytes());;
        OutputStream output11 = new ByteArrayOutputStream();
                
        handler11.handleRequest(input11, output11, c);

        String sampleOutputString11 = output11.toString();
        System.out.println("output11");
        System.out.println(sampleOutputString11);
        System.out.println("/output11");
        
        //*******************************************
        //Delete Schedule
//    	DeleteScheduleRequest req2 = new DeleteScheduleRequest(tempShareCode, tempOrganizerCode);
//    	DeleteScheduleHandler handler2 = new DeleteScheduleHandler();
//    	JSONObject req2Json = new JSONObject();
//    	req2Json.put("body", new Gson().toJson(req2));
//    	
//    	
//        InputStream input2 = new ByteArrayInputStream(req2Json.toJSONString().getBytes());;
//        OutputStream output2 = new ByteArrayOutputStream();
//                
//        handler2.handleRequest(input2, output2, c);
//
//        String sampleOutputString2 = output2.toString();
//        System.out.println("output2");
//        System.out.println(sampleOutputString2);
//        System.out.println("/output2");
      //*******************************************
        //Test reportActivity
//    	CreateScheduleRequest req12 = new CreateScheduleRequest("Yin4", 60, "2018-12-1", "2018-12-12", 0300, 0400);
//    	CreateScheduleHandler handler12 = new CreateScheduleHandler();
//    	JSONObject reqJson12 = new JSONObject();
//    	reqJson12.put("body", new Gson().toJson(req12));
//    	
//    	
//        InputStream input12 = new ByteArrayInputStream(reqJson12.toJSONString().getBytes());;
//        OutputStream output12 = new ByteArrayOutputStream();
//                
//        handler12.handleRequest(input12, output12, c);
//        
//        
//    	CreateScheduleRequest req13 = new CreateScheduleRequest("Yang4", 60, "2018-12-1", "2018-12-9", 0300, 0400);
//    	CreateScheduleHandler handler13 = new CreateScheduleHandler();
//    	JSONObject reqJson13 = new JSONObject();
//    	reqJson13.put("body", new Gson().toJson(req13));
//    	
//    	
//        InputStream input13 = new ByteArrayInputStream(reqJson13.toJSONString().getBytes());;
//        OutputStream output13 = new ByteArrayOutputStream();
//                
//        handler13.handleRequest(input13, output13, c);
        
    	ReportActivityRequest req14 = new ReportActivityRequest(1);
    	ReportActivityHandler handler14 = new ReportActivityHandler();
    	JSONObject reqJson14 = new JSONObject();
    	reqJson14.put("body", new Gson().toJson(req14));
    	
    	
        InputStream input14 = new ByteArrayInputStream(reqJson14.toJSONString().getBytes());;
        OutputStream output14 = new ByteArrayOutputStream();
                
        handler14.handleRequest(input14, output14, c);
        
        String sampleOutputString14 = output14.toString();
        System.out.println("output14");
        System.out.println(sampleOutputString14);
        System.out.println("/output14");
        
        //*******************************************
        //Test retrieveOldSchedules
    	RetrieveOldSchedulesRequest req15 = new RetrieveOldSchedulesRequest(1);
    	RetrieveOldSchedulesHandler handler15 = new RetrieveOldSchedulesHandler();
    	JSONObject reqJson15 = new JSONObject();
    	reqJson15.put("body", new Gson().toJson(req15));
    	
    	
        InputStream input15 = new ByteArrayInputStream(reqJson15.toJSONString().getBytes());;
        OutputStream output15 = new ByteArrayOutputStream();
                
        handler15.handleRequest(input15, output15, c);
        
        String sampleOutputString15 = output15.toString();
        System.out.println("output15");
        System.out.println(sampleOutputString15);
        System.out.println("/output15");
        
        //*******************************************
        //Test deleteOldSchedules
//    	DeleteOldSchedulesRequest req16 = new DeleteOldSchedulesRequest(3);
//    	DeleteOldSchedulesHandler handler16 = new DeleteOldSchedulesHandler();
//    	JSONObject reqJson16 = new JSONObject();
//    	reqJson16.put("body", new Gson().toJson(req16));
//    	
//    	
//        InputStream input16 = new ByteArrayInputStream(reqJson16.toJSONString().getBytes());;
//        OutputStream output16 = new ByteArrayOutputStream();
//                
//        handler16.handleRequest(input16, output16, c);
//        
//        String sampleOutputString16 = output16.toString();
//        System.out.println("output16");
//        System.out.println(sampleOutputString16);
//        System.out.println("/output16");
        
        //*******************************************
        //Test extend End Date
    	ExtendEndDateRequest req16 = new ExtendEndDateRequest(tempShareCode, tempOrganizerCode, "2018-12-25");
    	ExtendEndDateHandler handler16 = new ExtendEndDateHandler();
    	JSONObject reqJson16 = new JSONObject();
    	reqJson16.put("body", new Gson().toJson(req16));
    	
    	
        InputStream input16 = new ByteArrayInputStream(reqJson16.toJSONString().getBytes());;
        OutputStream output16 = new ByteArrayOutputStream();
                
        handler16.handleRequest(input16, output16, c);
        
        String sampleOutputString16 = output16.toString();
        System.out.println("output16");
        System.out.println(sampleOutputString16);
        System.out.println("/output16");
        
        //*******************************************
        //Test extend Start Date
    	ExtendStartDateRequest req17 = new ExtendStartDateRequest(tempShareCode, tempOrganizerCode, "2018-10-1");
    	ExtendStartDateHandler handler17 = new ExtendStartDateHandler();
    	JSONObject reqJson17 = new JSONObject();
    	reqJson17.put("body", new Gson().toJson(req17));
    	
    	
        InputStream input17 = new ByteArrayInputStream(reqJson17.toJSONString().getBytes());;
        OutputStream output17 = new ByteArrayOutputStream();
                
        handler17.handleRequest(input17, output17, c);
        
        String sampleOutputString17 = output17.toString();
        System.out.println("output17");
        System.out.println(sampleOutputString17);
        System.out.println("/output17");

    }
    
	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8));
		return new GregorianCalendar(year, month-1, day);
	}
}