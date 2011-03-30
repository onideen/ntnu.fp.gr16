package no.ntnu.fp.model;

import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import no.ntnu.fp.gui.MainPanel;
import no.ntnu.fp.gui.WorkingForm;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

public class ServerRequest {

	String function;
	Element requestData;

        public String getXml()
        {
            return requestData.toXML();
        }

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
                            System.out.println("COULDNT CREATE XML!!!");
                            e1.printStackTrace();
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
                {
                    System.out.println("Object is null.");
                    return XmlSerializer.createDataXml("null",
					XmlSerializer.createElement("contents", ""));
                }

                System.out.println("Creating element from object: " + o.getClass());

		if (o.getClass().equals(Integer.TYPE) || o.getClass().equals(int.class)
				|| o.getClass().equals(Integer.class)) {
                        System.out.println("INT");
			return XmlSerializer.createDataXml("int", XmlSerializer
					.createElement("contents", (int) ((Integer) o)));
		}

		if (o.getClass().equals(java.util.ArrayList.class)) {
                        System.out.println("LIST");
			return XmlSerializer.createDataXml("list", XmlSerializer
					.createElementFromList("contents", (ArrayList) o));
		}

		if (o.getClass().equals(String.class)) {
                        System.out.println("STRING");
			return XmlSerializer.createDataXml("string",
					XmlSerializer.createElement("contents", (String) o));
		}

		if (o.getClass().equals(Message.class)) {
                        System.out.println("MESSAGE");
			return XmlSerializer.messageToXml((Message) o);
		}

		if (o.getClass().equals(Event.class)) {
                        System.out.println("EVENT");
			return XmlSerializer.eventToXml((Event) o);
		}

		if (o.getClass().equals(Room.class)) {
                        System.out.println("ROOM");
			return XmlSerializer.roomToXml((Room) o);
		}

		if (o.getClass().equals(Person.class)) {
                        System.out.println("PERSON");
			return XmlSerializer.personToXml((Person) o);
		}

		if (o.getClass().equals(Reservation.class)) {
                        System.out.println("RESERVATION");
			return XmlSerializer.reservationToXml((Reservation) o);
		}

		if (o.getClass().equals(Boolean.class)) {
                        System.out.println("BOOL");
			return XmlSerializer.createDataXml(
					"bool",
					XmlSerializer.createElement("contents",
							Boolean.toString((Boolean) o)));
		}

                System.out.println("NUTHIN!");
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
			throws ParseException, Exception {
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

                if(type.equals("null"))
                {
                    JOptionPane.showMessageDialog(null, "Feil ved serverkommunikasjon. Vennligst prøv igjen en gang i neste uke.","Kommunikasjonsfeil",JOptionPane.ERROR_MESSAGE);
                    return null;
                }

		throw new Exception(type + " is not supported.");
	}

	/**
	 * Sends this server request to the server.
	 * 
	 * @return
	 */
	public ServerResponse sendRequest() {

                setWorkingFormVisible(true);

                try {
			ServerConnection.connect();
                        String ans = ServerConnection.proxyRequest(requestData.toXML());

                        Document doc = new Builder().build(new StringReader(ans));

                        ServerResponse sr = new ServerResponse(doc.getRootElement());

                        setWorkingFormVisible(false);

                        return sr;

		} catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Feil ved serverkommunikasjon. Vennligst prøv igjen en gang i neste uke.","Kommunikasjonsfeil",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

                setWorkingFormVisible(false);

		return ServerResponse.createFail();
	}

        static WorkingForm workingForm = null;
        private static void setWorkingFormVisible(boolean value)
        {
            if(workingForm == null)
                workingForm = new WorkingForm();

            int x = MainPanel.getMainForm().getLocation().x;
            int y = MainPanel.getMainForm().getLocation().y;

            x += MainPanel.getMainForm().getWidth() / 2;
            y += MainPanel.getMainForm().getHeight() / 2;

            x -= workingForm.getWidth() / 2;
            y -= workingForm.getHeight() /2;

            workingForm.setLocation(x, y);
            workingForm.setVisible(value);
            workingForm.setTitle("Arbeider ... Vennligst vent.");
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

                boolean verbose = true;

		ArrayList<Object> objects = new ArrayList<Object>();

		for (int i = 0; i < requestData.getChildElements().size(); i++) {
			Element e = requestData.getChildElements().get(i);

			try {
                                Object o = createObjectFromElement(e);
                                if(verbose) System.out.println("Created an object of type " + o.getClass().toString() + " from " + e.toXML());
				objects.add(o);
			} catch (Exception e1) {
                                if(verbose) System.out.println("Failed to create object from " + e.toXML());
				e1.printStackTrace();
			}
		}

		return objects.toArray();
	}

}
