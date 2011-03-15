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
	
	public static Element createDataXml(String type, Element contents)
	{
		Element e = new Element("data");
		appendChildren(e, 
				createElement("type", type),
				contents
		);
		
		return e;
	}
	
	public static String getTypeFromDataXml(Element e)
	{
		return readString(e, "type");
	}
	
	public static Element getContentsFromDataXml(Element e)
	{
		return e.getFirstChildElement("contents");
	}
	
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
	
	public static Person toPerson(Element e)
	{
		e = getContentsFromDataXml(e);
		return new Person(
				readString(e, Person.NAME_PROPERTY_NAME),
				readString(e, Person.EMAIL_PROPERTY_NAME),
				readString(e, Person.PASSWORD_PROPERTY_NAME)
				);
	}
	
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
	
	public static Element eventToXml(Event m) {
		Element e = new Element("contents");
		
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
		
		return createDataXml("Event", e);
	}
	
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
	
	public static Element roomToXml(Room m) {
		Element e = new Element("contents");
		
		appendChildren(e,
				createElement(Room.PROPERTY_NAME, m.getName()),
				createElement(Room.PROPERTY_SIZE, m.getSize())
				);
		
		return createDataXml("Room", e);
	}
	
	public static Room toRoom(Element m) throws ParseException {
		m = getContentsFromDataXml(m);
		return new Room(
				readString(m, Room.PROPERTY_NAME),
				readInt(m, Room.PROPERTY_SIZE)
				);
	}
	
	public static String readString(Element m, String id)
	{
		System.out.println(m.toXML());
		System.out.println(id);
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
	
	public static java.sql.Date readDate(Element m, String id) throws ParseException
	{
		return new java.sql.Date(Long.parseLong(readString(m, id)));
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
		e.appendChild(Long.toString(value.getTime()));
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
	
	public static ArrayList readListFromElement(Element e) throws ParseException
	{
		ArrayList a = new ArrayList();
		
		for (int i = 0; i < e.getChildElements().size(); i++) {
			Element child = e.getChildElements().get(i);
			
			a.add(ServerRequest.createObjectFromElement(child));
		}
		
		return a;
	}
}