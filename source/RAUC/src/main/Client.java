package main;


import dfa.ClientDFASpec;
import dfa.DFAState;
import pdu.Message;
import pdu.MessageImpl.TerminationMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityStateQueryMessage;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static dfa.DFAState.ESTABLISHED;

/**
 * Main client driver. Handles DFA, socket connection, reading/writing threads, and printing info for the user
 */
public class Client {
    private volatile boolean connected;

    // for I/O
    private InputStream sInput;        // read from socket
    private OutputStream sOutput;        // write to socket
    private Socket socket;

    // connection details
    private String server, username, pass;
    private int port;

    // if using a gui
    private ClientGUI cg;

    // dfa to make client respect RAUC protocol
    private final ClientDFASpec dfa;

    /**
     * Common constructor / Constructor used via a GUI. In CLI mode the ClientGUI parameter is null
     *
     * @param server   Server address
     * @param port     Server port number
     * @param username Username
     * @param pass     Password
     * @param cg       GUI handle if applicable, else null
     */
    Client(String server, int port, String username, String pass, ClientGUI cg) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.pass = pass;
        this.cg = cg;
        this.dfa = new ClientDFASpec();
        this.connected = true;
    }

    /**
     * Constructor for CLI. Sets GUI to null
     *
     * @param server   Server address
     * @param port     Server port number
     * @param username Username
     * @param pass     Password
     */
    Client(String server, int port, String username, String pass) {
        this(server, port, username, pass, null);
    }

    /**
     * Establish the connection and automatically send AUTH
     *
     * @return whether client connected successfully
     */
    public boolean start() {
        // Establish the SSL connection
        try {
            //SocketFactory sf = SocketFactory.getDefault();
            SocketFactory sf = SSLSocketFactory.getDefault();
            display("Socket created");
            socket = sf.createSocket(server, port);
            display("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
        } catch (Exception e) {
            display("Error connecting to server");
            e.printStackTrace();
            return false;
        }

        // Establish the data streams
        try {
            display("Connecting Input stream");
            sInput = socket.getInputStream();
            display("Connecting Output stream");
            sOutput = socket.getOutputStream();
        } catch (IOException e) {
            display("Error creating I/O streams");
            e.printStackTrace();
            return false;
        }

        display("Starting DFA");
        // TODO: ??? dfa.setState(DFAState.AUTH);
        dfa.setState(DFAState.INIT);

        // create the thread that listens for server responses
        display("Starting Listening thread");
        new ListenThread().start();

        // send the authentication message
        Message auth = new UserAuthenMessage(username, pass);
        try {
        	display("Sending AUTH");
            sendMessage(auth);
            display("Auth sent, waiting reply");
        } catch (IOException e) {
            display("Error sending AUTH");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Prints to GUI or CLI
     *
     * @param msg Message to display to user
     */
    protected void display(String msg) {
        if (cg == null)
            System.out.println(msg);      // print to sout in in CLI mode
        else
            cg.display(msg + "\n");        // append to the ClientGUI conversation window
    }

    /**
     * Prints to GUI or CLI
     *
     * @param msg Message to display to user
     */
    protected void display(Message msg) {
        display("<<< " + msg.toString());
    }

    /**
     * Sends a message to the server if it is valid in the current state
     *
     * @param msg Message to send to server
     */
    protected void sendMessage(Message msg) throws IOException {
        try {
            // if current state allows for sending this message
            if (dfa.send(msg)) {
                display(">>> " + msg.toString());
                sOutput.write(msg.toBytes());
                sOutput.flush();
            } else {
                display("Message not sent. Current protocol state not valid for sending.");
            }

        } catch (IOException e) {
            display("Exception writing to server");
            throw e;
        }
    }

    /**
     * Attempts to send SHUTDOWN message and closes the connection to the server
     */
    protected void disconnect(boolean sendShutdown) {
        connected = false;
        if (sendShutdown) {
            try {
                sendMessage(new TerminationMessage());
            } catch (IOException e) {
                display("IOError during shutdown");
                e.printStackTrace();
            }
        }
        try {
            if (sInput != null) sInput.close();
            if (sOutput != null) sOutput.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            display("IOError during socket closing");
            e.printStackTrace();
        }
        if (cg != null) {
            cg.reset();
        }
    }


    /**
     * Listening thread. Processes messages from the server through the DFA
     */
    private class ListenThread extends Thread {
        @Override
        public void run() {
            while (connected) {
                try {
                    // when a bytestream is received, process it through the DFA and display it
                    receiveMessage(sInput);
                } catch (IOException e) {
                    // Stream might end abruptly during shutdown if server closes connection first
                    if (connected) {
                        display("Exception in processing input from server");
                        e.printStackTrace();
                        disconnect(true);
                    }
                }
            }
        }

        /**
         * Receive message from server, process it through the DFA, and display it
         *
         * @param stream Input stream from socket
         * @throws IOException When unexpected error occurs in connection
         */
        private void receiveMessage(InputStream stream) throws IOException {
            // reassemble bytestream into message
            Message inMsg = Message.fromStream(stream);
            // process message to make sure it is valid
            Message outMsg = dfa.receive(inMsg);
            if (outMsg == null) {
                // Received shutdown from server
                disconnect(false);
            } else {
                // display error or valid message
                display(outMsg);
            }
        }
    }

    /**
     * Simulation of Client for testing purposes
     */
    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", "./cert/sslclienttrust");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        Client client = new Client("localhost", 1500, "user", "pass");
        System.out.println("Starting");
        client.start();

        while (client.dfa.state() != ESTABLISHED) { /* Busy loop, but it's just for testing */ }
        System.out.println("Sending query");
        client.sendMessage(new UtilityStateQueryMessage("test", "test"));

        while (client.dfa.state() != ESTABLISHED) { /* Busy loop, but it's just for testing */ }
        System.out.println("Sending shutdown");
        client.disconnect(true);
    }
}