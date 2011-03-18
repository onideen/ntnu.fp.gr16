package no.ntnu.fp.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.fp.net.co.ConnectionImpl;
import no.ntnu.fp.net.co.ReceiveConnectionWorker;
import no.ntnu.fp.net.co.ReceiveConnectionWorker.ConnectionListener;
import no.ntnu.fp.net.co.ReceiveMessageWorker;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

public class CalendarService implements ConnectionListener,
        ReceiveMessageWorker.MessageListener {

    public ServerResponse receiveData(ServerRequest sr) throws FileNotFoundException {

        boolean foundMethod = false;
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals(sr.getFunction())) {
                foundMethod = true;

                try {
                    Object o = null;

                    try {
                        o=method.invoke(this, sr.getParameters());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Element returnData = new Element("data");
                    XmlSerializer.appendChildren(returnData, XmlSerializer.createElement("success", "true"), XmlSerializer.createElement("returnData",
                            ServerRequest.createElementFromObject(o)));

                    return new ServerResponse(returnData);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(sr.getFunction());
                }
            }
        }

        if(!foundMethod)
            throw new FileNotFoundException("The method '" + sr.getFunction() + "' could not be found.");

        return null;
    }

    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String userName = "fpg16";
            String password = "fpfpfp";
            String url = "jdbc:mysql://127.0.0.1/fellesprosjekt";
            Connection conn = DriverManager.getConnection(url, userName,
                    password);

            return conn;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public ResultSet executeQuery(String s) {
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

    public void executeUpdate(String s) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String userName = "erlendd_felles";
            String password = "fpfpfp";
            String url = "jdbc:mysql://mydb11.surftown.no/erlendd_qamerat";
            Connection conn = DriverManager.getConnection(url, userName,
                    password);

            Statement q = conn.createStatement();
            q.executeUpdate(s);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ReceiveConnectionWorker receiver;
    Set<no.ntnu.fp.net.co.Connection> connections = Collections.synchronizedSet(new HashSet<no.ntnu.fp.net.co.Connection>());

    public void startListening() {
        try {
            no.ntnu.fp.net.co.Connection conn = new ConnectionImpl(1010);
            receiver = new ReceiveConnectionWorker(conn, this);
            receiver.start();
        } catch (Throwable t) {
            startListening();
        }
    }

    public void connectionClosed(no.ntnu.fp.net.co.Connection conn) {
        try {
            conn.close();
            connections.remove(conn);
        } catch (IOException ex) {
            Logger.getLogger(CalendarService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void connectionReceived(no.ntnu.fp.net.co.Connection connection) {
        ReceiveMessageWorker worker = new ReceiveMessageWorker(connection);
        worker.addMessageListener(this);
        worker.start();
        connections.add(connection);
    }

    public void messageReceived(String message, no.ntnu.fp.net.co.Connection connection) {
        try {
            Document doc = new Builder().build(new StringReader(message));

            ServerRequest req = new ServerRequest(doc.getRootElement());
            ServerResponse resp = receiveData(req);

            connection.send(resp.getXmlForSending());
        } catch (ParsingException ex) {
            Logger.getLogger(CalendarService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CalendarService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopListening() {
        receiver.stopRunning();
    }

    public List<Person> getEmployees() {

        try {

            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT `e-mail` FROM Person;");

            List<Person> persons = new ArrayList<Person>();

            while (rs.next()) {
                persons.add(getPerson(rs.getString("e-mail")));
            }

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

    public void deleteEvent(int eId) throws SQLException {
        Event e = getEvent(eId);
        Connection c = getConnection();

        PreparedStatement p = c.prepareStatement("DELETE FROM Melding WHERE relatertmote = ?;");
        p.setInt(1, e.getEid());
        p.executeUpdate();

        List<String> attendees = e.getAttendees();

        String s = "DELETE FROM Hendelse WHERE id = " + eId + ";";
        executeUpdate(s);

        for (String attendee : attendees) {
            Message m = new Message(e.getResponsible()
                    + " har avlyst møtet den: " + e.getDateString(),
                    Message.Type.Information, attendee, eId);
            saveMessage(m);
        }
    }

    public void updateEvent(Event e) {

        try {

            Connection c = getConnection();
            PreparedStatement p = c.prepareStatement("UPDATE Hendelse SET dato = ?, starttid = ?, sluttid = ?, "
                    + "beskrivelse = ?, type = ?, ansvarlig = ?, reservertrom = ? WHERE id = "
                    + e.getEid() + ";");

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

            for (String attendee : e.getAttendees()) {
                Person boss = getPerson(e.getResponsible());
                Message m = new Message(boss.getName()
                        + " har endret møtet. Møtet er nå " + e.getDateString()
                        + ". Møtet gjelder: " + e.getDescription(),
                        Message.Type.Invitation, attendee, e.getEid());
                saveMessage(m);
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    public int saveEvent(Event e) {

        try {

            Connection c = getConnection();
            PreparedStatement p = c.prepareStatement("INSERT INTO Hendelse(dato, starttid, sluttid, beskrivelse, type, ansvarlig, reservertrom) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?);");

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
            if (rs.next()) {
                e.setEid(rs.getInt(1));
            }

            for (String attendee : e.getAttendees()) {
                p = c.prepareStatement("INSERT INTO Deltaker(`hid`, `e-mail`, `status`) VALUES(?, ?, ?);");
                p.setInt(1, e.getEid());
                p.setString(2, attendee);
                p.setString(3, "venter");

                p.executeUpdate();

                Person boss = getPerson(e.getResponsible());
                Message m = new Message(boss.getName()
                        + " har kalt inn til møte " + e.getDateString()
                        + ". Møtet gjelder: " + e.getDescription(),
                        Message.Type.Invitation, attendee, e.getEid());
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
            PreparedStatement p = c.prepareStatement("INSERT INTO Melding(innhold, tidsendt, type, mottaker, relatertmote) "
                    + "VALUES(?, ?, ?, ?, ?);");

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
                    + Boolean.toString(answer) + "' WHERE `e-mail` = '"
                    + m.getReceiver() + "';";
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
            ResultSet rs = s.executeQuery("SELECT `e-mail` FROM Deltaker WHERE `hid` = "
                    + eventId + ";");

            List<Person> persons = new ArrayList<Person>();

            while (rs.next()) {
                persons.add(getPerson(rs.getString("e-mail")));
            }

            rs.close();
            c.close();

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
            ResultSet rs = s.executeQuery("SELECT * FROM Person WHERE `e-mail` = '"
                    + email + "';");

            if (rs.next()) {
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

    public List<Message> getMessages(String person) throws SQLException {
		List<Message> messages = new ArrayList<Message>();
		Connection c = getConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM Melding WHERE `mottaker` = '" + person + "';");

		while (rs.next()) {
			Message m = getMessage(rs.getInt("id"));
			messages.add(m);
		}
		
		return messages;
	}

    public static Message getMessage(int id) {

        try {

            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Melding WHERE id = "
                    + id + ";");

            if (rs.next()) {
                Message m = new Message(rs.getString("innhold"),
                        Message.Type.valueOf(rs.getString("type")),
                        rs.getString("mottaker"), rs.getInt("relatertmote"));
                m.setMid(rs.getInt("id"));
                m.setTimeSent(rs.getTimestamp("tidsendt"));

                return m;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Event> getEvents(String email) throws SQLException {
        ArrayList<Event> events = new ArrayList<Event>();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT id FROM Hendelse WHERE `ansvarlig` = '"
                + email + "';");

        while (rs.next()) {
            Event e = getEvent(rs.getInt("id"));
            events.add(e);
        }

        rs = s.executeQuery("SELECT Hid FROM Deltaker WHERE `e-mail` = '"
                + email + "';");
        while (rs.next()) {
            Event e = getEvent(rs.getInt("hid"));
            events.add(e);
        }

        return events;
    }

    public Event getEvent(int eventId) {

        try {

            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Hendelse WHERE `id` = "
                    + eventId + ";");

            if (rs.next()) {
                Event e = new Event(rs.getString("beskrivelse"),
                        Event.Type.valueOf(rs.getString("type")),
                        rs.getString("ansvarlig"), rs.getDate("dato"),
                        rs.getTime("starttid"), rs.getTime("sluttid"),
                        rs.getString("reservertrom"));
                e.setEid(eventId);

                for (Person p : getAttendees(eventId)) {
                    e.addAttendee(p.getEmail());
                }

                s.close();

                return e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Room> getFreeRooms(Reservation r) throws SQLException {
        HashMap<String, Room> hashRooms = new HashMap<String, Room>();
        List<Room> freeRooms = new ArrayList<Room>();
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from Rom;");

        while (rs.next()) {
            Room room = new Room(rs.getString("navn"), rs.getInt("størrelse"));
            hashRooms.put(rs.getString("navn"), room);
        }

        // rs = s.executeQuery("SELECT id from Hendelse WHERE dato =" +
        // r.getDate() );
        PreparedStatement p = c.prepareStatement("SELECT id from Hendelse WHERE dato = ?;");
        p.setDate(1, r.getDate());
        rs = p.executeQuery();

        while (rs.next()) {
            Event e = getEvent(rs.getInt("id"));
            long a = e.getStartTime().getTime();
            long b = e.getEndTime().getTime();
            long ss = r.getStartTime().getTime();
            long ee = r.getEndTime().getTime();
            if (((a >= ss && a < ee) || (b > ss && b < ee))
                    || ((ss > a && ss < b) || (ee > a && ee <= b))) {
                if (hashRooms.containsKey(e.getRoom())) {
                    hashRooms.remove(e.getRoom());
                }
            }
        }

        s.close();

        for (Room room : hashRooms.values()) {
            freeRooms.add(room);
        }

        return freeRooms;

    }

    public Room getRoom(String roomName) throws SQLException {

        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * from Rom WHERE navn = '" + roomName + "';");

        if (rs.next()) {
            return new Room(rs.getString("navn"), rs.getInt("storrelse"));
        }

        s.close();
        return null;
    }

    public boolean login(String user, String password) throws SQLException {
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Person WHERE `e-mail` = '"
                + user + "' AND `passord` = '" + password + "';");

        if (rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        return false;
    }

    public static void main(String[] args) throws SQLException {
        // List<Event> lol = new ArrayList<Event>();
        // CalendarService kake = new CalendarService();
        // //Reservation res = new Reservation(createDate(2011, 3, 17), new
        // Time(14,00,00), new Time(16,00,00));
        // lol = kake.getEvents("bolle@bool.com");
        //
        // for(Event r: lol){
        // System.out.println(r.getDescription() + " --" + r.getResponsible() +
        // "-.--- id=" + r.getEid());
        // }
        CalendarService cs = new CalendarService();
        cs.startListening();
        Communication com = new Communication();
        Communication.getEmployees();
    }

    private static Date createDate(int y, int m, int d) {
        Calendar c = new GregorianCalendar(y, m - 1, d);
        java.sql.Date dd = new Date(c.getTimeInMillis());
        return dd;
    }
}
