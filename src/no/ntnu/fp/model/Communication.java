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

	private static ServerResponse sendData(String function, Element...parameters)
	{
		Element e = new Element(function);
		for(Element o : parameters)
			e.appendChild(o);
		
		//TODO: Do magic.
		throw new NotImplementedException();
	}
	
	public static List<Message> getMessages(String email)
	{
		ServerResponse sr = sendData("getMessages", XmlSerializer.createElement("email", email));
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
		ServerResponse sr = sendData("saveMessage", XmlSerializer.messageToXml(m));
		if(sr.isSuccess())
			m.setMid(XmlSerializer.readInt(sr.returnData, Message.PROPERTY_MID));
		return sr.isSuccess();
	
	}
	
	public static boolean saveEvent(Event e)
	{
		ServerResponse sr = sendData("saveEvent", XmlSerializer.eventToXml(e));
		if(sr.isSuccess())
			e.setEid(XmlSerializer.readInt(sr.returnData, Event.PROPERTY_EID));
		return sr.isSuccess();
	}
	
	public static boolean saveReservation(Reservation r)
	{
		ServerResponse sr = sendData("saveReservation", XmlSerializer.reservationToXml(r));
		if(sr.isSuccess())
			r.setReservationID(XmlSerializer.readInt(sr.returnData, Event.PROPERTY_RESERVATIONID));
		return sr.isSuccess();
	}
	
	public static boolean deleteMessage(Message m)
	{
		return sendData("deleteMessage", XmlSerializer.createElement("message", XmlSerializer.createElement(Message.PROPERTY_MID, m.getMid()))).isSuccess();
	}
	
	public static boolean deleteEvent(Event e)
	{
		return sendData("deleteEvent", XmlSerializer.createElement("event", XmlSerializer.createElement(Message.PROPERTY_MID, e.getEid()))).isSuccess();
	}
	
	public static boolean deleteReservation(Reservation r)
	{
		return sendData("deleteReservation", XmlSerializer.reservationToXml(r)).isSuccess();
	}
	
	public static boolean updateEvent(Event e)
	{
		return sendData("updateEvent", XmlSerializer.eventToXml(e)).isSuccess();
	}
	
	public static boolean updateReservation(Reservation r)
	{
		return sendData("updateReservation", XmlSerializer.reservationToXml(r)).isSuccess();
	}
	
	public static List<Room> getFreeRooms(Reservation r)
	{
		ServerResponse sr = sendData("getFreeRooms", XmlSerializer.reservationToXml(r));
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
		Element a = new Element("answer");
		XmlSerializer.appendChildren(a,
				XmlSerializer.createElement(Message.PROPERTY_MID, m.getMid()),
				XmlSerializer.createElement("answer",Boolean.toString(answer))
				);
		
		ServerResponse sr = sendData("answerMessage", a);
		
		return sr.isSuccess();
	}
	
	public static List<Person> getAttendees()
	{
		throw new NotImplementedException();
	}
}
