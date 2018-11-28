import java.util.Date;
import java.util.GregorianCalendar;

import entity.Schedule;
import entity.Scheduler;
import junit.framework.TestCase;

public class testModel extends TestCase{
	
	public void testSchedule()
	{
		Scheduler master = new Scheduler();
		Schedule sched1 = new Schedule("R's Office Hours", new GregorianCalendar(2018, 11, 20), new GregorianCalendar(2018, 11, 27), "bestCodeEver");
		Schedule sched2 = new Schedule("J's Office Hours", new GregorianCalendar(2018, 11, 10), new GregorianCalendar(2018, 11, 27), "worstCodeEver");
		System.out.println("Whut "+sched1.getScheduleName());
		master.addSchedule(sched1);
		master.addSchedule(sched2);
		assertTrue(master.requestOldSchedules(10).equals(sched2));
	}

}
