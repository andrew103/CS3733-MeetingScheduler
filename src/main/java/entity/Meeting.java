package entity;

public class Meeting 
{

	private String participantInfo;
	private String meetingCode;
	
	public Meeting(String partInfo)
	{
		this.participantInfo = partInfo;
	}
	
	public String getParticipantInfo()
	{
		return participantInfo;
	}
	
	public String getMeetingCode()
	{
		return meetingCode;
	}
}
