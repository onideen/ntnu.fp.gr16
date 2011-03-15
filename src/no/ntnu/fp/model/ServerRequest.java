package no.ntnu.fp.model;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class ServerRequest {

	String function;
	Element requestData;
	
	public ServerRequest(String function, Object...parameters){
		
		Element e = new Element(function);
		for(Object o : parameters)
			try {
				e.appendChild(createElementFromObject(o));
			} catch (Exception e1) {}
		
		this.function = function;
		requestData = e;
	}
	
	public static Element createElementFromObject(Object o) throws Exception
	{
		if(o==null)
			return XmlSerializer.createDataXml("null", XmlSerializer.createElement("contents", ""));
		
		if(o.getClass().equals(Integer.TYPE) || o.getClass().equals(int.class) || o.getClass().equals(Integer.class))
		{
			return XmlSerializer.createDataXml("int", XmlSerializer.createElement("contents", (int)((Integer)o)));
		}
		
		if(o.getClass().equals(java.util.ArrayList.class))
		{			
			return XmlSerializer.createDataXml("list", XmlSerializer.createElementFromList("contents", (ArrayList)o));
		}
		
		if(o.getClass().equals(String.class))
		{
			return XmlSerializer.createDataXml("string", XmlSerializer.createElement("contents", (String)o));
		}
		
		if(o.getClass().equals(Message.class))
		{
			return XmlSerializer.messageToXml((Message)o);
		}
		
		if(o.getClass().equals(Event.class))
		{
			return XmlSerializer.eventToXml((Event)o);
		}
		
		if(o.getClass().equals(Room.class))
		{
			return XmlSerializer.roomToXml((Room)o);
		}
		
		if(o.getClass().equals(Person.class))
		{
			return XmlSerializer.personToXml((Person)o);
		}
		
		throw new Exception(o.getClass() + " is not supported.");
	}
	
	public static Object createObjectFromElement(Element e) throws ParseException
	{
		String type = XmlSerializer.getTypeFromDataXml(e);
		Element xml = XmlSerializer.getContentsFromDataXml(e);
		
		if(type.equals("int"))
		{
			System.out.println("FEBFIEB     INT" + e.toXML());
		}
		
		if(type.equals("list"))
		{			
			return XmlSerializer.readListFromElement(xml);
		}
		
		if(type.equals("string"))
		{
			System.out.println("XNOIEOA    STRING" + e.toXML());
		}
		
		if(type.equals("Message"))
		{
			return XmlSerializer.toMessage(e);
		}
		
		if(type.equals("Event"))
		{
			return XmlSerializer.toEvent(e);
		}
		
		if(type.equals("Room"))
		{
			return XmlSerializer.toRoom(e);
		}
		
		if(type.equals("Person"))
		{
			return XmlSerializer.toPerson(e);
		}
		
		throw new NotImplementedException();
	}
	
	public ServerResponse sendRequest()
	{
		//TODO: Putt Aleksander her.
		CalendarService c = new CalendarService();
		
		try {
			return c.receiveData(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getFunction() {
		return function;
	}

	public Object[] getParameters() {
		
		ArrayList<Object> objects = new ArrayList<Object>();
		
		for (int i = 0; i < requestData.getChildElements().size(); i++) {
			Element e = requestData.getChildElements().get(i);
			
			try {
				objects.add(createObjectFromElement(e));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		return objects.toArray();
	}
	
}
