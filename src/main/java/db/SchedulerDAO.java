package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Day;
import entity.Meeting;
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
        	ps.setString(1, schedule.getShareCode());
        	ps.setString(2, schedule.getOrganizerCode());
        	ps.setString(3, schedule.getScheduleName());
        	ps.setInt(4, schedule.getDuration());
        	ps.setDate(5, schedule.getStartDate());
        	ps.setDate(6, schedule.getEndDate());
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
        	
        	ps.close();
        	return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to create new schedule: " + e.getMessage());
    	}
    }
    
    public boolean getSchedule(String shareCode) throws Exception {
    	try {
        	String query = "";
        	PreparedStatement ps = conn.prepareStatement(query);

        	ps.close();
        	return true;
    	} catch (Exception e) {
    		throw new Exception("Failed to get schedule: " + e.getMessage());    		
    	}
    }
}
