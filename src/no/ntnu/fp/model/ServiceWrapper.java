/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.model;

import java.util.List;


/**
 *
 * @author alxandr
 */
public class ServiceWrapper {

    public List<Event> getEvents() {
        return Communication.getEvents(Communication.LoggedInUserEmail);
    }
    

}
