package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import no.ntnu.fp.model.Event.Type;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Reservation {

	Date date;
	Time startTime;
	Time endTime;
	
	public Reservation(Date date, Time start, Time end)
	{
		this.date=date;
		this.startTime=start;
		this.endTime=end;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}	
}
