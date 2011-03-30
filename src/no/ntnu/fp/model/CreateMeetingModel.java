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

    public CreateMeetingModel(Calendar date, Calendar startTime, Calendar endTime)
    {
        this(new Event());
        setDate(date);
        setStartTime(startTime);
        setEndTime(endTime);
        newEvent = true;
        timeIsSet = true;
    }

    public CreateMeetingModel(Event event)
    {
        this.event = event;
        this.event.attendees.add(Communication.LoggedInUserEmail);

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
        return event.getDate();
    }

    public boolean isNew()
    {
        return newEvent;
    }

    public void setDate(Calendar calendar)
    {
        event.setDate(calendar);
    }

    public Calendar getStartTime()
    {
        return event.getStartTime();
    }

    public void setStartTime(Calendar startTime)
    {
        event.setStartTime(startTime);
    }

    public Calendar getEndTime()
    {
        return event.getEndTime();
    }

    public void setEndTime(Calendar endTime)
    {
        event.setEndTime(endTime);
    }

    public Room getRoom()
    {
        return event.getRoomObject();
    }

    public void setRoom(Room room)
    {
        event.setRoom(room.toString());
    }

    public String getDescription()
    {
        return event.getDescription();
    }

    public void setDescription(String description)
    {
        event.setDescription(description);
    }

    public void setDefaultValues()
    {
        if (newEvent)
        {
            if (!timeIsSet)
            {
                setDate(Calendar.getInstance());
                Calendar startTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                startTime.setTime(new Time(10, 0, 0));
                endTime.setTime(new Time(11, 0, 0));
                setStartTime(startTime);
                setEndTime(endTime);
            }
            event.setResponsible(Communication.LoggedInUserEmail);
        }
        else
        {
            setDate(event.getDate());
            setStartTime(event.getStartTime());
            setEndTime(event.getEndTime());
            setRoom(event.getRoomObject());
            setDescription(event.getDescription());
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
        List<Room> rooms = Communication.getFreeRooms(new Reservation(new Date(getDate().getTimeInMillis()), new Time(getStartTime().getTimeInMillis()), new Time(getEndTime().getTimeInMillis())));

        if (!newEvent && getStartTime() == event.getStartTime())
            rooms.add(event.getRoomObject());

        return rooms;
    }

    public List<Person> getAttendees()
    {
        List<Person> att = new ArrayList<Person>();
        for(Person p : Communication.getEmployees())
            if(event.attendees.contains(p.getEmail()) || Communication.LoggedInUserEmail.equals(p.getEmail()))
                att.add(p);
        return att;
    }

    public List<Person> getAllUsers()
    {
        if (newEvent)
        {
            List<Person> employees = Communication.getEmployees();
            for(Person p:employees)
                if(p.getEmail().equals(Communication.LoggedInUserEmail))
                {
                    employees.remove(p);
                    break;
                }

            return Communication.getEmployees();
        }
        else
        {
            List<Person> attendees = getAttendees();
            List<Person> employees = Communication.getEmployees();

            for (Person person : attendees)
                employees.remove(person);
            
            return employees;
        }
    }

    public void cleanAttendees()
    {
        event.attendees.clear();
    }

    public void addAttendee(Person person)
    {
        event.attendees.add(person.getEmail());
    }

    public boolean isValidInput()
    {
        return true;
    }

    public void save()
    {
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
        return event.getResponsible().equals(Communication.LoggedInUserEmail);
    }
}
