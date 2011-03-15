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
 * @author tho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlSerializer {
	
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
	
	private Element personToXml(Person aPerson) {
		Element element = new Element("person");
		Element name = new Element("name");
		name.appendChild(aPerson.getName());
		Element email = new Element("email");
		email.appendChild(aPerson.getEmail());
		Element dateOfBirth = new Element("date-of-birth");
		dateOfBirth.appendChild(format.format(aPerson.getDateOfBirth()));
		element.appendChild(name);
		element.appendChild(email);
		element.appendChild(dateOfBirth);
		return element;
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
	
	public static Element messageToXml(Message m) {
		Element e = new Element("message");
		
		appendChildren(e,
				createElement(Message.PROPERTY_MID, m.getMid()),
				createElement(Message.PROPERTY_CONTENT, m.getContent()),
				createElement(Message.PROPERTY_RECEIVER, m.getReceiver()),
				createElement(Message.PROPERTY_TIMESENT, m.getTimeSent()),
				createElement(Message.PROPERTY_TYPE, m.getType().toString()),
				createElement(Message.PROPERTY_EVENT, m.getEvent())

				);
		
		return e;
	}
	
	public static Message toMessage(Element m) throws ParseException {
		return new Message(
				readInt(m, Message.PROPERTY_MID),
				readString(m, Message.PROPERTY_CONTENT),
				readTimestamp(m, Message.PROPERTY_TIMESENT),
				readString(m, Message.PROPERTY_TYPE),
				readString(m, Message.PROPERTY_RECEIVER),
				readInt(m, Message.PROPERTY_EVENT)
				);
	}
	
	public static Element eventToXml(Event m) {
		Element e = new Element("event");
		
		appendChildren(e,
				createElement(Event.PROPERTY_EID, m.getEid()),
				createElement(Event.PROPERTY_DATE, m.getDate()),
				createElement(Event.PROPERTY_DESCRIPTION, m.getDescription()),
				createElement(Event.PROPERTY_ENDTIME, m.getEndTime()),
				createElement(Event.PROPERTY_STARTTIME, m.getStartTime()),
				createElement(Event.PROPERTY_TYPE, m.getType().toString()),
				createElement(Event.PROPERTY_RESPONSIBLE, m.getResponsible()),
				createElement(Event.PROPERTY_ATTENDEE, m.getAttendees()),
				createElement(Room.PROPERTY_NAME, m.getRoom())
				);
		
		return e;
	}
	
	public static Event toEvent(Element m) throws ParseException {
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
	
	public static Element reservationToXml(Reservation m) {
		Element e = new Element("event");
		
		appendChildren(e,
				createElement(Event.PROPERTY_EID, m.getEventID()),
				createElement(Event.PROPERTY_RESPONSIBLE, m.getResponsible()),
				createElement(Room.PROPERTY_NAME, m.getRoomName()),
				createElement(Event.PROPERTY_DATE, m.getDate()),
				createElement(Event.PROPERTY_STARTTIME, m.getStartTime()),
				createElement(Event.PROPERTY_ENDTIME, m.getEndTime())
				);
		
		return e;
	}

	public static Reservation toReservation(Element m) throws ParseException {
		return new Reservation(
				readInt(m,Event.PROPERTY_EID),
				readString(m, Event.PROPERTY_RESPONSIBLE),
				readString(m, Room.PROPERTY_NAME),
				readDate(m, Event.PROPERTY_DATE),
				readDate(m, Event.PROPERTY_STARTTIME),
				readDate(m, Event.PROPERTY_ENDTIME)
				);
	}
	
	public static Element roomToXml(Room m) {
		Element e = new Element("event");
		
		appendChildren(e,
				createElement(Room.PROPERTY_NAME, m.getName()),
				createElement(Room.PROPERTY_SIZE, m.getSize())
				);
		
		return e;
	}
	
	public static Room toRoom(Element m) throws ParseException {
		return new Room(
				readString(m, Room.PROPERTY_NAME),
				readInt(m, Room.PROPERTY_SIZE)
				);
	}
	
	public static String readString(Element m, String id)
	{
		return m.getFirstChildElement(id).getValue();
	}
	
	public static int readInt(Element m, String id)
	{
		return Integer.parseInt(readString(m, id));
	}
	
	public static List<String> readStringList(Element m, String id)
	{
		ArrayList<String> a = new ArrayList<String>();
		for(String s : readString(m, id).split("µ"))
			a.add(s);
		return a;
	}
	
	public static Date readDate(Element m, String id) throws ParseException
	{
		return (Date) format.parse(readString(m, id));
	}
	
	public static Timestamp readTimestamp(Element m, String id) throws ParseException
	{
		String s = readString(m, id);
		long t = Long.parseLong(s);
		
		return new Timestamp(t);
	}
	
	public static Time readTime(Element m, String id) throws ParseException
	{
		String s = readString(m, id);
		long t = Long.parseLong(s);
		
		return new Time(t);
	}
	
	public static Element createElement(String key, String value)
	{
		Element e = new Element(key);
		e.appendChild(value);
		return e;
	}
	
	public static Element createElement(String key, List<String> value)
	{
		Element e = new Element(key);
		
		String s = "";
		for(String p : value)
			s += (s=="" ? "" : "µ") + p;
		
		e.appendChild(s);
		return e;
	}
	
	public static Element createElement(String key, int value)
	{
		Element e = new Element(key);
		e.appendChild(Integer.toString(value));
		return e;
	}
	
	public static Element createElement(String key, java.sql.Date value)
	{
		Element e = new Element(key);
		e.appendChild(format.format(value));
		return e;
	}
	
	public static Element createElement(String key, java.sql.Timestamp value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value.getTime()));
		return e;
	}
	
	public static Element createElement(String key, java.sql.Time value)
	{
		Element e = new Element(key);
		e.appendChild(Long.toString(value.getTime()));
		return e;
	}
	
	public static Element createElement(String key, Element... children)
	{
		Element e = new Element(key);
		appendChildren(e, children);
		return e;
	}

	public static void appendChildren(Element e, Element...elements)
	{
		for(Element child:elements)
			e.appendChild(child);
	}
}