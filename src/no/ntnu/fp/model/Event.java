package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Event {

	int eid;
	int reservationID;
	Date date;
	Date startTime;
	Date endTime;
	String description;
	Type type;
	
	public enum Type{
		Meeting,
		Appointment
	}
	
	private PropertyChangeSupport propChangeSupp;

	public final static String PROPERTY_EID = "eid";
	public final static String PROPERTY_RESERVATIONID = "reservationid";
	public final static String PROPERTY_DATE = "date";
	public final static String PROPERTY_STARTTIME = "starttime";
	public final static String PROPERTY_ENDTIME = "endtime";
	public final static String PROPERTY_DESCRIPTION = "description";
	public final static String PROPERTY_TYPE = "type";
	
	public Event(int eid, String type, int resID, String desc, Date date, Date start, Date end)
	{
		this.eid=eid;
		this.type=Type.valueOf(type);
		this.reservationID=resID;
		this.description=desc;
		this.date=date;
		this.startTime=start;
		this.endTime=end;
	}
	
	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
		int old = this.eid;
		this.eid = eid;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_EID, old, eid);
		propChangeSupp.firePropertyChange(event);
	}
	
	public int getReservationID() {
		return reservationID;
	}
	
	public void setReservationID(int reservationID) {
		int old = this.reservationID;
		this.reservationID = reservationID;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_RESERVATIONID, old, reservationID);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		Date old = this.date;
		this.date = date;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_DATE, old, date);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		Date old = this.startTime;
		this.startTime = startTime;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_STARTTIME, old, startTime);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		Date old = this.endTime;
		this.endTime = endTime;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_ENDTIME, old, endTime);
		propChangeSupp.firePropertyChange(event);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		String old = this.description;
		this.description = description;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_DESCRIPTION, old, description);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		Type old = this.type;
		this.type = type;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_TYPE, old, type);
		propChangeSupp.firePropertyChange(event);
	}
	
	public List<Person> getAttendees(){
		throw new NotImplementedException();
	}
}
