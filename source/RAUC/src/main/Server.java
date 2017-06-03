package main;

import components.Factory;
import main.ServerGUI.ConnectionPanel;
import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.TerminationMessage;
import specs.SpecImpl.ServerDFASpec;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Server {
	/*
	 * main - listen a specific port. When receiving socket, start a new thread
	 * to process data so that the program can process multiple socket at the
	 * same time
	 */
	
	private HashMap<String, ConnectionThread> connections; // <Identifier, client thread>
	private HashMap<String, String> users; // <username, password>
	
	// gui if running in gui mode
	private ServerGUI sg;
	
	// default listening port for incoming connections
	private int port; 
	
	
	
	
	/*
	 * consutrctor used when running in CLI mode
	 */
	public Server(int port) {
		this(port, null);
		connections = new HashMap<String, ConnectionThread>();
	}
	
	/*
	 * common constructor for cli or GUI mode
	 */
	public Server (int port, ServerGUI sg) {
		// TODO: Fix me with stuff from the certificatemoving branch!!!!
		// Needs the new files generated placed in a new directory; fix rel path
		System.setProperty("javax.net.ssl.keyStore", "/home/ismail/sslserverkeys");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		System.setProperty("javax.net.ssl.trustStore", "/home/ismail/sslservertrust");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		this.sg = sg;
		this.port = port;
	}
	
	
	public void start() {
		/* create a server socket to listen for connections */
		try {
		SSLServerSocketFactory sf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLServerSocket sSocket = (SSLServerSocket) sf.createServerSocket(port);
		
		while(true) {
			Socket cSocket = sSocket.accept();
			
			// fork a new thread when a connection is accepted
			ConnectionThread ct = new ConnectionThread(cSocket);

			// keep it in collection with an identifier
			connections.put(ct.identify(), ct);
			
			// if using gui, add connection to it
			if (sg != null) {
				ct.setGui(sg.addConnection(ct.identify()));
			}

		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// return a list of connection identifiers
	public ArrayList<String> getConnections() {
		return  new ArrayList<String>(connections.keySet());
	}
	
	private void display(String msg) {
		if(sg == null)	
			System.out.println(msg);
		else
			sg.display(msg + "\n");
	}
	
	private void display(String connection, String msg) {
		if(sg == null)
			System.out.println(connection + " >>> " + msg);
		else
			sg.display(connection, msg);
	}
		
	protected void shutdown() {
		// disconnect all connected clients
		for (String id : connections.keySet()) {
			connections.get(id).disconnect();
		}
	}
	
	private class ConnectionThread extends Thread {
		private ObjectInputStream sInput;		// read from socket
		private ObjectOutputStream sOutput;		// write to socket
		private SSLSocket socket;
		private String identifier;
		private ConnectionPanel out;
		private ServerDFASpec dfa;
		boolean connected;
		
		ConnectionThread(Socket socket, ConnectionPanel cp) {
			try {
				// establish direct connection
				dfa = new ServerDFASpec(new Factory());
				this.identifier = socket.getInetAddress() + ":" + socket.getPort();
				display("Connection accepted " + identifier);
				
				// establish streams;
				sInput  = new ObjectInputStream(socket.getInputStream());
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				
				
				connected = true;
				}
				catch (IOException eIO) {
					display("Error creating in/out streams: " + eIO);
				}
			}

        ConnectionThread(Socket socket) {
            // create a common ConnectionThread without a gui panel
            this(socket, null);
        }
        
		public void run() {
			while(connected) {
				try {
					// when a bytestream is received, process it through the DFA and display it
					Message outM = receiveMessage((byte[][]) sInput.readObject());
					sendMessage(outM);
				}
				catch (IOException e) {
					display("Connection closed unexpectedly: " + e);
					disconnect();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
			
		}
		
		// process a bytestream into a message and display it
		private Message receiveMessage(byte[][] bb) {
			try {
			// reassemble bytestream into message 
			Message inMsg = MessageFactory.createMessage(bb);
			display("<<< " + inMsg);
			// process message to make sure it is valid
			return dfa.process(inMsg);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		protected void sendMessage(Message msg) {
			// send the message across the connection and log it in output window
			try {
				if (dfa.send(msg)) { // if current state allows for sending a message
					// convert message to bytestream
					List<byte[]> bl = msg.serialize();
					byte[][] bb = msg.crunchToBytes(bl);
					
					// send the message
					sOutput.writeObject(bb); 
					
					// print the message to client log
					display(">>> " + msg.toString()); 
				} else { // yell at the user
					display("Message not sent. Current protocol state not valid for sending.");
				}
				
			}
			catch(IOException e) {
				display("Exception writing to server: " + e);
			}
		}

		protected void setGui(ConnectionPanel cp) {
			this.out = cp;
		}
		
		protected void disconnect() {
			// send the termination message
			//TODO use MessageFactory
			sendMessage(new TerminationMessage());
			connected = false;
			//flush the streams
			try {
				sInput.close();
				sOutput.close();
			} 
			catch (Exception e) {
			}
			finally {
				//if using gui, delete cp
				}
			}
        
        void display(String s) {
        	out.display(s);
        }
        
        String identify() {
        	return identifier;
        }
        
        String identifyUser() {
        	return null;
        }
	}
	
	
	
	
	// legacy section below
	
	
	/*
	public static void main(String[] args) {
		// set keystore and trust store location/home/ismail/Repos/cs_544/source/RAUC/src

		System.setProperty("javax.net.ssl.keyStore", "/home/ismail/sslserverkeys");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		System.setProperty("javax.net.ssl.trustStore", "/home/ismail/sslservertrust");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		// create socket
		SSLServerSocket sslserversocket = null;
		SSLSocket sslsocket = null;
		// create a listener on port 9999
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
			while (true) {
				// blocks the program when no socket floats in
				sslsocket = (SSLSocket) sslserversocket.accept();
				System.out.println("sslsocket:" + sslsocket);
				// assign a handler to process data
				new SocketHandler(sslsocket);
			}
		} catch (Exception e) {
			try {
				sslsocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}*/
}
