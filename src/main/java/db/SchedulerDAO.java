package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.GregorianCalendar;

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
        	String query = "INSERT INTO Schedule (scheduleID, shareCode, organizerCode, scheduleName, meetingDuration, startDate, endDate, startTime, endTime) values(NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
        	PreparedStatement ps = conn.prepareStatement(query);
        	Date startDate = new Date(schedule.getStartDate().getTimeInMillis());
        	Date endDate = new Date(schedule.getEndDate().getTimeInMillis());
        	
        	
        	ps.setString(1, schedule.getShareCode());
        	ps.setString(2, schedule.getOrganizerCode());
        	ps.setString(3, schedule.getScheduleName());
        	ps.setInt(4, schedule.getDuration());
        	ps.setDate(5, startDate);
        	ps.setDate(6, endDate);
        	ps.setInt(7, schedule.getStartTime());
        	ps.setInt(8, schedule.getEndTime());
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
            		
            		query = "INSERT INTO Day (dayDate, dayStartTime, dayEndTime, scheduleID) values(?, ?, ?, ?);";
            		ps = conn.prepareStatement(query);
            		ps.setDate(1, currentDate);
            		ps.setInt(2, schedule.getStartTime());
            		ps.setInt(3, schedule.getEndTime());
            		ps.setInt(4, schedID);
            		ps.execute();        			
        		}
        		
        		calendarCurrent.add(Calendar.DAY_OF_MONTH, 1);
        	}
        	
        	query = "SELECT dayDate FROM Day WHERE scheduleID = ?;";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, schedID);
        	resultSet = ps.executeQuery();
        	while (resultSet.next()) {
            	int currentTime = schedule.getStartTime();
            	int endTime = schedule.getEndTime();
        		while (currentTime != endTime && currentTime < endTime) {
        			query = "INSERT INTO Timeslot (startTime, available, participantInfo, meetingCode, dayDate) values(?, true, NULL, NULL, ?);";
        			ps = conn.prepareStatement(query);
        			ps.setInt(1, currentTime);
        			ps.setDate(2, resultSet.getDate("dayDate"));
        			ps.execute();
        			
        			currentTime = currentTime + schedule.getDuration();
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
        	Schedule schedule = new Schedule(resultSet1.getString("scheduleName"),
        									 startDate,
        									 endDate,
        									 resultSet1.getInt("meetingDuration"),
        									 resultSet1.getInt("startTime"),
        									 resultSet1.getInt("endTime"),
        									 resultSet1.getString("organizerCode"),
        									 resultSet1.getString("shareCode"));
        	
        	query = "SELECT * FROM Day WHERE scheduleID = ?;";
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, resultSet1.getInt("scheduleID"));
        	ResultSet resultSet2 = ps.executeQuery();
        	while (resultSet2.next()) {
        		Day day = new Day(resultSet2.getInt("dayStartTime"), resultSet2.getInt("dayEndTime"));
        		
        		query = "SELECT * FROM Timeslot WHERE dayDate = ?;";
        		ps = conn.prepareStatement(query);
        		ps.setDate(1, resultSet2.getDate("dayDate"));
        		ResultSet resultSet3 = ps.executeQuery();
        		while (resultSet3.next()) {
        			resultSet3.getString("participantInfo");
        			if (resultSet3.wasNull()) {
            			Timeslot timeslot = new Timeslot(resultSet3.getBoolean("available"), resultSet3.getInt("startTime"));
            			day.addTimeslot(timeslot);
        			}
        			else {
            			Timeslot timeslot = new Timeslot(resultSet3.getBoolean("available"), resultSet3.getInt("startTime"), resultSet3.getString("participantInfo"), resultSet3.getString("meetingCode"));
            			day.addTimeslot(timeslot);
        			}        			
        		}
        		
        		schedule.addDay(day);
        		resultSet3.close();
        	}
        	System.out.println("After resultset2");
        	
        	resultSet2.close();
        	resultSet1.close();
        	ps.close();
        	return schedule;
    	} catch (Exception e) {
    		throw new Exception("Failed to get schedule: " + e.getMessage());    		
    	}
    }
}
