package entity;

import java.util.ArrayList;
import java.util.Date;

public class Scheduler 
{
	public ArrayList<Schedule> schedules;
	
	public Scheduler()
	{
		
	}
	
	public void createSchedule(Schedule schedule)
	{
		System.out.println(""+schedule.scheduleName);
		schedules.add(schedule);
	}
	
	public ArrayList<Schedule> requestOldSchedules(int days)
	{
		ArrayList<Schedule> sched = new ArrayList<Schedule>();
		Date current = new Date();
		for (Schedule schedule : schedules)
		{
			//If schedule is more than 'days' days old, return it
			if (current.getDate() - schedule.getStartDate().getDate() <= days)
			{
				sched.add(schedule);
			}
		}
		return sched;
	}
	
	public void deleteOldSchedules(int days)
	{
		Date current = new Date();
		for (Schedule schedule : schedules)
		{
			if (current.getDate() - schedule.getStartDate().getDate() <= days)
			{
				schedules.remove(schedule);
			}
		}
	}
	
	public ArrayList<Schedule> requestNewSchedules(int hours) 
	{
		ArrayList<Schedule> sched = new ArrayList<Schedule>();
		Date current = new Date();
		for (Schedule schedule : schedules)
		{
			if (current.getDate() - schedule.getStartDate().getDate() <= hours)
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
