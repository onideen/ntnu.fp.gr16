package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Timestamp;;

public class Message {

	int mid;
	String content;
	Timestamp timeSent;
	Type type;
	String receiver;
	int event;
	
	private PropertyChangeSupport propChangeSupp;

	public final static String PROPERTY_MID = "mid";
	public final static String PROPERTY_CONTENT = "content";
	public final static String PROPERTY_TIMESENT = "timesent";
	public final static String PROPERTY_TYPE = "type";
	public final static String PROPERTY_RECEIVER = "receiver";
	public final static String PROPERTY_EVENT = "event";

	
	public enum Type {
		Invitation, 
		Information
	}
	
	public Message(int mid, String content, Timestamp timeSent, String type, String receiver, int event)
	{
		this.mid=mid;
		this.content=content;
		this.timeSent=timeSent;
		this.type=Type.valueOf(type);
		this.receiver=receiver;
		this.event=event;
		
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	
	public Message(String content, Type type, String receiver, int event)
	{
		this.content=content;
		this.type=type;
		this.receiver=receiver;
		this.event=event;
		
		propChangeSupp = new PropertyChangeSupport(this);
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		int old = this.mid;
		this.mid = mid;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_MID, old, mid);
		propChangeSupp.firePropertyChange(event);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		Object old = this.content;
		this.content = content;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_CONTENT, old, content);
		propChangeSupp.firePropertyChange(event);
	}

	public Timestamp getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(Timestamp timeSent) {
		Object old = this.timeSent;
		this.timeSent = timeSent;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_TIMESENT, old, timeSent);
		propChangeSupp.firePropertyChange(event);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		Object old = this.type;
		this.type = type;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_TYPE, old, type);
		propChangeSupp.firePropertyChange(event);
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		Object old = this.receiver;
		this.receiver = receiver;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_RECEIVER, old, receiver);
		propChangeSupp.firePropertyChange(event);
	}
	
	public int getEvent() {
		return event;
	}
	
	public void setEvent(int event) {
		int old = this.event;
		this.event = event;
		PropertyChangeEvent pcEvent = new PropertyChangeEvent(this, PROPERTY_EVENT, old, event);
		propChangeSupp.firePropertyChange(pcEvent);
	}
}
