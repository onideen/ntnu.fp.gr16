package no.ntnu.fp.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class Communication {

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		
		Date d = createDate(2011, 3, 15);
		Time t1 = new Time(14,00,00);
		Time t2 = new Time(16,00,00);
		Event e = new Event("Ultraviktig møte.", Event.Type.Appointment, "torkristianveld@hotmail.com", d, t1, t2, "Grupperom3");
		
		e.addAttendee("bolle@bool.com");
		e.addAttendee("Trollkjerringa@tull.no");
		e.addAttendee("svulstig@gmail.com");
		e.addAttendee("mothersday@monday.com");
		
		//System.out.println(saveEvent(e));
		
		/*for(Message p : getMessages("bolle@bool.com"))
		{
			System.out.println(p.getContent());
		}*/
		
		//for(Person p:c.getEmployees())
		//	System.out.println(p.getEmail());
		
		//e = c.getEvent(44);
		//c.updateEvent(e);
		
		//Message m = getMessage(34);
		//c.answerMessage(m, true);
		
//		for(Event r: getEvents("bolle@bool.com")){
//			System.out.println(r.getDescription() + " --" + r.getResponsible() + "-.--- id=" + r.getEid());
//		}
		
		//System.out.println(deleteEvent(82));
		
		login("pelle", "fdsfsd");
	}
	
	public static boolean login(String user, String password) throws SQLException {
		ServerResponse sr = sendData("login", user, password);
		if(sr.isSuccess())
		{
			System.out.println(sr.getParameters()[0]);
		}
		
		return false;
	}
	
	private static Date createDate(int y, int m, int d)
	{
		Calendar c = new GregorianCalendar(y,m-1,d);
		java.sql.Date dd = new Date(c.getTimeInMillis());
		return dd;
	}
	
	private static ServerResponse sendData(String function, Object...parameters)
	{		
		ServerRequest sr = new ServerRequest(function, parameters);
		ServerResponse response = sr.sendRequest();
		
		return response;
	}
	
	public static List<Person> getEmployees()
	{
		ServerResponse sr = sendData("getEmployees");
		try {
			return (List<Person>) ServerRequest.createObjectFromElement(sr.returnData.getChildElements().get(0));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<Message> getMessages(String email)
	{
		ServerResponse sr = sendData("getMessages", email);
		if(sr.isSuccess())
		{
			try {
				return (List<Message>) ServerRequest.createObjectFromElement(sr.returnData.getChildElements().get(0));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	
	public static List<Event> getEvents(String email)
	{
		ServerResponse sr = sendData("getEvents", email);
		if(sr.isSuccess())
		{
			try {
				return (List<Event>) ServerRequest.createObjectFromElement(sr.returnData.getChildElements().get(0));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return new ArrayList<Event>();
	}

	
	public static Event getEvent(int eid)
	{
		ServerResponse sr = sendData("getEvent", eid);
		if(sr.isSuccess())
			return (Event)sr.getParameters()[0];
		
		return null;
	}
	
	public static boolean saveMessage(Message m)
	{
		ServerResponse sr = sendData("saveMessage", m);
		if(sr.isSuccess())
			m.setMid((int)(Integer)sr.getParameters()[0]);
		return sr.isSuccess();
	
	}
	
	public static boolean saveEvent(Event e)
	{
		ServerResponse sr = sendData("saveEvent", e);
		if(sr.isSuccess())
			e.setEid((int)(Integer)sr.getParameters()[0]);
		return sr.isSuccess();
	}

	
	public static boolean deleteEvent(int eId)
	{
		return sendData("deleteEvent", eId).isSuccess();
	}
	
	public static boolean updateEvent(Event e)
	{
		return sendData("updateEvent", e).isSuccess();
	}
	
	public static List<Room> getFreeRooms(Reservation r)
	{
		ServerResponse sr = sendData("getFreeRooms", r);
		if(sr.isSuccess())
		{
			try {
				return (List<Room>) ServerRequest.createObjectFromElement(sr.returnData.getChildElements().get(0));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return new ArrayList<Room>();
	}
	
	public static boolean answerMessage(Message m, boolean answer)
	{
		ServerResponse sr = sendData("answerMessage", m.getMid(), answer);
		return sr.isSuccess();
	}
	
	public static List<Person> getAttendees(int eventID)
	{
		ServerResponse sr = sendData("getAttendees", eventID);
		if(sr.isSuccess())
		{
			try {
				return (List<Person>) ServerRequest.createObjectFromElement(sr.returnData.getChildElements().get(0));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return new ArrayList<Person>();
	}
}
