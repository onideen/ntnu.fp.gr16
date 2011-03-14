package no.ntnu.fp.model;

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
				e.appendChild(createElement(o));
			} catch (Exception e1) {}
		
		this.function = function;
		requestData = e;
	}
	
	private Element createElement(Object o) throws Exception
	{
		if(o.getClass().equals(Integer.TYPE))
		{
			return XmlSerializer.createElement("int", (int)((Integer)o));
		}
		
		if(o.getClass().equals(String.class))
		{
			return XmlSerializer.createElement("string", (String)o);
		}
		
		if(o.getClass().equals(Message.class))
		{
			return XmlSerializer.createElement("Message", XmlSerializer.messageToXml((Message)o));
		}
		
		if(o.getClass().equals(Event.class))
		{
			return XmlSerializer.createElement("Event", XmlSerializer.eventToXml((Event)o));
		}
		
		if(o.getClass().equals(Reservation.class))
		{
			return XmlSerializer.createElement("Reservation", XmlSerializer.reservationToXml((Reservation)o));
		}
		
		if(o.getClass().equals(Room.class))
		{
			return XmlSerializer.createElement("Room", XmlSerializer.roomToXml((Room)o));
		}
		
		throw new Exception(o.getClass() + " is not supported.");
	}
	
	public ServerResponse sendRequest()
	{
		//TODO: Magic
		throw new NotImplementedException();
	}
	
	public String getFunction() {
		return function;
	}

	public Object[] getParameters() {
		throw new NotImplementedException();
	}
	
}
