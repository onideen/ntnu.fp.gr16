package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import no.ntnu.fp.model.Event.Type;
import no.ntnu.fp.model.calendar.Utils;
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
	
	public Calendar getStartTime() {
		return Utils.getCalendar(startTime);
	}
	
	public void setStartTime(Calendar startTime) {
		startTime.set(1970, 1, 1);
		this.startTime = Utils.getSqlTime(startTime);
	}
	
	public Calendar getEndTime() {
		return Utils.getCalendar(endTime);
	}
	
	public void setEndTime(Calendar endTime) {
		endTime.set(1970, 1, 1);
		this.startTime = Utils.getSqlTime(endTime);
	}	
}
