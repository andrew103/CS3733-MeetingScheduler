package entity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Scheduler 
{
	public ArrayList<Schedule> schedules;
	
	//Ensuring only one instance of scheduler exists
	private static Scheduler instance;
	
	static {
		instance = new Scheduler();
	}
	
	public Scheduler()
	{
		
	}
	
    public static Scheduler getInstance() {
        return instance;
    }
	
	public void addSchedule(Schedule s)
	{
		schedules.add(s);
	}
	
	
	public ArrayList<Schedule> requestOldSchedules(int days)
	{
		ArrayList<Schedule> sched = new ArrayList<Schedule>();
		GregorianCalendar current = new GregorianCalendar();
		for (Schedule schedule : schedules)
		{
			if (current.getTime().getTime() - schedule.getStartDate().getTime().getTime() <= TimeUnit.DAYS.toMillis(days))
			{
				sched.add(schedule);
			}
		}
		return sched;
	}
	
	public void deleteOldSchedules(int days)
	{
		GregorianCalendar current = new GregorianCalendar();
		for (Schedule schedule : schedules)
		{
			if (current.getTime().getTime() - schedule.getStartDate().getTime().getTime() <= TimeUnit.DAYS.toMillis(days))
			{
				schedules.remove(schedule);
			}
		}
	}
	
	public ArrayList<Schedule> requestNewSchedules(int hours) 
	{
		ArrayList<Schedule> sched = new ArrayList<Schedule>();
		GregorianCalendar current = new GregorianCalendar();
		for (Schedule schedule : schedules)
		{
			if (current.getTime().getTime() - schedule.getStartDate().getTime().getTime() <= TimeUnit.HOURS.toMillis(hours))
			{
				sched.add(schedule);
			}
		}
		return sched;
		
	}
	
	public Schedule retrieveSchedule(String shareCode)
	{
		for (Schedule s : schedules)
		{
			if (s.getShareCode().equals(shareCode))
			{
				return s;
			}
		}
		return null;
	}
}
