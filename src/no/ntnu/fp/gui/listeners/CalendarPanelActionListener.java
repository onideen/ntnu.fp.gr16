/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.gui.listeners;

import com.u2d.type.atom.TimeSpan;
import no.ntnu.fp.gui.CalendarPanel;
import no.ntnu.fp.model.Event;

/**
 *
 * @author Alxandr
 */
public interface CalendarPanelActionListener {
    void eventClicked(CalendarPanel panel, Event event);
    void timeClicked(CalendarPanel panel, TimeSpan timeSpan);
}
