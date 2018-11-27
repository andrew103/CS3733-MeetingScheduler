package entity;

public class Meeting {

	private String participantInfo;
	private String meetingCode;
	
	public Meeting(String pI)
	{
		participantInfo = pI;
		meetingCode = "lol";
	}
	
	public String getParticipantInfo() { return participantInfo; }
	public String getMeetingCode() {return meetingCode; }
}
