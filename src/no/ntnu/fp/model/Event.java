package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import no.ntnu.fp.model.calendar.Utils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Event {

	int eid = -1;
	String roomName;
	Date date;
	Time startTime;
	Time endTime;
	String description;
	Type type;
	String responsible;
	
	List<String> attendees = new ArrayList<String>();
	
	public enum Type{
		Meeting,
		Appointment
	}
	
	private PropertyChangeSupport propChangeSupp;

	public final static String PROPERTY_EID = "eid";
	public final static String PROPERTY_DATE = "date";
	public final static String PROPERTY_STARTTIME = "starttime";
	public final static String PROPERTY_ENDTIME = "endtime";
	public final static String PROPERTY_DESCRIPTION = "description";
	public final static String PROPERTY_TYPE = "type";
	public final static String PROPERTY_RESPONSIBLE = "responsible";
	public final static String PROPERTY_ATTENDEE = "attendee";

	public Event() {
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	public Event(String desc, Type type, String owner, Date date, Time start, Time end, String room){
		this();
		this.description=desc;
		this.type=type;
		this.responsible=owner;
		this.date=date;
		this.startTime=start;
		this.endTime=end;
		this.roomName=room;
	}
	
	public Event(int eid, String type, String desc, Date date, Time start, Time end, String responsible, List<String> attendees, String room)
	{
		this();
		this.eid=eid;
		this.type=Type.valueOf(type);
		this.description=desc;
		this.date=date;
		this.startTime=start;
		this.endTime=end;
		this.responsible=responsible;
		this.attendees=attendees;
		this.roomName=room;
	}
	
	public String getDateString()
	{
		return date.toString() + ", kl " + startTime.toString() + " - " + endTime.toString();
	}
	
	public void addAttendee(String email)
	{
		attendees.add(email);
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_ATTENDEE, null, email);
		propChangeSupp.firePropertyChange(event);
	}
	
	public List<String> getAttendees()
	{
		return attendees;
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
	
	public String getRoom() {
		return roomName;
	}

        Room roomObject = null;
		private boolean first;
        public Room getRoomObject()
        {
            if(roomObject!=null)
                if(roomObject.getName().equals(getRoom()))
                    return roomObject;

            roomObject = Communication.getARoom(getRoom());
            return roomObject;
        }
	
	public void setRoom(String room) {
		String old = this.roomName;
		this.roomName=room;
		PropertyChangeEvent event = new PropertyChangeEvent(this, Room.PROPERTY_NAME, old, room);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Calendar getDate() {
		return Utils.getCalendar(date);
	}
	
	public void setDate(Calendar date) {
		Date old = this.date;
		this.date = Utils.getSqlDate(date);
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_DATE, old, this.date);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Calendar getStartTime() {
		return Utils.getCalendar(startTime);
	}
	
	public void setStartTime(Calendar startTime) {
		Time old = this.startTime;
		this.startTime = Utils.getSqlTime(startTime);
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_STARTTIME, old, this.startTime);
		propChangeSupp.firePropertyChange(event);
	}
	
	public Calendar getEndTime() {
		return Utils.getCalendar(endTime);
	}
	
	public void setEndTime(Calendar endTime) {
		Time old = this.endTime;
		this.endTime = Utils.getSqlTime(endTime);
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_ENDTIME, old, this.endTime);
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
	
	public String getResponsible() {
		return responsible;
	}
	
	public void setResponsible(String responsible) {
		String old = this.responsible;
		this.responsible = responsible;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_RESPONSIBLE, old, responsible);
		propChangeSupp.firePropertyChange(event);
	}

	public void setFirst(boolean b) {
		first = b;
		
	}

	public boolean getFirst() {
		return first;
	}
	
}
