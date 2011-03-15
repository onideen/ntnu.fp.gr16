package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.List;

import no.ntnu.fp.model.Event.Type;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Reservation {

	int reservationID;
	int eventID;
	String responsible;
	String roomName;
	Date date;
	Date startTime;
	Date endTime;
	
	public Reservation(int eventID, String responsible, String roomName, Date date, Date start, Date end)
	{
		this.reservationID=reservationID;
		this.eventID=eventID;
		this.responsible=responsible;
		this.roomName=roomName;
		this.date=date;
		this.startTime=start;
		this.endTime=end;
	}
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int id) {
		this.eventID=id;
	}
	
	public int getReservationID()
	{
		return reservationID;
	}
	
	public void setReservationID(int id)
	{
		this.reservationID=id;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName=roomName;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	
	
	public String getResponsible() {
		return responsible;
	}
	
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
}
