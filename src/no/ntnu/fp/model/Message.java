package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Date;

public class Message {

	int mid;
	String content;
	Date timeSent;
	Type type;
	String receiver;
	
	private PropertyChangeSupport propChangeSupp;

	public final static String PROPERTY_MID = "mid";
	public final static String PROPERTY_CONTENT = "content";
	public final static String PROPERTY_TIMESENT = "timesent";
	public final static String PROPERTY_TYPE = "type";
	public final static String PROPERTY_RECEIVER = "receiver";
	
	public enum Type {
		Invitation, 
		Information
	}

	public final int getMid() {
		return mid;
	}

	public final void setMid(int mid) {
		int old = this.mid;
		this.mid = mid;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_MID, old, mid);
		propChangeSupp.firePropertyChange(event);
	}

	public final String getContent() {
		return content;
	}

	public final void setContent(String content) {
		Object old = this.content;
		this.content = content;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_CONTENT, old, content);
		propChangeSupp.firePropertyChange(event);
	}

	public final Date getTimeSent() {
		return timeSent;
	}

	public final void setTimeSent(Date timeSent) {
		Object old = this.timeSent;
		this.timeSent = timeSent;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_TIMESENT, old, timeSent);
		propChangeSupp.firePropertyChange(event);
	}

	public final Type getType() {
		return type;
	}

	public final void setType(Type type) {
		Object old = this.type;
		this.type = type;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_TYPE, old, type);
		propChangeSupp.firePropertyChange(event);
	}

	public final String getReceiver() {
		return receiver;
	}

	public final void setReceiver(String receiver) {
		Object old = this.receiver;
		this.receiver = receiver;
		PropertyChangeEvent event = new PropertyChangeEvent(this, PROPERTY_RECEIVER, old, receiver);
		propChangeSupp.firePropertyChange(event);
	}
}
