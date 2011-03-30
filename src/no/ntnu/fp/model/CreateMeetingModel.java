package no.ntnu.fp.model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateMeetingModel
{

    private boolean newEvent;
    private boolean timeIsSet;
    private Event event;
    private Calendar calendar;
    private Calendar startTime;
    private Calendar endTime;
    private String description;
    private Room room;
    private List<Person> attendees;
    private String responsible;

    public CreateMeetingModel(Calendar date, Calendar startTime, Calendar endTime)
    {
        this(new Event());
        this.calendar = date;
        this.startTime = startTime;
        this.endTime = endTime;
        newEvent = true;
        timeIsSet = true;
    }

    public CreateMeetingModel(Event event)
    {
        this.event = event;
        newEvent = false;
        timeIsSet = true;
    }

    public CreateMeetingModel()
    {
        this(new Event());
        newEvent = true;
        timeIsSet = false;
    }

    public Calendar getDate()
    {
        return calendar;
    }

    public boolean isNew()
    {
        return newEvent;
    }

    public void setDate(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public Calendar getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Calendar startTime)
    {
        this.startTime = startTime;
    }

    public Calendar getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Calendar endTime)
    {
        this.endTime = endTime;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDefaultValues()
    {
        if (newEvent)
        {
            if (!timeIsSet)
            {
                calendar = Calendar.getInstance();
                startTime = Calendar.getInstance();
                endTime = Calendar.getInstance();
                startTime.setTime(new Time(10, 0, 0));
                endTime.setTime(new Time(11, 0, 0));
            }
            event.setResponsible(Communication.LoggedInUserEmail);
        }
        else
        {
            calendar = event.getDate();
            startTime = event.getStartTime();
            endTime = event.getEndTime();
            room = event.getRoomObject();
            description = event.getDescription();
        }
    }

    public Time[] hentKlokkeslett()
    {
        Time[] comboChoose = new Time[24];
        for (int i = 0; i < 24; i++)
        {
            comboChoose[i] = new Time(i, 0, 0);
        }
        return comboChoose;
    }

    public List<Room> getRooms()
    {

        List<Room> rooms = Communication.getFreeRooms(new Reservation(new Date(calendar.getTimeInMillis()), new Time(startTime.getTimeInMillis()), new Time(endTime.getTimeInMillis())));

        if (!newEvent && startTime == event.getStartTime())
        {
            rooms.add(event.getRoomObject());
        }

        return rooms;
    }

    public List<Person> getAttendees()
    {
        return attendees;
    }

    public List<Person> getAllUsers()
    {
        if (newEvent)
        {
            return Communication.getEmployees();
        }
        else
        {
            List<Person> attendees = Communication.getAttendees(event.getEid());
            List<Person> employees = Communication.getEmployees();
            for (Person person : attendees)
            {
                employees.remove(person);
            }
            return employees;
        }
    }

    public void cleanAttendees()
    {
        attendees = new ArrayList<Person>();
    }

    public void addAttendee(Person person)
    {
        attendees.add(person);
    }

    public boolean isValidInput()
    {
        return true;
    }

    public void save()
    {
        event.setDate(calendar);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setRoom(room.toString());
        event.setDescription(description);

        for (Person person : attendees)
        {
            event.addAttendee(person.getEmail());
        }
        if (event.getRoom() == null)
        {
            event.setRoom("");
        }
        if (event.attendees == null || event.attendees.isEmpty())
        {
            event.type = Event.Type.Appointment;
        }
        else
        {
            event.type = Event.Type.Meeting;
        }


        if (newEvent)
        {
            Communication.saveEvent(event);
        }
        else
        {
            Communication.updateEvent(event);
        }
    }

    public void delete()
    {
        Communication.deleteEvent(event.getEid());
    }

    public boolean isEditable()
    {
    	System.out.println("Event.getResponsible: " + event.getResponsible());
    	System.out.println("Logged in User: " + Communication.LoggedInUserEmail);
    	System.err.println("=====================================================================================================");
        if (event.getResponsible().equals(Communication.LoggedInUserEmail))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
