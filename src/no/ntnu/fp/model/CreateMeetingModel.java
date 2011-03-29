package no.ntnu.fp.model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CreateMeetingModel {

	private ServiceWrapper sw;
	
	private boolean newEvent = false;
	private boolean timeIsSet = true;
	private Event event;
	
	private Date calendar;
	private Time startTime;
	private Time endTime;
	private String description;
	private Room room;
	private List<Person> attendees;
	
	public CreateMeetingModel(Date date, java.sql.Time startTime, java.sql.Time endTime){
		this(new Event());
		calendar = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public CreateMeetingModel(Event event) {
		this.event = event;
	}
	
	public CreateMeetingModel() {
		this(new Event());
		newEvent = true;
		timeIsSet = false;
	}

	public Date getDate() {
		return calendar;
	}

	public void setDate(Date calendar) {
		this.calendar = calendar;
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

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDefaultValues() {
		if (newEvent) {
			if (!timeIsSet){
				calendar = new Date(new java.util.Date().getTime());
				startTime = new Time(10, 0, 0);
				endTime = new Time(11, 0, 0);
				event.setResponsible(Communication.LoggedInUserEmail);
			}
		}
		else {
			calendar = new Date(event.getDate().getTimeInMillis());
			startTime = new Time(event.getStartTime().getTimeInMillis());
			endTime = new Time(event.getEndTime().getTimeInMillis());
			room = event.getRoomObject();
			description = event.getDescription();
		}
	}
	
	public Time[] hentKlokkeslett() {
		Time[] comboChoose = new Time[24];
		for (int i = 0; i < 24; i++){
			comboChoose[i] = new Time(i, 0, 0);
		}
		return comboChoose;
		
	}
	
	public List<Room> getRooms() {
		
		List<Room> rooms = Communication.getFreeRooms(new Reservation(calendar, startTime, endTime));

		if (!newEvent && startTime == event.getStartTime().getTime() ){
			rooms.add(event.getRoomObject());
		}
		
		return rooms;
	}

	public List<Person> getAttendees() {
		return attendees;
	}

	public List<Person> getAllUsers() {
		if (newEvent) {
			return Communication.getEmployees();
		}
		else {
			List<Person> attendees = Communication.getAttendees(event.getEid()); 
			List<Person> employees = Communication.getEmployees();
			for (Person person : attendees) {
				employees.remove(person);
			}
			return employees;
		}
	}

	public void cleanAttendees() {
		attendees = new ArrayList<Person>();
	}

	public void addAttendee(Person person) {
		attendees.add(person);
	}

	public boolean isValidInput() {
		return true;
	}

	public void save() {
		if (newEvent){
			Communication.saveEvent(event);
		}
		else {
			Communication.updateEvent(event);
		}
	}
}
