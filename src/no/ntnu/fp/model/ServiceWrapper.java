/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import no.ntnu.fp.model.calendar.Utils;


/**
 *
 * @author alxandr
 */
public class ServiceWrapper {

    public Event[] getEvents() {
        //return (Event[])Communication.getEvents("bolle@bool.com").toArray();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal.getTime());
        cal2.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 2);
        System.out.println(cal.getTime());
        System.out.println(cal2.getTime());
        Event event = new Event("Test møte, holdes en gang i året.",
                Event.Type.Appointment,
                "Per",
                Utils.getSqlDate(cal),
                Utils.getSqlTime(cal),
                Utils.getSqlTime(cal2),
                null);
        return new Event[] {
            event
        };
    }
    

}
