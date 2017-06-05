package main;

import components.Factory;
import dfa.ServerDFASpec;
import main.ServerGUI.LogPanel;
import pdu.Message;
import pdu.MessageImpl.TerminationMessage;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Main server driver. Handles DFA, socket connection, reading/writing threads, and printing info for the user
 */
public class Server {
    private Map<String, ConnectionThread> connections; // <Identifier, client thread>
    private Map<String, String> users; // <username, password>
    private boolean running;

    // For the prototype
    private static final Map<String, String> testUsers = new HashMap<>();

    static {
        testUsers.put("user", "pass");
    }

    // gui if running in gui mode
    private ServerGUI sg;

    // default listening port for incoming connections
    private int port;

    public Server(int port) {
        this(port, null);
    }

    /**
     * Main constructor
     *
     * @param port Port to listen on
     * @param sg   GUI handle if applicable, else null
     */
    public Server(int port, ServerGUI sg) {
        this(port, testUsers, sg);
    }

    public Server(int port, Map<String, String> users, ServerGUI sg) {
        // TODO: fix cert problems
        //System.setProperty("javax.net.ssl.keyStore", "/home/ismail/sslserverkeys");
        System.setProperty("javax.net.ssl.keyStore", "./cert/sslserverkeys");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        //System.setProperty("javax.net.ssl.trustStore", "/home/ismail/sslservertrust");
        System.setProperty("javax.net.ssl.trustStore", "./cert/sslserverkeys");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        this.sg = sg;
        this.port = port;
        this.users = users;
        connections = new HashMap<>();
    }

    /**
     * Listen to a specific port. When receiving socket, start a new thread to process data so that the program can
     * process multiple connections at the same time
     */
    public void run() {
    	running = true;
        /* create a server socket to listen for connections */
        try {
            //ServerSocket sSocket = ServerSocketFactory.getDefault().createServerSocket(port);
            ServerSocket sSocket = SSLServerSocketFactory.getDefault().createServerSocket(port);
            while (running) {
                display("Waiting for clients on port " + port + "...");
                Socket cSocket = sSocket.accept();
                if (!running) {
                	break;
                }
                display("Connected to client " + cSocket.getInetAddress() + ":" + cSocket.getPort());
                
                // fork a new thread when a connection is accepted
                ConnectionThread ct = new ConnectionThread(cSocket, sg);
                // keep it in collection with an identifier
                connections.put(ct.identify(), ct);
                
                display("Starting client thread " + ct.identify());
                ct.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Set of connection identifiers
     */
    public Set<String> getConnections() {
        return connections.keySet();
    }

    /**
     * Prints to GUI or CLI
     *
     * @param msg Message to display to user
     */
    private void display(String msg) {
        if (sg == null)
            System.out.println(msg);
        else
            sg.getServerLog().display(msg);
    }

    /**
     * Shuts down all connected clients and stop the server.
     */
    protected void shutdown() {
    	running = false;
        // disconnect all connected clients
        for (String id : connections.keySet()) {
            connections.get(id).disconnect(true);
        }
    }

    /**
     * Handles a single connection between server and client
     */
    private class ConnectionThread extends Thread {        
        private InputStream sInput;
        private OutputStream sOutput;
        private Socket socket;
        private String identifier;
        private LogPanel out;
        private ServerDFASpec dfa;
        boolean connected;


        /**
         * Creates a new thread for the connection
         *
         * @param socket Socket to connect to
         * @param cp     GUI handle if applicable, else null
         */
        ConnectionThread(Socket socket, ServerGUI sg) {
            this.socket = socket;
            try {
                // establish streams
                this.sInput = socket.getInputStream();
                this.sOutput = socket.getOutputStream();
                this.connected = true;
            } catch (IOException e) {
                display("Error creating in/out streams");
                e.printStackTrace();
            }
            finally {
                this.dfa = new ServerDFASpec(new Factory(), users);
                this.identifier = socket.getInetAddress() + ":" + socket.getPort();
                if (sg != null) {
                	this.out = sg.addConnection(identifier);
                }
            }
        }

        @SuppressWarnings("unused")
		ConnectionThread(Socket socket) {
            // create a common ConnectionThread without a gui panel
            this(socket, null);
        }

        /**
         * Main run loop. Receives and processes messages from client, transitions DFA, and sends automated responses
         */
        public void run() {
            while (connected) {
                try {
                    // when a bytestream is received, process it through the DFA and display it
                    Message outM = receiveMessage(sInput);
                    if (outM == null) {
                        // Received shutdown from client
                        disconnect(false);
                    } else {
                        sendMessage(outM);
                    }
                } catch (IOException e) {
                    display("Error in processing. Closing connection");
                    e.printStackTrace();
                    disconnect(true);
                }
            }
        }

        /**
         * Process an input byte stream into a message, and display it
         *
         * @param stream Input stream
         * @return Automated message response
         */
        private Message receiveMessage(InputStream stream) throws IOException {
            try {
                // reassemble bytestream into message
                Message inMsg = Message.fromStream(stream);
                display("<<< " + inMsg);
                // process message to make sure it is valid
                return dfa.receive(inMsg);
            } catch (IOException e) {
                display("Exception in processing input from client");
                throw e;
            }
        }

        /**
         * Sends the message if valid for the current DFA state
         *
         * @param msg
         * @throws IOException
         */
        protected void sendMessage(Message msg) throws IOException {
            // send the message across the connection and log it in output window
            try {
                // if current state allows for sending this message
                if (dfa.send(msg)) {
                    display(">>> " + msg.toString());
                    sOutput.write(msg.toBytes());
                    sOutput.flush();
                } else {
                    display("Cannot send message in state " + dfa.state());
                }
            } catch (IOException e) {
                display("Exception in processing client output");
                throw e;
            }
        }

        /**
         * Attempts to send SHUTDOWN message and closes the connection to the client
         */
        protected void disconnect(boolean sendShutdown) {
            connected = false;
            // send the termination message
            if (sendShutdown) {
                try {
                    sendMessage(new TerminationMessage());
                } catch (IOException e) {
                    display("IOError during shutdown");
                    e.printStackTrace();
                }
            }
            //flush the streams
            try {
                if (sInput != null) sInput.close();
                if (sOutput != null) sOutput.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                display("IOError during stream closing");
                e.printStackTrace();
            }
        }


        /**
         * Prints to GUI or CLI
         *
         * @param s Message to display to user
         */
        void display(String s) {
            if (out == null) {
                System.out.println(s);
            } else {
                out.display(s);
            }
        }

        String identify() {
            return identifier;
        }
    }

    /**
     * CLI server
     *
     * @param args 1st arg taken as port number
     */
    public static void main(String[] args) {
        int port = 1500;
       /*if (args.length > 1) {
            port = Integer.valueOf(args[1]);
        }*/
        Server server = new Server(port);
        server.run();
    }
}
