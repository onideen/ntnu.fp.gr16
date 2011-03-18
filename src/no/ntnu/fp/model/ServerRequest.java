package no.ntnu.fp.model;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import no.ntnu.fp.net.co.ConnectionImpl;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class ServerRequest {

	String function;
	Element requestData;

	/**
	 * Creates a server request to the given function with the given parameters
	 * 
	 * @param function
	 * @param parameters
	 */
	public ServerRequest(String function, Object... parameters) {

		Element e = new Element(function);
		for (Object o : parameters)
			try {
				e.appendChild(createElementFromObject(o));
			} catch (Exception e1) {
			}

		this.function = function;
		requestData = e;
	}

        public ServerRequest(Element element) {
            this.function = element.getQualifiedName();
            this.requestData = element;
        }

	/**
	 * Converts an object to an Xml element.
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static Element createElementFromObject(Object o) throws Exception {
		if (o == null)
			return XmlSerializer.createDataXml("null",
					XmlSerializer.createElement("contents", ""));

		if (o.getClass().equals(Integer.TYPE) || o.getClass().equals(int.class)
				|| o.getClass().equals(Integer.class)) {
			return XmlSerializer.createDataXml("int", XmlSerializer
					.createElement("contents", (int) ((Integer) o)));
		}

		if (o.getClass().equals(java.util.ArrayList.class)) {
			return XmlSerializer.createDataXml("list", XmlSerializer
					.createElementFromList("contents", (ArrayList) o));
		}

		if (o.getClass().equals(String.class)) {
			return XmlSerializer.createDataXml("string",
					XmlSerializer.createElement("contents", (String) o));
		}

		if (o.getClass().equals(Message.class)) {
			return XmlSerializer.messageToXml((Message) o);
		}

		if (o.getClass().equals(Event.class)) {
			return XmlSerializer.eventToXml((Event) o);
		}

		if (o.getClass().equals(Room.class)) {
			return XmlSerializer.roomToXml((Room) o);
		}

		if (o.getClass().equals(Person.class)) {
			return XmlSerializer.personToXml((Person) o);
		}

		if (o.getClass().equals(Reservation.class)) {
			return XmlSerializer.reservationToXml((Reservation) o);
		}

		if (o.getClass().equals(Boolean.class)) {
			return XmlSerializer.createDataXml(
					"bool",
					XmlSerializer.createElement("contents",
							Boolean.toString((Boolean) o)));
		}

		throw new Exception(o.getClass() + " is not supported.");
	}

	/**
	 * Converts a Xml element to an object.
	 * 
	 * @param e
	 * @return
	 * @throws ParseException
	 */
	public static Object createObjectFromElement(Element e)
			throws ParseException {
		String type = XmlSerializer.getTypeFromDataXml(e);
		Element xml = XmlSerializer.getContentsFromDataXml(e);

		if (type.equals("int")) {
			return XmlSerializer.readInt(e, "contents");
		}

		if (type.equals("bool")) {
			return XmlSerializer.readString(e, "contents").equals("true");
		}

		if (type.equals("list")) {
			return XmlSerializer.readListFromElement(xml);
		}

		if (type.equals("string")) {
			return XmlSerializer.readString(e, "contents");
		}

		if (type.equals("Message")) {
			return XmlSerializer.toMessage(e);
		}

		if (type.equals("Event")) {
			return XmlSerializer.toEvent(e);
		}

		if (type.equals("Room")) {
			return XmlSerializer.toRoom(e);
		}

		if (type.equals("Person")) {
			return XmlSerializer.toPerson(e);
		}

		if (type.equals("Reservation")) {
			return XmlSerializer.toReservation(e);
		}

		throw new NotImplementedException();
	}

	/**
	 * Sends this server request to the server.
	 * 
	 * @return
	 */
	public ServerResponse sendRequest() {

                //CalendarService c = new CalendarService();

		/*try {
			return c.receiveData(this);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

                try {

			ServerConnection.connect();
                        String ans = ServerConnection.proxyRequest(requestData.toXML());

                        Document doc = new Builder().build(new StringReader(ans));

                        ServerResponse sr = new ServerResponse(doc.getRootElement());
                        return sr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @return This server request's function name.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Returns this server request's parameters.
	 * 
	 * @return A list of objects containing the parameters.
	 */
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
