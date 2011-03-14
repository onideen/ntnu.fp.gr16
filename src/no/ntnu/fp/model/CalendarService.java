package no.ntnu.fp.model;

import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CalendarService {
	private void executeQuery(String s) {
		throw new NotImplementedException();
	}
	
	
	public void startListening() {
		throw new NotImplementedException();
	}
	
	
	public void stopListening() {
		throw new NotImplementedException();
	}
	
	
	private void deleteMessage(Message m) {
		String s = "DELETE FROM Melding WHERE id = " + m.getMid() + ";";
		executeQuery(s);
	}
	
	
	private void deleteEvent(Event e) {
		String s = "DELETE FROM Hendelse WHERE id = " + e.getEid() + ";";
		executeQuery(s);
	}
	
	
	private void updateEvent(Event e) {
		executeQuery("UPDATE Hendelse SET dato = '" + e.getDate() + "', " + 
				"starttid = '" + e.getStartTime() + "', " +
				"sluttid = '" + e.getEndTime() + "', " +
				"beskrivelse = '" + e.getDescription() + "'," +
				"type = '" + e.getType() + "', " + 
				"ansvarlig = '" + e.getResponsible() + "';");
	}
	
	
	public void saveEvent(Event e) {
		String s = "INSERT INTO Hendelse(dato, starttid, sluttid, beskrivelse, type, ansvarlig) values ('"
					+ e.getDate() + "', '" +  
					e.getStartTime() + "', '" +
					 e.getEndTime() + "', '" +
					 e.getDescription() + "', '" +
					e.getType().toString() + "', '" + 
					e.getResponsible() + "');";
		executeQuery(s);
	}
	
	
	public void saveMessage(Message m) {
		String s = "INSERT INTO Melding(innhold, tidsendt, type, mottaker, relatertmote) values('" 
				+ m.getContent() + "', '"
				+ m.getTimeSent() + "', '"
				+ m.getType() + "', '"
				+ m.getReceiver() + "', "
				+ m.getEvent() + ");";
		executeQuery(s);
	}
	
	
	public void answerMessage(Message m, boolean answer) {
		if(m.getType() == Message.Type.Invitation) {

			String s = "UPDATE Deltaker SET status = '" + Boolean.toString(answer) + "');";
			executeQuery(s);	
			
			if(answer == false) {
				
				Event relatedEvent = getEvent(m.getEvent());
				Person sender = getPerson(m.getReceiver());
				Person creator = getPerson(relatedEvent.getResponsible());
			
				List<Person> attendees = getAttendees(m.getEvent());
				for (Person person : attendees) {
					if(!sender.getEmail().equals(person.getEmail()) ) {
						Message message = new Message(sender.getName() + " har avslått møtet til " + creator.getName()
								+ " den " + relatedEvent.getDate() + " kl " + relatedEvent.getStartTime() + ".",
								Message.Type.Information, person.getEmail(), m.getEvent());
						saveMessage(message);
					}
				}
				
			}
		}
		
		String s = "DELETE FROM Melding WHERE id = " + m.getMid() + ";";
		executeQuery(s);
	}
	
	
	public List<Person> getAttendees(int eventId) {
		throw new NotImplementedException();
		//TODO IF YOU WANT TOO ^^
	}
	
	
	public Person getPerson(String email)
	{
		throw new NotImplementedException();
	    //TODO fix!! VERY VERY VERY ASAP MISTER X ^^
	}
	
	
	public Event getEvent(int eventId)
	{
		throw new NotImplementedException();
		//TODO FIX IT ASAP :)
	}
	
	
	public void saveReservation(Reservation r) {
		String s = "INSERT INTO Romreservasjon(hid, e-mail, navn) values(" 
				+ r.getEventID() + ", '"
				+ r.getResponsible() + "', '"
				+ r.getRoomName() + "');";

		executeQuery(s);
	}
	
	
	public void deleteReservation(Reservation r) {
		String s = "DELETE FROM romreservasjon WHERE id = " + r.getReservationID() + ";";
		executeQuery(s);	
		}
}
