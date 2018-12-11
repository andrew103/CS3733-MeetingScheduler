package entity;

import org.json.simple.JSONObject;

public class Timeslot 
{
	private boolean available;
	private int startTime;
	private String participantInfo;
	private String meetingCode;
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public Timeslot(boolean available, int startTime) {
		this.available = available;
		this.startTime = startTime;
		this.participantInfo = "";
		this.meetingCode = "";
	}

	public Timeslot(boolean available, int startTime, String participantInfo) {
		this.available = available;
		this.startTime = startTime;
		this.participantInfo = participantInfo;
		this.meetingCode = randomAlphaNumeric(10);
	}

	public Timeslot(boolean available, int startTime, String participantInfo, String meetingCode) {
		this.available = available;
		this.startTime = startTime;
		this.participantInfo = participantInfo;
		this.meetingCode = meetingCode;
	}

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}

		return builder.toString();
	}
	
	public String getMeetingCode() {
		return this.meetingCode;
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
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("isClosed", this.available);
		obj.put("startTime", this.startTime);
		obj.put("participantInfo", this.participantInfo);
		return obj;
	}
}
