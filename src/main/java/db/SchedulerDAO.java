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

    public boolean createSchedule(Schedule schedule) {
    	return true;
    }
    
    public boolean getSchedule(String shareCode) {
    	return true;
    }
}
