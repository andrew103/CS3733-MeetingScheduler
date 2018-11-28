package entity;

import java.util.ArrayList;

public class Scheduler 
{
	private ArrayList<Schedule> schedules;
	
	//Ensuring only one instance of scheduler exists
	private static Scheduler instance;
	
	static {
		instance = new Scheduler();
	}
	
	private Scheduler()
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
		for (Schedule schedule : schedules)
		{
			//If schedule is more than 'days' days old, return it
			//if (schedule.getStartDate())
			schedules.add(schedule);
		}
		return sched;
	}
}
