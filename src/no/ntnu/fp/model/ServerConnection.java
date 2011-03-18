/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.model;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.activity.InvalidActivityException;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;

/**
 *
 * @author Alxandr
 */
public class ServerConnection {
    private static Connection connection = null;
    private static final Integer mutex = -1;

    public static void connect() throws UnknownHostException, IOException {
        synchronized(mutex) {
            if(connection != null)
                return;
            connection = new ConnectionImpl(1011);
            connection.connect(InetAddress.getByName("129.241.138.74"), 1010);
        }
    }

    public static String proxyRequest(String message) throws InvalidActivityException, ConnectException, IOException {
        synchronized(mutex) {
            if(connection == null)
                throw new InvalidActivityException("Can't send data before a connection is made.");

            connection.send(message);
            return connection.receive();
        }
    }
}
