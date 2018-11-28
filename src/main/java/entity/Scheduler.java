package entity;

import java.util.ArrayList;

public class Scheduler 
{
	private ArrayList<Schedule> schedules;
	
	public Scheduler()
	{
		
	}
	
	public void createSchedule(Schedule s)
	{
		schedules.add(s);
	}
	
	public ArrayList<Schedule> requestOldSchedules(int days)
	{
		ArrayList<Schedule> sched = new ArrayList<Schedule>();
		for (Schedule schedule : schedules)
		{
			//If schedule is more than 'days' days old, return it
			//if (schedule.getStartDate())
			schedules.add(schedule);
		}
		return sched;
	}
}
