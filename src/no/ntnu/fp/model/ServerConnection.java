/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.model;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
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
    private static String ip = "";

    public static void connect() throws UnknownHostException, IOException {

        if(ip == "")
        {
            try {
                System.out.println(new java.io.File("ip.txt").getAbsolutePath());
                Scanner scanner = new Scanner(new java.io.File("ip.txt"));
                if(scanner.hasNextLine())
                    ip = scanner.nextLine();
                System.out.println("Remote IP is now " + ip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        synchronized(mutex) {
            if(connection != null)
                return;
            connection = new ConnectionImpl(1011);
            connection.connect(InetAddress.getByName(ip), 1010);
        }
    }

    public static String proxyRequest(String message) throws InvalidActivityException, ConnectException, IOException {
        synchronized(mutex) {
            if(connection == null)
                throw new InvalidActivityException("Can't send data before a connection is made.");

            connection.send(message);
            System.out.println(">>>>>>Sent request and waiting for response.");
            return connection.receive();
        }
    }
}
