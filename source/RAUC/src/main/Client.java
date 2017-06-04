package main;


import dfa.ClientDFASpec;
import dfa.DFAState;
import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.TerminationMessage;
import pdu.MessageImpl.UserAuthenMessage;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * Main client driver. Handles DFA, socket connection, reading/writing threads, and printing info for the user
 */
public class Client {
    // for I/O
    private InputStream sInput;        // read from socket
    private OutputStream sOutput;        // write to socket
    private SSLSocket socket;

    // connection details
    private String server, username, pass;
    private int port;

    // if using a gui
    private ClientGUI cg;

    // dfa to make client respect RAUC protocol
    private ClientDFASpec dfa;

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
            SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) sf.createSocket(server, port);
            display("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
        }
        catch (Exception e) {
            display("Error connecting to server");
            e.printStackTrace();
            return false;
        }

        // Establish the data streams
        try {
            display("Connecting Input");
            sInput = socket.getInputStream();
            display("Connecting Output");
            sOutput = socket.getOutputStream();
        } catch (IOException e) {
            display("Error creating in/out streams");
            e.printStackTrace();
            return false;
        }

        // all further DFA state changes will be handled by dfa.receive(message)
        display("Starting DFA");
        // TODO: ??? dfa.setState(DFAState.AUTH);
        dfa.setState(DFAState.INIT);

        // create the thread that listens for server responses
        display("Starting Listening thread");
        new ListenThread().start();

        // send the authentication message
        display("Sending AUTH");
        Message auth = new UserAuthenMessage(username, pass);
        sendMessage(auth);

        return true;
    }

    protected void sendMessage(Message msg) {
        // send the message across the connection and log it in output window
        try {
            if (dfa.send(msg)) { // if current state allows for sending a message
                sOutput.write(msg.toBytes());

                // print the message to client log
                display(">>> " + msg.toString());
            } else { // yell at the user
                display("Message not sent. Current protocol state not valid for sending.");
            }

        } catch (IOException e) {
            display("Exception writing to server");
            e.printStackTrace();
        }
    }


    // prints a string to output
    protected void display(String msg) {
        if (cg == null)
            System.out.println(msg);      // print to sout in in CLI mode
        else
            cg.display(msg + "\n");        // append to the ClientGUI conversation window
    }

    // prints a message to output
    protected void display(Message msg) {
        display("<<< " + msg.toString());
    }

    protected void disconnect() {
        Message m = new TerminationMessage();
        sendMessage(m);
        //flush the streams
        try {
            sInput.close();
            sOutput.close();
            socket.close();
        } catch (Exception e) {
        } finally {
            //if using gui, reset to initial state
            if (cg != null) {
                cg.reset();
            }
        }
    }


    /*
     * A thread to listen for bytestreams from the server and display them after processing through the DFA
     */
    private class ListenThread extends Thread {
        // the loop to hear a message when delivered
        public void run() {
            while (true) {
                try {
                    // when a bytestream is received, process it through the DFA and display it
                    receiveMessage(sInput);
                } catch (IOException e) {
                    display("Connection closed unexpectedly");
                    e.printStackTrace();
                    disconnect();
                    break;
                }
            }

        }

        // process a bytestream into a message and display it
        private void receiveMessage(InputStream stream) throws IOException {
            try {
                // reassemble bytestream into message
                Message inMsg = Message.fromStream(stream);
                // process message to make sure it is valid
                Message outMsg = dfa.receive(inMsg);
                // display error or valid message
                display(outMsg);
            } catch (IOException e) {
                display("Exception in processing input from server");
                e.printStackTrace();
                throw e;
            }
            // if dfa.state == ok to send, reenable sending of messages from client
        }
    }


    // extraneous code below this line
    // -------------------------------------------------------

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "./cert/sslclienttrust");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        Client client = new Client("localhost", 1500, "user", "pass");
        System.out.println("Starting");
        client.start();
    }

		/* LEGACY SECTION */

    /*
     * SendSocket - send a socket to a target
     *
     * @param server - target server
     * @param port - target port
     * @param message - message
     *
     * @return - received responses
     */
    public static void sendSocket(String server, int port, String uname, String pass) {
        SSLSocket sslsocket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        String str = "";
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslsocket = (SSLSocket) sslsocketfactory.createSocket(server, port);
            System.out.println("sslsocket=" + sslsocket);
            br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream(), "UTF-8")));
            ClientDFASpec dfa = new ClientDFASpec();


            //[TEST - THIS IS A MINI CLIENT -- WILL BE REPLACED WITH while(true){dfa.receive()} ]
            //This mimics one of messages processing of dfa.receive
            UserAuthenMessage uauth = new UserAuthenMessage(uname, pass);

            pw.println(uauth.toString() + "\n");
            pw.flush();

            str = (br.readLine() + "\n");
            Message ackm = MessageFactory.createMessage(str);
            System.out.println("Message received from server " + ackm.toString());
            //[MINI-CLIENT-ENDS]


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing Client...");
                br.close();
                pw.close();
                sslsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }
}