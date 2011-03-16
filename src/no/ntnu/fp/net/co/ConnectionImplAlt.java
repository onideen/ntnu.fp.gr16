/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 *
 * @author alxandr
 */
public class ConnectionImplAlt implements Connection
{
    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    private State state;
    private int packetNumber = 0;
    private final List<KtnDatagram> outBuffer = new LinkedList<KtnDatagram>();
    private final List<KtnDatagram> transmitBuffer = new LinkedList<KtnDatagram>();

    private final List<String> inBuffer = new LinkedList<String>();
    private int nextPacketNumber = 1;

    private Map<Integer, AutoResetEvent> packetWait = new HashMap<Integer, AutoResetEvent>();

    private final AutoResetEvent connectionReceiveWait = new AutoResetEvent(false);
    private final AutoResetEvent outBufferWait = new AutoResetEvent(false);
    private final MultiAutoResetEvent transmitBufferWait = new MultiAutoResetEvent(false);
    private final AutoResetEvent dataWait = new AutoResetEvent(false);
    private void handleSyn(KtnDatagram packet) {
        if(state != State.CONNECTION_WAIT) {
            SendResponse(packet, Flag.FIN, null);
        } else {
            state = State.SYN_RECEIVED;
            SendResponse(packet, Flag.SYN_ACK, null);
            remoteAddress = packet.getSrc_addr();
            remotePort = packet.getSrc_port();
            connectionReceiveWait.Set();
        }
    }

    private void handleSynAck(KtnDatagram packet) {
        if(state != State.SYN_SENT) {
            SendResponse(packet, Flag.FIN, null);
        } else {
            state = State.ESTABLISHED;
            SendAck(packet);
            remoteAddress = packet.getSrc_addr();
            remotePort = packet.getSrc_port();
            connectionReceiveWait.Set();
        }
    }

    private void handleFin(KtnDatagram packet) {
        if(state != State.ESTABLISHED && state != State.FIN_WAIT_2) return;
        if(state == State.FIN_WAIT_2)
            state = State.CLOSED;
        else
            state = State.FIN_WAIT_1;
        
        SendAck(packet);
        if(state == State.CLOSED)
            receiver.receiving = false;
        dataWait.Set();
    }

    private void handleAck(KtnDatagram packet) {
        AutoResetEvent are = null;
        synchronized(outBuffer) {
            if(!packetWait.containsKey(packet.getAck()))
                return;
            are = packetWait.get(packet.getAck());
            for(int i = 0, l = transmitBuffer.size(); i < l; i++)
            {
                KtnDatagram buff = transmitBuffer.get(i);
                int seq = buff.getSeq_nr();
                if(seq == packet.getAck())
                {
                    transmitBuffer.remove(i);
                    break;
                }
            }
        }
        if(are != null)
            are.Set();
    }

    private void handleData(KtnDatagram packet) {
        if(state != State.ESTABLISHED) return;
        if(packet.getChecksum() == packet.calculateChecksum() && packet.getSeq_nr() == nextPacketNumber) {
            synchronized(inBuffer) {
                nextPacketNumber++;
                inBuffer.add(packet.getPayload().toString());
            }
            SendAck(packet);
            dataWait.Set();
        } else if(packet.getSeq_nr() < nextPacketNumber) {
            SendAck(packet);
        }
    }

    private void SendResponse(KtnDatagram packet, Flag flag, String data) {
        KtnDatagram toSend = new KtnDatagram();
        toSend.setDest_addr(packet.getSrc_addr());
        toSend.setDest_port(packet.getSrc_port());
        toSend.setSrc_addr(myAddress);
        toSend.setSrc_port(myPort);
        toSend.setFlag(flag);
        toSend.setPayload(data);
        toSend.setSeq_nr(packetNumber++);

        try {
            new ClSocket().send(toSend);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AutoResetEvent SendPacket(Flag flag, String data, boolean awaitAck) {
        KtnDatagram toSend = new KtnDatagram();
        toSend.setDest_addr(remoteAddress);
        toSend.setDest_port(remotePort);
        toSend.setSrc_addr(myAddress);
        toSend.setSrc_port(myPort);
        toSend.setFlag(flag);
        toSend.setPayload(data);
        toSend.setSeq_nr(packetNumber++);
        if(awaitAck) {
            AutoResetEvent are = new AutoResetEvent(false);
            synchronized(outBuffer) {
                outBuffer.add(toSend);
                packetWait.put(toSend.getSeq_nr(), are);
            }
            outBufferWait.Set();
            return are;
        } else {
            try {
                new ClSocket().send(toSend);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClException ex) {
                Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    private void SendAck(KtnDatagram packet) {
        KtnDatagram toSend = new KtnDatagram();
        toSend.setDest_addr(remoteAddress);
        toSend.setDest_port(remotePort);
        toSend.setSrc_addr(myAddress);
        toSend.setSrc_port(myPort);
        toSend.setFlag(Flag.ACK);
        toSend.setAck(packet.getSeq_nr());
        System.out.println(toSend.toString());

        synchronized(outBuffer) {
            outBuffer.add(toSend);
            outBufferWait.Set();
        }
    }

    public enum State {
        CLOSED,
        SYN_RECEIVED,
        SYN_SENT,
        ESTABLISHED,
        CONNECTION_WAIT,
        FIN_WAIT_1,
        FIN_WAIT_2
    }

    private class Retransmittor extends Thread {
        /** Connection to listen on. */
        private ClSocket connection;

        private boolean sending = true;

        public Retransmittor()
        {
            setName("RetransmitDeamon for ConnectionImpl");
        }

        @Override
        public void run() {
            while(sending) {
                connection = new ClSocket();
                try {
                    transmitBufferWait.WaitOne(TIMEOUT * 5, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
                AutoResetEvent waiter = new AutoResetEvent(false);
                try {
                    waiter.WaitOne(TIMEOUT, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
                synchronized(outBuffer) {
                    if(!transmitBuffer.isEmpty()) {
                        KtnDatagram toSend = transmitBuffer.remove(0);
                        try {
                            new ClSocket().send(toSend);
                            transmitBuffer.add(toSend);
                        } catch (IOException ex) {
                            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClException ex) {
                            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    private class Sender extends Thread {
        /** Connection to listen on. */
        private ClSocket connection;

        private boolean sending = true;

        public Sender()
        {
            setName("SendDeamon for ConnectionImpl");
        }

        @Override
        public void run() {
            while(sending) {
                connection = new ClSocket();
                try {
                    outBufferWait.WaitOne(TIMEOUT * 5, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<KtnDatagram> myOutBuffer = new LinkedList<KtnDatagram>();
                synchronized(outBuffer) {
                    while(!outBuffer.isEmpty()) {
                        myOutBuffer.add(outBuffer.remove(0));
                    }
                }
                while(!myOutBuffer.isEmpty()) {
                    KtnDatagram toSend = myOutBuffer.remove(0);
                    try {
                        connection.send(toSend);
                        if(toSend.getFlag() == Flag.NONE || toSend.getFlag() == Flag.FIN) {
                            synchronized(outBuffer) {
                                transmitBuffer.add(toSend);
                            }
                            transmitBufferWait.Set();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClException ex) {
                        Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private class Receiver extends Thread {
        /** Connection to listen on. */
        private ClSocket connection;

        private boolean receiving = true;

        public Receiver()
        {
            setName("ReceiveDeamon for ConnectionImpl");
        }

        @Override
        public void run()
        {
            while(receiving) {
                connection = new ClSocket();
                final KtnDatagram[] packet = new KtnDatagram[] {null};
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            packet[0] = connection.receive(myPort);
                        } catch (IOException ex) {
                            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                thread.start();
                try {
                    thread.join(TIMEOUT * 5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    connection.cancelReceive();
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(packet[0] != null) {
                    try { handlePacket(packet[0]); }
                    catch (Throwable ex) {
                        Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void handlePacket(KtnDatagram packet) {
        if(packet.getDest_addr().equals(myAddress) && packet.getDest_port() == myPort) {
            if(packet.getFlag() != Flag.NONE) {
                if(packet.getFlag() == Flag.SYN)
                {
                    handleSyn(packet);
                } else if(packet.getFlag() == Flag.SYN_ACK) {
                    handleSynAck(packet);
                } else if(packet.getFlag() == Flag.FIN) {
                    handleFin(packet);
                } else if(packet.getFlag() == Flag.ACK) {
                    handleAck(packet);
                }
            } else {
                handleData(packet);
            }
        }
    }

    /**
     * Time between retransmissions. When setting this, also consider setting
     * {@link #TIMEOUT}: There has to be time for a few retransmissions within
     * the timeout. Setting RETRANSMIT too low will result in a lot of traffic
     * and duplicate packets because of the delays in A2. Note: Low values of
     * RETRANSMIT will generate duplicate packets independently of the setting
     * for duplicate packets in the configuration for A2!
     */
    protected final static int RETRANSMIT = 800;

    /**
     * Timeout for receives. Setting this too high can cause slow operation in
     * the case of many errors, while setting it too low can cause failure of
     * operation because of the delays in A2. It is now set to three times the
     * {@link #RETRANSMIT} value, for a total of 4 possible transmissions before
     * timing out.
     */
    protected static int TIMEOUT = 3 * RETRANSMIT + (RETRANSMIT / 2);


    private int myPort = -1;
    private String myAddress;
    private int remotePort = -1;
    private String remoteAddress;

    private Receiver receiver;
    private Sender sender;
    private Retransmittor retransmittor;

    public ConnectionImplAlt(int port) {
        myPort = port;
        myAddress = getIPv4Address();
        state = State.CLOSED;
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
        if(state != state.CLOSED)
            throw new IOException("Not a closed socket.");
        state = State.SYN_SENT;
        this.remoteAddress = remoteAddress.getHostAddress();
        this.remotePort = remotePort;
        if(sender == null) {
            sender = new Sender();
            sender.start();
        }
        if(receiver == null) {
            receiver = new Receiver();
            receiver.start();
        }
        if(retransmittor == null) {
            retransmittor = new Retransmittor();
            retransmittor.start();
        }
        SendPacket(Flag.SYN, null, false);
        try {
            connectionReceiveWait.WaitOne();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }
        state = State.ESTABLISHED;
    }

    public Connection accept() throws IOException, SocketTimeoutException {
        if(state != state.CLOSED)
            throw new IOException("Not a closed socket.");
        state = State.CONNECTION_WAIT;
        if(sender == null) {
            sender = new Sender();
            sender.start();
        }
        if(receiver == null) {
            receiver = new Receiver();
            receiver.start();
        }
        if(retransmittor == null) {
            retransmittor = new Retransmittor();
            retransmittor.start();
        }
        try {
            connectionReceiveWait.WaitOne();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }
        state = State.ESTABLISHED;
        return this;
    }

    public void send(String msg) throws ConnectException, IOException {
        try {
            SendPacket(Flag.NONE, msg, true).WaitOne();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String receive() throws ConnectException, IOException {
        synchronized(inBuffer) {
            if(!inBuffer.isEmpty()) {
                return inBuffer.remove(0);
            }
        }

        if(state == State.FIN_WAIT_1 || state == State.CLOSED)
            throw new EOFException("FIN received");

        try {
            dataWait.WaitOne();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receive();
    }

    public void close() throws IOException {
        if(state == State.ESTABLISHED || state == State.FIN_WAIT_1) {
            SendPacket(Flag.FIN, null, true);

            AutoResetEvent are = new AutoResetEvent(false);
            try {
                are.WaitOne();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(state == State.ESTABLISHED)
                state = State.FIN_WAIT_2;
            else
                state = State.CLOSED;
        } else return;

        sender.sending = false;
        retransmittor.sending = false;
        if(state == State.CLOSED)
            receiver.receiving = false;
        outBufferWait.Set();
        transmitBufferWait.Set();


        AutoResetEvent are2 = new AutoResetEvent(false);
        try {
            are2.WaitOne(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConnectionImplAlt.class.getName()).log(Level.SEVERE, null, ex);
        }


        /*receiver.interrupt();
        retransmittor.interrupt();
        sender.interrupt();*/

    }

}
