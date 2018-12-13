package db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

import entity.Day;
import entity.Schedule;
import entity.Timeslot;
import entity.Scheduler;

public class SchedulerDAO {

	java.sql.Connection conn;

    public SchedulerDAO() {
    	try  {
    		conn = DatabaseUtils.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public boolean createSchedule(Schedule schedule) throws Exception {
    	try {
        	String query = "INSERT INTO Schedule (scheduleID, shareCode, organizerCode, scheduleName, meetingDuration, startDate, endDate, createdDate ,startTime, endTime) values(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        	PreparedStatement ps = conn.prepareStatement(query);
        	Date startDate = new Date(schedule.getStartDate().getTimeInMillis());
        	Date endDate = new Date(schedule.getEndDate().getTimeInMillis());
        	Date createdDate = new Date((new GregorianCalendar()).getTimeInMillis());
        	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String createdDateStr = fmt.format(createdDate);        	
        	
        	ps.setString(1, schedule.getShareCode());
        	ps.setString(2, schedule.getOrganizerCode());
        	ps.setString(3, schedule.getScheduleName());
        	ps.setInt(4, schedule.getDuration());
        	ps.setDate(5, startDate);
        	ps.setDate(6, endDate);
        	ps.setString(7, createdDateStr);
        	ps.setLong(8, convertTimeToDB(schedule.getStartTime()));
        	ps.setLong(9, convertTimeToDB(schedule.getEndTime()));
        	ps.execute();
        	        	
        	query = "SELECT scheduleID FROM Schedule WHERE shareCode = ?;";
        	ps = conn.prepareStatement(query);
        	ps.setString(1, schedule.getShareCode());
        	ResultSet resultSet = ps.executeQuery();
        	resultSet.next();
        	int schedID = resultSet.getInt("scheduleID");
        	resultSet.close();
        	
        	GregorianCalendar calendarCurrent = schedule.getStartDate();
        	GregorianCalendar calendarEnd = schedule.getEndDate();
        	while(!calendarCurrent.equals(calendarEnd)) {
        		if (calendarCurrent.DAY_OF_WEEK != calendarCurrent.SUNDAY || calendarCurrent.DAY_OF_WEEK != calendarCurrent.SATURDAY) {
            		Date currentDate = new Date(calendarCurrent.getTimeInMillis());
            		
            		query = "INSERT INTO Day (dayID, dayDate, dayStartTime, dayEndTime, scheduleID) values(NULL, ?, ?, ?, ?);";
            		ps = conn.prepareStatement(query);
            		ps.setDate(1, currentDate);
            		ps.setLong(2, convertTimeToDB(schedule.getStartTime()));
            		ps.setLong(3, convertTimeToDB(schedule.getEndTime()));
            		ps.setInt(4, schedID);
            		ps.execute();
        		}
        		
        		calendarCurrent.add(Calendar.DAY_OF_MONTH, 1);
        	}
        	
        	query = "SELECT dayID, dayDate FROM Day WHERE scheduleID = ?;";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, schedID);
        	resultSet = ps.executeQuery();
        	while (resultSet.next()) {
            	long currentTime = convertTimeToDB(schedule.getStartTime());
            	long endTime = convertTimeToDB(schedule.getEndTime());
        		while (currentTime < endTime) {
        			query = "INSERT INTO Timeslot (startTime, available, participantInfo, meetingCode, dayDate, scheduleID, dayID) values(?, true, NULL, NULL, ?, ?, ?);";
        			ps = conn.prepareStatement(query);
        			ps.setLong(1, currentTime);
        			ps.setDate(2, resultSet.getDate("dayDate"));
        			ps.setInt(3, schedID);
        			ps.setInt(4, resultSet.getInt("dayID"));
        			ps.execute();
        			
        			currentTime = currentTime + schedule.getDuration()*60*1000;
        		}
        	}
        	resultSet.close();
        	
        	ps.close();
        	return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to create new schedule: " + e.getMessage());
    	}
    }
    
    public Schedule getSchedule(String shareCode) throws Exception {
    	try {
        	String query = "SELECT * FROM Schedule WHERE shareCode = ?;";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, shareCode);
        	ResultSet resultSet1 = ps.executeQuery();

        	resultSet1.next();
        	GregorianCalendar startDate = new GregorianCalendar();
        	startDate.setTime(resultSet1.getDate("startDate"));
        	GregorianCalendar endDate = new GregorianCalendar();
        	endDate.setTime(resultSet1.getDate("endDate"));
        	GregorianCalendar createdDate = new GregorianCalendar();
        	createdDate.setTime(resultSet1.getTimestamp("createdDate"));
        	Schedule schedule = new Schedule(resultSet1.getString("scheduleName"),
        									 startDate,
        									 endDate,
        									 resultSet1.getInt("meetingDuration"),
        									 convertTimeToMilitary(resultSet1.getLong("startTime")),
        									 convertTimeToMilitary(resultSet1.getLong("endTime")),
        									 createdDate,
        									 resultSet1.getString("organizerCode"),
        									 resultSet1.getString("shareCode"));
        	
        	schedule.makeDateStr();
        	query = "SELECT * FROM Day WHERE scheduleID = ?;";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, resultSet1.getInt("scheduleID"));
        	ResultSet resultSet2 = ps.executeQuery();
        	while (resultSet2.next()) {
        		GregorianCalendar date = new GregorianCalendar();
        		date.setTime(resultSet2.getDate("dayDate"));
        		Day day = new Day(convertTimeToMilitary(resultSet2.getLong("dayStartTime")), convertTimeToMilitary(resultSet2.getLong("dayEndTime")), date);
        		day.makeDateStr();
        		//Day day = new Day(resultSet2.getInt("dayStartTime"), resultSet2.getInt("dayEndTime"), new GregorianCalendar());//Calendar Placeholder - Fix later
        		query = "SELECT * FROM Timeslot WHERE dayDate = ? AND dayID = ? AND scheduleID = ?;";
        		ps = conn.prepareStatement(query);
        		ps.setDate(1, resultSet2.getDate("dayDate"));
        		ps.setInt(2, resultSet2.getInt("dayID"));
        		ps.setInt(3, resultSet1.getInt("scheduleID"));
        		ResultSet resultSet3 = ps.executeQuery();
        		while (resultSet3.next()) {
        			resultSet3.getString("participantInfo");
        			if (resultSet3.wasNull()) {
            			Timeslot timeslot = new Timeslot(resultSet3.getBoolean("available"), convertTimeToMilitary(resultSet3.getLong("startTime")));
            			day.addTimeslot(timeslot);
        			}
        			else {
            			Timeslot timeslot = new Timeslot(resultSet3.getBoolean("available"), convertTimeToMilitary(resultSet3.getLong("startTime")), resultSet3.getString("participantInfo"), resultSet3.getString("meetingCode"));
            			day.addTimeslot(timeslot);
        			} 
        		}
        		
        		schedule.addDay(day);
        		resultSet3.close();
        	}
        	
        	resultSet2.close();
        	resultSet1.close();
        	ps.close();
        	return schedule;
    	} catch (Exception e) {
    		throw new Exception("Failed to get schedule: " + e.getMessage());
    	}
    }
    
    public Schedule organizerGetSchedule(String secretCode) throws Exception {
    	try {
    		String query = "SELECT shareCode FROM Schedule WHERE organizerCode=?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet = ps.executeQuery();
        	resultSet.next();
        	
        	return getSchedule(resultSet.getString("shareCode"));
        	
    	} catch (Exception e) {
    		throw new Exception("Failed to get the organizer's schedule: " + e.getMessage());
    	}
    }

	public boolean deleteSchedule(String scheduleCode, String secretCode) throws Exception {
		try {
			
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	
        	int scheduleID = resultSet1.getInt("scheduleID");
        	query = "DELETE FROM Timeslot WHERE scheduleID = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.executeUpdate();
        	
        	query = "DELETE FROM Day WHERE scheduleID = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.executeUpdate();

        	query = "DELETE FROM Schedule WHERE scheduleID = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.executeUpdate();
        	
        	resultSet1.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			throw new Exception("***Failed to delete schedule: " + e.getMessage()+ "***");
		}
	}

	public boolean openOrCloseTimeSlot(String secretCode, int time, GregorianCalendar day) throws Exception {
		
		try {
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	Date startDate = new Date(day.getTimeInMillis());
        	int scheduleID = resultSet1.getInt("scheduleID");
        	System.out.println("*** Schedule ID:" + scheduleID +"***");
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	System.out.println("***"+ startDate+"***");
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	query = "SELECT * FROM Timeslot WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setInt(2, dayID);
        	ps.setLong(3, convertTimeToDB(time));
        	ResultSet resultSet3 = ps.executeQuery();
        	resultSet3.next();
        	
        	System.out.println("*** Schedule ID: "+ scheduleID + ", dayID: "+dayID+", startTime: "+ convertTimeToDB(time));
        	
        	if (resultSet3.getInt("available")==1)
        	{
        		query = "UPDATE Timeslot SET available = ? WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
            	ps = conn.prepareStatement(query);
            	ps.setInt(1, 0);
            	ps.setInt(2, scheduleID);
            	ps.setInt(3, dayID);
            	ps.setLong(4, convertTimeToDB(time));
            	ps.executeUpdate();
            	System.out.println("***TOGGLED TIMESLOT***");
        	}
        	else
        	{
        		query = "UPDATE Timeslot SET available = ? WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
            	ps = conn.prepareStatement(query);
            	ps.setInt(1, 1);
            	ps.setInt(2, scheduleID);
            	ps.setInt(3, dayID);
            	ps.setLong(4, convertTimeToDB(time));
            	ps.executeUpdate();
            	
        	}
        	resultSet1.close();
        	resultSet2.close();
        	resultSet3.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to toggle timeslot: " + e.getMessage()+ "***");
		}
	}

	public boolean createMeeting(String scheduleCode, String participantInfo, String meetingCode, int time, GregorianCalendar day) throws Exception {
		try {
			//UPDATE tableName SET colname = ? WHERE schedId = ? AND dayID = ? AND dayDate = ? --Retrieves timeslots
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE shareCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, scheduleCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	int scheduleID = resultSet1.getInt("scheduleID");
        	Date startDate = new Date(day.getTimeInMillis());
        	long startTime = convertTimeToDB(time);
        	System.out.println(startTime);
        	
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	query = "SELECT * FROM Timeslot WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setInt(2, dayID);
        	ps.setLong(3, startTime);
        	ResultSet resultSet3 = ps.executeQuery();
        	resultSet3.next();
        	
        	if (resultSet3.getInt("available")==1)
        	{
            	//Create new timeslot+meeting
        		query = "UPDATE Timeslot SET available = ?, participantInfo = ?, meetingCode = ? WHERE scheduleID = ? AND dayID =? AND startTime = ?";
            	ps = conn.prepareStatement(query);
            	ps.setInt(1, 0);
            	ps.setString(2, participantInfo);
            	ps.setString(3, meetingCode);
            	ps.setInt(4, scheduleID);
            	ps.setInt(5, dayID);
            	ps.setLong(6, startTime);
            	ps.executeUpdate();
        	}
        	else
        	{
        		System.out.println("A meeting already exists at that time");
        	}
        	resultSet1.close();
        	resultSet2.close();
        	resultSet3.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to create meeting: " + e.getMessage()+ "***");
		}
	}
	
	public boolean cancelMeeting(String scheduleCode, String secretCode, int time, GregorianCalendar day) throws Exception {
		try {
			//UPDATE tableName SET colname = ? WHERE schedId = ? AND dayID = ? AND dayDate = ? --Retrieves timeslots
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	int scheduleID = resultSet1.getInt("scheduleID");
        	Date startDate = new Date(day.getTimeInMillis());
        	long startTime = convertTimeToDB(time);
        	System.out.println(startTime);
        	
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	query = "SELECT * FROM Timeslot WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setInt(2, dayID);
        	ps.setLong(3, startTime);
        	ResultSet resultSet3 = ps.executeQuery();
        	resultSet3.next();
        	
        	if (resultSet3.getInt("available")==0)
        	{
            	//Create new timeslot+meeting
        		query = "UPDATE Timeslot SET available = ?, participantInfo = ?, meetingCode = ? WHERE scheduleID = ? AND dayID =? AND startTime = ?";
            	ps = conn.prepareStatement(query);
            	ps.setInt(1, 1);
            	ps.setString(2, null);
            	ps.setString(3, null);
            	ps.setInt(4, scheduleID);
            	ps.setInt(5, dayID);
            	ps.setLong(6, startTime);
            	ps.executeUpdate();
        	}
        	else
        	{
        		System.out.println("No meeting to cancel at that time");
        	}
        	resultSet1.close();
        	resultSet2.close();
        	resultSet3.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to cancel meeting: " + e.getMessage()+ "***");
		}

	}
	
	public boolean cancelMeetingParticipant(String scheduleCode, String meetingCode, int time, GregorianCalendar day) throws Exception {
		try {
			//UPDATE tableName SET colname = ? WHERE schedId = ? AND dayID = ? AND dayDate = ? --Retrieves timeslots
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE shareCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, scheduleCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	int scheduleID = resultSet1.getInt("scheduleID");
        	Date startDate = new Date(day.getTimeInMillis());
        	long startTime = convertTimeToDB(time);
        	System.out.println(startTime);
        	
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	query = "SELECT * FROM Timeslot WHERE scheduleID = ? AND dayID = ? AND startTime = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setInt(2, dayID);
        	ps.setLong(3, startTime);
        	ResultSet resultSet3 = ps.executeQuery();
        	resultSet3.next();
        	
        	if (resultSet3.getString("meetingCode")==meetingCode)
        	{
            	//Create new timeslot+meeting
        		query = "UPDATE Timeslot SET available = ?, participantInfo = ?, meetingCode = ? WHERE scheduleID = ? AND dayID =? AND startTime = ?";
            	ps = conn.prepareStatement(query);
            	ps.setInt(1, 1);
            	ps.setString(2, null);
            	ps.setString(3, null);
            	ps.setInt(4, scheduleID);
            	ps.setInt(5, dayID);
            	ps.setLong(6, startTime);
            	ps.executeUpdate();
        	}
        	else
        	{
        		System.out.println("Wrong code, correct code: " + resultSet3.getString("meetingCode"));
            	resultSet1.close();
            	resultSet2.close();
            	resultSet3.close();
            	ps.close();
        		return false;
        	}
        	resultSet1.close();
        	resultSet2.close();
        	resultSet3.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to cancel meeting: " + e.getMessage()+ "***");
		}
	}

	public boolean openAllSlotsDay(String scheduleCode, String secretCode, GregorianCalendar date) throws Exception {
		
		try {
			//UPDATE tableName SET colname = ? WHERE schedId = ? AND dayID = ? AND dayDate = ? --Retrieves timeslots
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	Date startDate = new Date(date.getTimeInMillis());
        	int scheduleID = resultSet1.getInt("scheduleID");
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	//Selects all timeslots from indicated schedule on desired day
        	query = "UPDATE Timeslot SET available = ? WHERE scheduleID = ? AND dayID = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, 1);
        	ps.setInt(2, scheduleID);
        	ps.setInt(3, dayID);
        	ps.executeUpdate();
        	
        	resultSet1.close();
        	resultSet2.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to open all slots on desired day: " + e.getMessage()+ "***");
		}
	}

	public boolean closeAllSlotsDay(String scheduleCode, String secretCode, GregorianCalendar date) throws Exception {
		
		try {
			//UPDATE tableName SET colname = ? WHERE schedId = ? AND dayID = ? AND dayDate = ? --Retrieves timeslots
			//Remove schedule from database
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, secretCode);
        	ResultSet resultSet1 = ps.executeQuery();
        	resultSet1.next();
        	Date startDate = new Date(date.getTimeInMillis());
        	int scheduleID = resultSet1.getInt("scheduleID");
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ? AND dayDate = ?";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, scheduleID);
        	ps.setDate(2, startDate);
        	ResultSet resultSet2 = ps.executeQuery();
        	resultSet2.next();
        	int dayID = resultSet2.getInt("dayID");
        	
        	//Selects all timeslots from indicated schedule on desired day
        	query = "UPDATE Timeslot SET available = ? WHERE scheduleID = ? AND dayID = ?";
        	ps  =conn.prepareStatement(query);
        	ps.setInt(1, 0);
        	ps.setInt(2, scheduleID);
        	ps.setInt(3, dayID);
        	ps.executeUpdate();
        	
        	resultSet1.close();
        	resultSet2.close();
        	ps.close();
			return true;
		}
		catch(Exception e){
			
			throw new Exception("***Failed to close slots on desired day: " + e.getMessage()+ "***");
		}

	}

	public boolean openAllTimeSlotsTime(String scheduleCode, String secretCode, int time) throws Exception {
		//System.out.println("MOVEBITCHGETOUTTHEWAY");
		try {
			String query = "SELECT scheduleID FROM Schedule WHERE organizerCode=?";
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, secretCode);
	    	ResultSet resultSet = ps.executeQuery();
	    	resultSet.next();		
			
	    	int schedID = resultSet.getInt("scheduleID");
	    	
	    	query = "UPDATE Timeslot SET available=1 WHERE scheduleID=? AND startTime=?";
	    	ps = conn.prepareStatement(query);
	    	ps.setInt(1, schedID);
	    	ps.setLong(2, convertTimeToDB(time));
	    	ps.executeUpdate();
	    	
	    	resultSet.close();
	    	ps.close();
	    	
			return true;
	    	
		} catch (Exception e) {
			throw new Exception("Couldn't open timeslots at specified time " + e.getMessage());
		}
	}

	public boolean closeAllTimeSlotsTime(String scheduleCode, String secretCode, int time) throws Exception {
		try {
			String query = "SELECT scheduleID FROM Schedule WHERE organizerCode=?";
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, secretCode);
	    	ResultSet resultSet = ps.executeQuery();
	    	resultSet.next();		
			
	    	int schedID = resultSet.getInt("scheduleID");
	    	
	    	query = "UPDATE Timeslot SET available=0 WHERE scheduleID=? AND startTime=?";
	    	ps = conn.prepareStatement(query);
	    	ps.setInt(1, schedID);
	    	ps.setLong(2, convertTimeToDB(time));
	    	ps.executeUpdate();
	    	
	    	resultSet.close();
	    	ps.close();
	    	
			return true;
	    	
		} catch (Exception e) {
			throw new Exception("Couldn't close timeslots at specified time " + e.getMessage());
		}
	}
	
public ArrayList<String> reportActivity(int hours) throws Exception {
    try {
			ArrayList<String> s = new ArrayList<String>();
			
			String query = "SELECT * FROM Schedule";
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	//ps.setString(1, shareCode);
	    	ResultSet resultSet1 = ps.executeQuery();
	    	//resultSet1.next();
	    	while (resultSet1.next())
	    	{
	    		GregorianCalendar startDate = new GregorianCalendar();
	        	startDate.setTime(resultSet1.getDate("startDate"));
	        	GregorianCalendar endDate = new GregorianCalendar();
	        	endDate.setTime(resultSet1.getDate("endDate"));
	        	GregorianCalendar createdDate = new GregorianCalendar();
	        	createdDate.setTime(resultSet1.getTimestamp("createdDate"));
	        	Schedule schedule = new Schedule(resultSet1.getString("scheduleName"),
	        									 startDate,
	        									 endDate,
	        									 resultSet1.getInt("meetingDuration"),
	        									 convertTimeToMilitary(resultSet1.getLong("startTime")),
	        									 convertTimeToMilitary(resultSet1.getLong("endTime")),
	        									 createdDate,
	        									 resultSet1.getString("organizerCode"),
	        									 resultSet1.getString("shareCode"));
	        	GregorianCalendar current = new GregorianCalendar();
	        	
	        	//GregorianCalendar schedDate = schedule.getCreatedDate();	
	        	
	    		if (schedule.getCreatedDate().getTimeInMillis() > (current.getTimeInMillis() - TimeUnit.HOURS.toMillis(hours)))
	    		{
	    			s.add(schedule.getScheduleName());
	    		}
	    	}
			return s;
    	}
    	catch (Exception e) {
			throw new Exception("Couldn't return any schedules: " + e.getMessage());
		}
	}

	public ArrayList<String> retrieveOldSchedules(int days) throws Exception {
    	try {
			ArrayList<String> s = new ArrayList<String>();
			
			String query = "SELECT * FROM Schedule";
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	//ps.setString(1, shareCode);
	    	ResultSet resultSet1 = ps.executeQuery();
	    	//resultSet1.next();
	    	while (resultSet1.next())
	    	{
	    		GregorianCalendar startDate = new GregorianCalendar();
	        	startDate.setTime(resultSet1.getDate("startDate"));
	        	GregorianCalendar endDate = new GregorianCalendar();
	        	endDate.setTime(resultSet1.getDate("endDate"));
	        	GregorianCalendar createdDate = new GregorianCalendar();
	        	createdDate.setTime(resultSet1.getTimestamp("createdDate"));
	        	Schedule schedule = new Schedule(resultSet1.getString("scheduleName"),
	        									 startDate,
	        									 endDate,
	        									 resultSet1.getInt("meetingDuration"),
	        									 convertTimeToMilitary(resultSet1.getLong("startTime")),
	        									 convertTimeToMilitary(resultSet1.getLong("endTime")),
	        									 createdDate,
	        									 resultSet1.getString("organizerCode"),
	        									 resultSet1.getString("shareCode"));
	        	
	        	GregorianCalendar current = new GregorianCalendar();
	        	GregorianCalendar schedDate = schedule.getEndDate();
	        	long endDateTime = resultSet1.getLong("endTime");
	    		
	    		if ((schedDate.getTimeInMillis() + endDateTime) < (current.getTimeInMillis() - TimeUnit.DAYS.toMillis(days)))
	    		{
	    			s.add(schedule.getScheduleName());
	    		}
	    	}
			return s;
    	}
    	catch (Exception e) {
			throw new Exception("Couldn't find any schedules: " + e.getMessage());
		}
	}

	public boolean deleteOldSchedules(int days)  throws Exception {
    	try {
			String query = "SELECT * FROM Schedule";
	    	PreparedStatement ps = conn.prepareStatement(query);
	    	ResultSet resultSet1 = ps.executeQuery();
	    	while (resultSet1.next())
	    	{
	    		GregorianCalendar startDate = new GregorianCalendar();
	        	startDate.setTime(resultSet1.getDate("startDate"));
	        	GregorianCalendar endDate = new GregorianCalendar();
	        	endDate.setTime(resultSet1.getDate("endDate"));
	        	GregorianCalendar createdDate = new GregorianCalendar();
	        	createdDate.setTime(resultSet1.getTimestamp("createdDate"));
	        	Schedule schedule = new Schedule(resultSet1.getString("scheduleName"),
	        									 startDate,
	        									 endDate,
	        									 resultSet1.getInt("meetingDuration"),
	        									 convertTimeToMilitary(resultSet1.getLong("startTime")),
	        									 convertTimeToMilitary(resultSet1.getLong("endTime")),
	        									 createdDate,
	        									 resultSet1.getString("organizerCode"),
	        									 resultSet1.getString("shareCode"));
	        	
	        	GregorianCalendar current = new GregorianCalendar();
	        	GregorianCalendar schedDate = schedule.getEndDate();
	        	long endDateTime = resultSet1.getLong("endTime");
	    		
	    		if ((schedDate.getTimeInMillis() + endDateTime) < (current.getTimeInMillis() - TimeUnit.DAYS.toMillis(days)))
	    		{
	    			int scheduleID = resultSet1.getInt("scheduleID");
	            	query = "DELETE FROM Timeslot WHERE scheduleID = ?";
	            	ps = conn.prepareStatement(query);
	            	ps.setInt(1, scheduleID);
	            	ps.executeUpdate();
	            	
	            	query = "DELETE FROM Day WHERE scheduleID = ?";
	            	ps = conn.prepareStatement(query);
	            	ps.setInt(1, scheduleID);
	            	ps.executeUpdate();

	            	query = "DELETE FROM Schedule WHERE scheduleID = ?";
	            	ps = conn.prepareStatement(query);
	            	ps.setInt(1, scheduleID);
	            	ps.executeUpdate();
	    		}
	    	}
	    	System.out.println("Deleted old schedules");
			return true;
    	}
    	catch (Exception e) {
			throw new Exception("Couldn't find any schedules to delete: " + e.getMessage());
		}
	}
	
	public boolean extendEndDate(String shareCode, String organizerCode, GregorianCalendar newEndDate) throws Exception {
		try {
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
			PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, organizerCode);
	    	ResultSet resultSet1 = ps.executeQuery();
	    	resultSet1.next();
	    	int scheduleID = resultSet1.getInt("scheduleID");
	    	long startTime = resultSet1.getLong("startTime");
	    	long endTime = resultSet1.getLong("endTime");
	    	int duration = resultSet1.getInt("meetingDuration");
	    	Date endDate = resultSet1.getDate("endDate");
	    	
	    	query = "UPDATE Schedule SET endDate = ? WHERE scheduleID = ?";
	    	ps = conn.prepareStatement(query);
	    	ps.setDate(1, new Date(newEndDate.getTimeInMillis()));
	    	ps.setInt(2, scheduleID);
	    	ps.executeUpdate();
	    	String calCurr = endDate.toString();
	    	Date calEnd = new Date(newEndDate.getTimeInMillis());
	    	String calEndd = calEnd.toString();
	    	GregorianCalendar calendarCurrent = parseDate(endDate.toString());
	    	//GregorianCalendar calendarEnd = newEndDate;
	    	
	    	while(calendarCurrent.equals(newEndDate)==false) {
	    		calendarCurrent.add(Calendar.DAY_OF_MONTH, 1);
	    		if (calendarCurrent.DAY_OF_WEEK != calendarCurrent.SUNDAY || calendarCurrent.DAY_OF_WEEK != calendarCurrent.SATURDAY) {
	        		Date currentDate = new Date(calendarCurrent.getTimeInMillis());
	        		
	        		query = "INSERT INTO Day (dayID, dayDate, dayStartTime, dayEndTime, scheduleID) values(NULL, ?, ?, ?, ?);";
	        		ps = conn.prepareStatement(query);
	        		ps.setDate(1, currentDate);
	        		ps.setLong(2, startTime);
	        		ps.setLong(3, endTime);
	        		ps.setInt(4, scheduleID);
	        		ps.execute();
	        		
	        		//Populate day with timeslots
	        		
	        		query = "SELECT dayID FROM Day WHERE scheduleID = ? AND dayDate =?";
	        		ps = conn.prepareStatement(query);
	        		ps.setInt(1, scheduleID);
	        		ps.setDate(2, currentDate);
	        		ResultSet resultSet2 = ps.executeQuery();
	        		resultSet2.next();
	        		int dayID = resultSet2.getInt("dayID");
	        		int schedID = scheduleID;
	        		//Timeslots
	        		long currentTime = startTime;
	        		long et = endTime;
	        		while (currentTime < et)
	        		{
	        			query = "INSERT INTO Timeslot (startTime, available, participantInfo, meetingCode, dayDate, scheduleID, dayID) values(?, true, NULL, NULL, ?, ?, ?);";
		    			ps = conn.prepareStatement(query);
		    			ps.setLong(1, currentTime);
		    			ps.setDate(2, currentDate);
		    			ps.setInt(3, scheduleID);
		    			ps.setInt(4, dayID);
		    			ps.execute();
		    			
		    			currentTime = currentTime + duration*60*1000;
	        		}
	    		}
	    		
	    	}
	    	resultSet1.close();
	    	ps.close();
			
			
			return true;
			
		}
		catch (Exception e){
			throw new Exception("Failed to extend end date: " + e.getMessage());
		}

	}
	
	public boolean extendStartDate(String shareCode, String organizerCode, GregorianCalendar newStartDate) throws Exception {
		try {
			String query = "SELECT * FROM Schedule WHERE organizerCode = ?";
			PreparedStatement ps = conn.prepareStatement(query);
	    	ps.setString(1, organizerCode);
	    	ResultSet resultSet1 = ps.executeQuery();
	    	resultSet1.next();
	    	int scheduleID = resultSet1.getInt("scheduleID");
	    	long startTime = resultSet1.getLong("startTime");
	    	long endTime = resultSet1.getLong("endTime");
	    	int duration = resultSet1.getInt("meetingDuration");
	    	Date startDate = resultSet1.getDate("startDate");
	    	
	    	query = "UPDATE Schedule SET startDate = ? WHERE scheduleID = ?";
	    	ps = conn.prepareStatement(query);
	    	ps.setDate(1, new Date(newStartDate.getTimeInMillis()));
	    	ps.setInt(2, scheduleID);
	    	ps.executeUpdate();
	    	String calCurr = startDate.toString();
	    	Date calStart = new Date(newStartDate.getTimeInMillis());
	    	String calSrt = calStart.toString();
	    	GregorianCalendar calendarStart = parseDate(startDate.toString());
	    	//newStartDate.add(Calendar.DAY_OF_MONTH, 1);
	    	calendarStart.add(Calendar.DAY_OF_MONTH, -1);
	    	//GregorianCalendar calendarEnd = newEndDate;
	    	
	    	while(newStartDate.equals(calendarStart)==false) {
	    		if (newStartDate.DAY_OF_WEEK != newStartDate.SUNDAY || newStartDate.DAY_OF_WEEK != newStartDate.SATURDAY) {
	        		Date currentDate = new Date(newStartDate.getTimeInMillis());
	        		
	        		query = "INSERT INTO Day (dayID, dayDate, dayStartTime, dayEndTime, scheduleID) values(NULL, ?, ?, ?, ?);";
	        		ps = conn.prepareStatement(query);
	        		ps.setDate(1, currentDate);
	        		ps.setLong(2, startTime);
	        		ps.setLong(3, endTime);
	        		ps.setInt(4, scheduleID);
	        		ps.execute();
	        		
	        		//Populate day with timeslots
	        		
	        		query = "SELECT dayID FROM Day WHERE scheduleID = ? AND dayDate =?";
	        		ps = conn.prepareStatement(query);
	        		ps.setInt(1, scheduleID);
	        		ps.setDate(2, currentDate);
	        		ResultSet resultSet2 = ps.executeQuery();
	        		resultSet2.next();
	        		int dayID = resultSet2.getInt("dayID");
	        		int schedID = scheduleID;
	        		//Timeslots
	        		long currentTime = startTime;
	        		long et = endTime;
	        		while (currentTime < et)
	        		{
	        			query = "INSERT INTO Timeslot (startTime, available, participantInfo, meetingCode, dayDate, scheduleID, dayID) values(?, true, NULL, NULL, ?, ?, ?);";
		    			ps = conn.prepareStatement(query);
		    			ps.setLong(1, currentTime);
		    			ps.setDate(2, currentDate);
		    			ps.setInt(3, scheduleID);
		    			ps.setInt(4, dayID);
		    			ps.execute();
		    			
		    			currentTime = currentTime + duration*60*1000;
	        		}
	        		newStartDate.add(Calendar.DAY_OF_MONTH, 1);
	    		}
	    		
	    	}
	    	resultSet1.close();
	    	ps.close();
			
			
			return true;
			
		}
		catch (Exception e){
			throw new Exception("Failed to extend end date: " + e.getMessage());
		}

	}
    
    private long convertTimeToDB(int inputTime) {
    	long millisTime = 0;
    	String inputTimeStr = Integer.toString(inputTime);
    	String inputHours;
    	String inputMin;
    	
    	if (inputTime == 0) {
    		inputHours = "00";
    		inputMin = "00";
    	}
    	else if (inputTime < 1000) {
        	inputHours = inputTimeStr.substring(0, 1);
        	inputMin = inputTimeStr.substring(1);
    	}
    	else {
        	inputHours = inputTimeStr.substring(0, 2);
        	inputMin = inputTimeStr.substring(2);
    	}
    	
    	millisTime = Integer.valueOf(inputHours)*3600*1000 + Integer.valueOf(inputMin)*60*1000;
    	return millisTime;
    }

    private int convertTimeToMilitary(long inputMillis) {
    	int militaryTime = 0;
    	
    	long rawMinutes = inputMillis/(1000*60);
    	long hours = rawMinutes/60;
    	long mins = rawMinutes%60;
    	
    	if (mins == 0) {
        	militaryTime = Integer.valueOf(Long.toString(hours) + "00");    		
    	}
    	else {
        	militaryTime = Integer.valueOf(Long.toString(hours) + Long.toString(mins));    		
    	}

    	return militaryTime;
    }

	public GregorianCalendar parseDate(String date) { ///take in date as "YYYY-MM-DD"
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day = Integer.parseInt(date.substring(8));
		return new GregorianCalendar(year, month-1, day);
	}



}
