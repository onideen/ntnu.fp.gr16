/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

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
	
	DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
	
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
		return format.parse(date);
	}
	
	private Element messageToXml(Message m) {
		Element e = new Element("message");
		
		AppendChildren(e,
				CreateElement(Message.PROPERTY_MID, m.getMid()),
				CreateElement(Message.PROPERTY_CONTENT, m.getContent()),
				CreateElement(Message.PROPERTY_RECEIVER, m.getReceiver()),
				CreateElement(Message.PROPERTY_TIMESENT, m.getTimeSent()),
				CreateElement(Message.PROPERTY_TYPE, m.getType().ordinal())
				);
		
		return e;
	}
	
	private Message toMessage(Element m) throws ParseException {
		String name = null, email = null;
		Date date = null;
		Element element = m.getFirstChildElement("name");
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
	
	private Element eventToXml(Event m) {
		Element e = new Element("event");
		
		AppendChildren(e,
				CreateElement(Event.PROPERTY_EID, m.getEid()),
				CreateElement(Event.PROPERTY_DATE, m.getDate()),
				CreateElement(Event.PROPERTY_DESCRIPTION, m.getDescription()),
				CreateElement(Event.PROPERTY_ENDTIME, m.getEndTime()),
				CreateElement(Event.PROPERTY_RESERVATIONID, m.getReservationID()),
				CreateElement(Event.PROPERTY_STARTTIME, m.getStartTime()),
				CreateElement(Event.PROPERTY_TYPE, m.getType().ordinal())
				);
		
		return e;
	}
	
	private Element CreateElement(String key, String value)
	{
		Element e = new Element(key);
		e.appendChild(value);
		return e;
	}
	
	private Element CreateElement(String key, int value)
	{
		Element e = new Element(key);
		e.appendChild(Integer.toString(value));
		return e;
	}
	
	private Element CreateElement(String key, Date value)
	{
		Element e = new Element(key);
		e.appendChild(format.format(value));
		return e;
	}

	private void AppendChildren(Element e, Element...elements)
	{
		for(Element child:elements)
			e.appendChild(child);
	}
}

