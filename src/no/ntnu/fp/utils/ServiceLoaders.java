/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.utils;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.fp.model.Communication;

/**
 *
 * @author alxandr
 */
public class ServiceLoaders {
    public static LoadRunner getMessages(final String email)
    {
        return new LoadRunner() {

            public Object run()
            {
                return Communication.getMessages(email);
            }
        };
    }

    public static LoadRunner login(final String username, final String password)
    {
        return new LoadRunner() {

            public Object run()
            {
                return Communication.login(username, password);
            }
        };
    }

    public static LoadRunner getEvents(final String email)
    {
        return new LoadRunner() {

            public Object run()
            {
                return Communication.getEvents(email);
            }
        };
    }
}
