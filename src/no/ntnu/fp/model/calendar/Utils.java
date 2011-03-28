/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.model.calendar;

import java.util.Calendar;

/**
 *
 * @author alxandr
 */
public class Utils {
    public static java.sql.Date getSqlDate(java.util.Calendar calendar) {
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    public static java.sql.Time getSqlTime(java.util.Calendar calendar) {
        return new java.sql.Time(calendar.getTimeInMillis());
    }

    public static java.util.Calendar getCalendar(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
