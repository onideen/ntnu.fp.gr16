package no.ntnu.fp.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.lang.reflect.*;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CalendarService {

	public ServerResponse receiveData(ServerRequest sr){
		
		for (Method method : this.getClass().getMethods()) {
			if (method.getName().equals(sr.getFunction())) {
				try {
					Object o = method.invoke(this, sr.getParameters());
					
					Element returnData = new Element("data");
					XmlSerializer.appendChildren(returnData,
							XmlSerializer.createElement("success", "true"),
							XmlSerializer.createElement("returnData", ServerRequest.createElementFromObject(o))
							);
					return new ServerResponse(returnData);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null; 
	}
	
	private static Connection getConnection()
	{
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			String userName = "erlendd_felles";
	        String password = "fpfpfp";
	        String url = "jdbc:mysql://mydb11.surftown.no/erlendd_qamerat";
	        Connection conn = DriverManager.getConnection (url, userName, password);
	        
	        return conn;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	public ResultSet executeQuery(String s)
	{		
		try {
            Connection conn = getConnection();
			
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery(s);
			
			conn.close();
			
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void executeUpdate(String s)
	{		
		try {
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			String userName = "erlendd_felles";
            String password = "fpfpfp";
            String url = "jdbc:mysql://mydb11.surftown.no/erlendd_qamerat";
            Connection conn = DriverManager.getConnection (url, userName, password);
			
			Statement q = conn.createStatement();
			q.executeUpdate(s);
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startListening() {
		throw new NotImplementedException();
	}

	public void stopListening() {
		throw new NotImplementedException();
	}

	public List<Person> getEmployees() {
		
		try {
			
			Connection c = getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT `e-mail` FROM Person;");
			
			List<Person> persons = new ArrayList<Person>();

			while(rs.next())
				persons.add(getPerson(rs.getString("e-mail")));
			
			return persons;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public void deleteMessage(Message m) {
		String s = "DELETE FROM Melding WHERE id = " + m.getMid() + ";";
		executeUpdate(s);
	}

	public void deleteEvent(Event e) {
		String s = "DELETE FROM Hendelse WHERE id = " + e.getEid() + ";";
		executeUpdate(s);
	}

	public void updateEvent(Event e) {

		try {
			
			Connection c = getConnection();
			PreparedStatement p = c.prepareStatement("UPDATE Hendelse SET dato = ?, starttid = ?, sluttid = ?, " + 
					"beskrivelse = ?, type = ?, ansvarlig = ?, reservertrom = ? WHERE id = " + e.getEid() + ";");
			
			p.setDate(1, e.getDate());
			p.setTime(2, e.getStartTime());
			p.setTime(3, e.getEndTime());
			p.setString(4, e.getDescription());
			p.setString(5, e.getType().toString());
			p.setString(6, e.getResponsible());
			p.setString(7, e.getRoom());
			
			p.executeUpdate();

			p = c.prepareStatement("DELETE FROM Melding WHERE relatertmote = ?;");
			p.setInt(1, e.getEid());
			p.executeUpdate();
			
			for(String attendee : e.getAttendees())
			{
				Person boss = getPerson(e.getResponsible());
				Message m = new Message(boss.getName() + " har endret møtet. Møtet er nå " + e.getDateString() + ". Møtet gjelder: " + e.getDescription(), Message.Type.Invitation, attendee, e.getEid());
				saveMessage(m);
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
	}

	public int saveEvent(Event e) {
		
		try {
			
			Connection c = getConnection();
			PreparedStatement p = c.prepareStatement("INSERT INTO Hendelse(dato, starttid, sluttid, beskrivelse, type, ansvarlig, reservertrom) " + 
					"VALUES(?, ?, ?, ?, ?, ?, ?);");
			
			p.setDate(1, e.getDate());
			p.setTime(2, e.getStartTime());
			p.setTime(3, e.getEndTime());
			p.setString(4, e.getDescription());
			p.setString(5, e.getType().toString());
			p.setString(6, e.getResponsible());
			p.setString(7, e.getRoom());
			
			p.executeUpdate();
			
			p = c.prepareStatement("SELECT id FROM Hendelse WHERE dato = ? AND starttid = ? AND sluttid = ? AND ansvarlig = ? AND beskrivelse = ?;");

			p.setDate(1, e.getDate());
			p.setTime(2, e.getStartTime());
			p.setTime(3, e.getEndTime());
			p.setString(4, e.getResponsible());
			p.setString(5, e.getDescription());
			
			ResultSet rs = p.executeQuery();
			if(rs.next())
				e.setEid(rs.getInt(1));
			
			for(String attendee : e.getAttendees())
			{
				p = c.prepareStatement("INSERT INTO Deltaker(`hid`, `e-mail`, `status`) VALUES(?, ?, ?);");
				p.setInt(1, e.getEid());
				p.setString(2, attendee);
				p.setString(3, "venter");
				
				p.executeUpdate();
				
				Person boss = getPerson(e.getResponsible());
				Message m = new Message(boss.getName() + " har kalt inn til møte " + e.getDateString() + ". Møtet gjelder: " + e.getDescription(), Message.Type.Invitation, attendee, e.getEid());
				saveMessage(m);
			}
			
			p.close();
			
			return e.getEid();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return -1;
	}

	public void saveMessage(Message m) {
		
		try {
			
			Connection c = getConnection();
			PreparedStatement p = c.prepareStatement("INSERT INTO Melding(innhold, tidsendt, type, mottaker, relatertmote) " + 
					"VALUES(?, ?, ?, ?, ?);");
			
			Calendar cal = Calendar.getInstance();
			Timestamp t = new Timestamp(cal.getTimeInMillis());
			
			p.setString(1, m.getContent());
			p.setTimestamp(2, t);
			p.setString(3, m.getType().toString());
			p.setString(4, m.getReceiver());
			p.setInt(5, m.getEvent());

			p.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void answerMessage(Message m, boolean answer) {
		
		if (m.getType() == Message.Type.Invitation) {

			String s = "UPDATE Deltaker SET status = '"
					+ Boolean.toString(answer) + "' WHERE `e-mail` = '" + m.getReceiver() + "';";
			executeUpdate(s);

			if (answer == false) {

				Event relatedEvent = getEvent(m.getEvent());
				Person sender = getPerson(m.getReceiver());
				Person creator = getPerson(relatedEvent.getResponsible());

				List<Person> attendees = getAttendees(m.getEvent());
				attendees.add(creator);
				for (Person person : attendees) {
					if (!sender.getEmail().equals(person.getEmail())) {
						Message message = new Message(sender.getName()
								+ " har avslått møtet til " + creator.getName()
								+ " den " + relatedEvent.getDate() + " kl "
								+ relatedEvent.getStartTime() + ".",
								Message.Type.Information, person.getEmail(),
								m.getEvent());
						saveMessage(message);
					}
				}

			}
		}

		String s = "DELETE FROM Melding WHERE id = " + m.getMid() + ";";
		executeUpdate(s);
	}

	public List<Person> getAttendees(int eventId) {

		try {
			
			Connection c = getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT `e-mail` FROM Deltaker WHERE `hid` = " + eventId + ";");
			
			List<Person> persons = new ArrayList<Person>();
			
			while(rs.next())
				persons.add(getPerson(rs.getString("e-mail")));
			
			return persons;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public Person getPerson(String email) {
		
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Person WHERE `e-mail` = '" + email + "';");
			
			if(rs.next())
			{
				Person p = new Person();
				p.setEmail(rs.getString("e-mail"));
				p.setName(rs.getString("navn"));
				p.setPassword(rs.getString("passord"));
				
				return p;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Message getMessage(int id) {
		
		try {
			
			Connection c = getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Melding WHERE id = " + id + ";");
			
			if(rs.next())
			{
				Message m = new Message(
						rs.getString("innhold"),
						Message.Type.valueOf(rs.getString("type")),
						rs.getString("mottaker"),
						rs.getInt("relatertmote")
						);
				m.setMid(rs.getInt("id"));
				m.setTimeSent(rs.getTimestamp("tidsendt"));
				
				return m;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Event getEvent(int eventId) {

		try {
			
			Connection c = getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Hendelse WHERE `id` = " + eventId + ";");
			
			if(rs.next())
			{
				Event e = new Event(
						rs.getString("beskrivelse"), 
						Event.Type.valueOf(rs.getString("type")), 
						rs.getString("ansvarlig"), 
						rs.getDate("dato"), 
						rs.getTime("starttid"), 
						rs.getTime("sluttid"),
						rs.getString("reservertrom")
						);
				e.setEid(eventId);
				
				for(Person p : getAttendees(eventId))
					e.addAttendee(p.getEmail());

				
				s.close();
				
				return e;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public void saveReservation(Reservation r) {
		String s = "INSERT INTO Romreservasjon(hid, e-mail, navn) values("
				+ r.getEventID() + ", '" + r.getResponsible() + "', '"
				+ r.getRoomName() + "');";

		executeUpdate(s);
	}

	public void deleteReservation(Reservation r) {
		String s = "DELETE FROM Romreservasjon WHERE id = "
				+ r.getReservationID() + ";";
		executeUpdate(s);
	}
}
