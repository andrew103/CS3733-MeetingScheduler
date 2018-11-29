package entity;

public class Timeslot 
{
	private boolean available;
	private int startTime;
	private String participantInfo;
	private String meetingCode;
	
	public Timeslot(boolean available, int startTime) {
		this.available = available;
		this.startTime = startTime;
		this.participantInfo = "";
		this.meetingCode = "";
	}

	public Timeslot(boolean available, int startTime, String participantInfo, String meetingCode) {
		this.available = available;
		this.startTime = startTime;
		this.participantInfo = participantInfo;
		this.meetingCode = meetingCode;
	}

	public void addMeeting() {
//		this.m = m;
	}
	
	public void removeMeeting() {
//		this.m = null;
	}
	
	public boolean isAvailable() {
		return available;
	}
}
