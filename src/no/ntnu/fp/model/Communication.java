package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class Communication {

	private static ServerResponse sendData(String function, Object...parameters)
	{		
		ServerRequest sr = new ServerRequest(function, parameters);
		return sr.sendRequest();
	}
	
	public static List<Message> getMessages(String email)
	{
		ServerResponse sr = sendData("getMessages", email);
		if(sr.isSuccess())
		{
			ArrayList<Message> messages = new ArrayList<Message>();
			for(int i = 0; i<sr.returnData.getChildElements().size(); i++){
				try {
					messages.add(XmlSerializer.toMessage(sr.returnData.getChildElements().get(i)));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return messages;
		}
		
		return new ArrayList<Message>();
	}
	
	public static List<Event> getEvents(String email)
	{
		ServerResponse sr = sendData("getEvents", XmlSerializer.createElement("email", email));
		if(sr.isSuccess())
		{
			ArrayList<Event> events = new ArrayList<Event>();
			for(int i = 0; i<sr.returnData.getChildElements().size(); i++){
				try {
					events.add(XmlSerializer.toEvent(sr.returnData.getChildElements().get(i)));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return events;
		}
		
		return new ArrayList<Event>();
	}
	
	public static boolean saveMessage(Message m)
	{
		ServerResponse sr = sendData("saveMessage", m);
		if(sr.isSuccess())
			m.setMid(XmlSerializer.readInt(sr.returnData, Message.PROPERTY_MID));
		return sr.isSuccess();
	
	}
	
	public static boolean saveEvent(Event e)
	{
		ServerResponse sr = sendData("saveEvent", e);
		if(sr.isSuccess())
			e.setEid(XmlSerializer.readInt(sr.returnData, Event.PROPERTY_EID));
		return sr.isSuccess();
	}
	
	public static boolean saveReservation(Reservation r)
	{
		ServerResponse sr = sendData("saveReservation", r);
		if(sr.isSuccess())
			r.setReservationID(XmlSerializer.readInt(sr.returnData, Event.PROPERTY_RESERVATIONID));
		return sr.isSuccess();
	}
	
	public static boolean deleteMessage(Message m)
	{
		return sendData("deleteMessage", m.getMid()).isSuccess();
	}
	
	public static boolean deleteEvent(Event e)
	{
		return sendData("deleteEvent", e.getEid()).isSuccess();
	}
	
	public static boolean deleteReservation(Reservation r)
	{
		return sendData("deleteReservation", r.getReservationID()).isSuccess();
	}
	
	public static boolean updateEvent(Event e)
	{
		return sendData("updateEvent", XmlSerializer.eventToXml(e)).isSuccess();
	}
	
	public static boolean updateReservation(Reservation r)
	{
		return sendData("updateReservation", r).isSuccess();
	}
	
	public static List<Room> getFreeRooms(Reservation r)
	{
		ServerResponse sr = sendData("getFreeRooms", r);
		if(sr.isSuccess())
		{
			ArrayList<Room> rooms = new ArrayList<Room>();
			for(int i = 0; i<sr.returnData.getChildElements().size(); i++){
				try {
					rooms.add(XmlSerializer.toRoom(sr.returnData.getChildElements().get(i)));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return rooms;
		}
		
		return new ArrayList<Room>();
	}
	
	public static boolean answerMessage(Message m, boolean answer)
	{
		ServerResponse sr = sendData("answerMessage", m.getMid(), answer);
		
		return sr.isSuccess();
	}
	
	public static List<Person> getAttendees()
	{
		throw new NotImplementedException();
	}
}
