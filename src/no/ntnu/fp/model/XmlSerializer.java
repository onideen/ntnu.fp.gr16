/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.model;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 * @author GR16
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlSerializer {
	
	/**
	 * 
	 * @param type Identifier for the type of contents. To be used when converting from XML to Object.
	 * @param contents The contents of this Datablock. Must be a XML block with root "contents".
	 * @return A DataXml block with root "data" and children "type" and "contents".
	 */
	public static Element createDataXml(String type, Element contents)
	{
		Element e = new Element("data");
		appendChildren(e, 
				createElement("type", type),
				contents
		);
		
		return e;
	}
	
	/**
	 * 
	 * @param e The DataXml block to extract data from.
	 * @return The type of the contents of this DataXml block.
	 */
	public static String getTypeFromDataXml(Element e)
	{
		return readString(e, "type");
	}
	
	/**
	 * 
	 * @param e The DataXml block to extract data from.
	 * @return The Xml block which is the contents of this DataXml block.
	 */
	public static Element getContentsFromDataXml(Element e)
	{
		return e.getFirstChildElement("contents");
	}
	
	/**
	 * Creates a DataXml block containing the given person.
	 * @param p
	 * @return
	 */
	public static Element personToXml(Person p)
	{
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Person.NAME_PROPERTY_NAME, p.getName()),
				createElement(Person.EMAIL_PROPERTY_NAME, p.getEmail()),
				createElement(Person.PASSWORD_PROPERTY_NAME, p.getPassword())
				);
		
		return createDataXml("Person", e);
	}
	
	/**
	 * Reads a person from the given DataXml block.
	 * @param p
	 * @return
	 */
	public static Person toPerson(Element e)
	{
		e = getContentsFromDataXml(e);
		return new Person(
				readString(e, Person.NAME_PROPERTY_NAME),
				readString(e, Person.EMAIL_PROPERTY_NAME),
				readString(e, Person.PASSWORD_PROPERTY_NAME)
				);
	}
	
	/**
	 * Creates a DataXml block containing the given message.
	 * @param p
	 * @return
	 */
	public static Element messageToXml(Message m) {
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Message.PROPERTY_MID, m.getMid()),
				createElement(Message.PROPERTY_CONTENT, m.getContent()),
				createElement(Message.PROPERTY_RECEIVER, m.getReceiver()),
				createElement(Message.PROPERTY_TIMESENT, m.getTimeSent()),
				createElement(Message.PROPERTY_TYPE, m.getType().toString()),
				createElement(Message.PROPERTY_EVENT, m.getEvent())

				);
		
		return createDataXml("Message", e);
	}

	/**
	 * Reads a message from the given DataXml block.
	 * @param p
	 * @return
	 */
	public static Message toMessage(Element m) throws ParseException {
		m = getContentsFromDataXml(m);
		return new Message(
				readInt(m, Message.PROPERTY_MID),
				readString(m, Message.PROPERTY_CONTENT),
				readTimestamp(m, Message.PROPERTY_TIMESENT),
				readString(m, Message.PROPERTY_TYPE),
				readString(m, Message.PROPERTY_RECEIVER),
				readInt(m, Message.PROPERTY_EVENT)
				);
	}
	
	/**
	 * Creates a DataXml block containing the given event.
	 * @param p
	 * @return
	 */
	public static Element eventToXml(Event m) {
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Event.PROPERTY_EID, m.getEid()),
				createElement(Event.PROPERTY_DATE, m.getDate().getTime()),
				createElement(Event.PROPERTY_DESCRIPTION, m.getDescription()),
				createElement(Event.PROPERTY_ENDTIME, m.getEndTime().getTime()),
				createElement(Event.PROPERTY_STARTTIME, m.getStartTime().getTime()),
				createElement(Event.PROPERTY_TYPE, m.getType().toString()),
				createElement(Event.PROPERTY_RESPONSIBLE, m.getResponsible()),
				createElement(Event.PROPERTY_ATTENDEE, m.getAttendees()),
				createElement(Room.PROPERTY_NAME, m.getRoom())
				);
		
		return createDataXml("Event", e);
	}

	/**
	 * Reads a event from the given DataXml block.
	 * @param p
	 * @return
	 */
	public static Event toEvent(Element m) throws ParseException {
		m = getContentsFromDataXml(m);
		return new Event(
				readInt(m, Event.PROPERTY_EID),
				readString(m, Event.PROPERTY_TYPE),
				readString(m, Event.PROPERTY_DESCRIPTION),
				readDate(m, Event.PROPERTY_DATE),
				readTime(m, Event.PROPERTY_STARTTIME),
				readTime(m, Event.PROPERTY_ENDTIME),
				readString(m, Event.PROPERTY_RESPONSIBLE),
				readStringList(m, Event.PROPERTY_ATTENDEE),
				readString(m, Room.PROPERTY_NAME)
				);
	}
	
	/**
	 * Creates a DataXml block containing the given reservation.
	 * @param p
	 * @return
	 */
	public static Element reservationToXml(Reservation m) {
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Event.PROPERTY_DATE, m.getDate()),
				createElement(Event.PROPERTY_ENDTIME, m.getEndTime()),
				createElement(Event.PROPERTY_STARTTIME, m.getStartTime())
				);
		
		return createDataXml("Reservation", e);
	}

	/**
	 * Reads a event from the given DataXml block.
	 * @param p
	 * @return
	 */
	public static Reservation toReservation(Element m) throws ParseException {
		m = getContentsFromDataXml(m);
		return new Reservation(
				readDate(m, Event.PROPERTY_DATE),
				readTime(m, Event.PROPERTY_STARTTIME),
				readTime(m, Event.PROPERTY_ENDTIME)
				);
	}
	
	/**
	 * Creates a DataXml block containing the given room.
	 * @param p
	 * @return
	 */
	public static Element roomToXml(Room m) {
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Room.PROPERTY_NAME, m.getName()),
				createElement(Room.PROPERTY_SIZE, m.getSize())
				);
		
		return createDataXml("Room", e);
	}

	/**
	 * Reads a room from the given DataXml block.
	 * @param p
	 * @return
	 */
	public static Room toRoom(Element m) throws ParseException {
		m = getContentsFromDataXml(m);
		return new Room(
				readString(m, Room.PROPERTY_NAME),
				readInt(m, Room.PROPERTY_SIZE)
				);
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, String value)
	{
		Element e = new Element(key);
		e.appendChild(value);
		return e;
	}

	/**
	 * Reads a string from the given XML element (< something > string < / something >).
	 * @param m
	 * @param id
	 * @return
	 */
	public static String readString(Element m, String id)
	{
		return m.getFirstChildElement(id).getValue();
	}

	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, int value)
	{
		Element e = new Element(key);
		e.appendChild(Integer.toString(value));
		return e;
	}


	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, long value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value));
		return e;
	}
	/**
	 * Reads an int from the given XML element (< something > string < / something >).
	 * @param m
	 * @param id
	 * @return
	 */
	public static int readInt(Element m, String id)
	{
		return Integer.parseInt(readString(m, id));
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, List<String> value)
	{
		Element e = new Element(key);
		
		String s = "";
		for(String p : value)
			s += (s=="" ? "" : "µ") + p;
		
		e.appendChild(s);
		return e;
	}

	/**
	 * Reads a list of strings from the given XML element.
	 * @param m
	 * @param id
	 * @return
	 */
	public static List<String> readStringList(Element m, String id)
	{
		ArrayList<String> a = new ArrayList<String>();
		for(String s : readString(m, id).split("µ"))
			a.add(s);
		return a;
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, java.sql.Date value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value.getTime()));
		return e;
	}

	/**
	 * Reads a date from the given XML element (< something > string < / something >).
	 * @param m
	 * @param id
	 * @return
	 */
	public static java.sql.Date readDate(Element m, String id) throws ParseException
	{
		return new java.sql.Date(Long.parseLong(readString(m, id)));
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, java.sql.Timestamp value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value.getTime()));
		return e;
	}

	/**
	 * Reads a timestamp from the given XML element (< something > string < / something >).
	 * @param m
	 * @param id
	 * @return
	 */
	public static Timestamp readTimestamp(Element m, String id) throws ParseException
	{
		String s = readString(m, id);
		long t = Long.parseLong(s);
		
		return new Timestamp(t);
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, java.sql.Time value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value.getTime()));
		return e;
	}

	/**
	 * Reads a time from the given XML element (< something > string < / something >).
	 * @param m
	 * @param id
	 * @return
	 */
	public static Time readTime(Element m, String id) throws ParseException
	{
		String s = readString(m, id);
		long t = Long.parseLong(s);
		
		return new Time(t);
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElement(String key, Element... children)
	{
		Element e = new Element(key);
		appendChildren(e, children);
		return e;
	}

	/**
	 * Appends all the given elements as children to the given parent node.
	 * @param m
	 * @param id
	 * @return
	 */
	public static void appendChildren(Element e, Element...elements)
	{
		for(Element child:elements)
			e.appendChild(child);
	}
	
	/**
	 * Creates a XML element with the given key and value (key is the contents of the tags).
	 * @param key
	 * @param value
	 * @return
	 */
	public static Element createElementFromList(String key, ArrayList list)
	{
		Element e = new Element(key);
		if(list.size()==0)
			return e;
		
		for(Object o : list)
			try {
				e.appendChild(ServerRequest.createElementFromObject(o));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		return e;
	}
	
	/**
	 * Reads an arraylist from the given XML element. The arraylist may be of any type defined in ServerRequest.
	 * @param e
	 * @return
	 * @throws ParseException
	 */
	public static ArrayList readListFromElement(Element e) throws ParseException
	{
		ArrayList a = new ArrayList();
		
		for (int i = 0; i < e.getChildElements().size(); i++) {
			Element child = e.getChildElements().get(i);
			
                     try {
                        a.add(ServerRequest.createObjectFromElement(child));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
		}
		
		return a;
	}
	
	
	// *******************************************
	// CODE BELOW THIS LINE IS NOT USED BY GR16
	// *******************************************
	
	static DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
	
	public Document toXml(Project aProject) {
		Element root = new Element("project");
		
		Iterator it = aProject.iterator();
		while (it.hasNext()) {
			Person aPerson = (Person)it.next();
			Element element = personToXml(aPerson);
			root.appendChild(element);
		}
		
		return new Document(root);
	}
	
	public Project toProject(Document xmlDocument) throws ParseException {
		Project aProject = new Project();
		Element groupElement = xmlDocument.getRootElement();
		Elements personElements = groupElement.getChildElements("person");
		
		for (int i = 0; i < personElements.size(); i++) {
			Element childElement = personElements.get(i);
			aProject.addPerson(assemblePerson(childElement));
		}
		
		return aProject;
	}

    public Person toPerson(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
	nu.xom.Builder parser = new nu.xom.Builder(false);
	nu.xom.Document doc = parser.build(xml, "");
	return assemblePerson(doc.getRootElement());
    }
	
	private Person assemblePerson(Element personElement) throws ParseException {
		String name = null, email = null;
		Date date = null;
		Element element = personElement.getFirstChildElement("name");
		if (element != null) {
			name = element.getValue();
		}
		element = personElement.getFirstChildElement("email");
		if (element != null) {
			email = element.getValue();
		}
		element = personElement.getFirstChildElement("date-of-birth");
		if (element != null) {
			date = parseDate(element.getValue());
		}
		return new Person(name, email, date);
	}
	
	/**
	 * TODO: handle this one to avoid duplicate code
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private Date parseDate(String date) throws ParseException {
		return (Date) format.parse(date);
	}
}