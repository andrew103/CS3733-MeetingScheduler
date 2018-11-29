package entity;

public class Timeslot 
{
	private boolean available;
	private int startTime;
	Meeting m = null;
	
	public Timeslot(boolean available, int startTime) {
		this.available = available;
		this.startTime = startTime;
	}
	
	public void addMeeting(Meeting m) {
		this.m = m;
	}
	
	public void removeMeeting() {
		this.m = null;
	}
	
	public boolean isAvailable() {
		return available;
	}
}
