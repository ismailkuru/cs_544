package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import main.ServerGUI.ConnectionPanel;
import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.TerminationMessage;
import specs.SpecImpl.ServerDFASpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.net.*;
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
				this.identifier = socket.getInetAddress() + ":" + socket.getPort();
				display("Connection accepted " + identifier);
				
				sInput  = new ObjectInputStream(socket.getInputStream());
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				
				// create server dfa
				// set dfa to S_AWAITS_AUTHEN_REQUES
				
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
					receiveMessage((byte[][]) sInput.readObject());
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
		private void receiveMessage(byte[][] bb) {
			try {
			// reassemble bytestream into message 
			Message inMsg = MessageFactory.createMessage(bb);
			// process message to make sure it is valid
			Message outMsg = dfa.process(inMsg);
			// display error or valid message
			display(outMsg);
			sendMessage(outMsg);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			// if dfa.state == ok to send, reenable sending of messages from client
		}
		
		protected void sendMessage(Message msg) {
			// send the message across the connection and log it in output window
			try {
				if (true /*dfa.send(msg)*/) { // if current state allows for sending a message
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
		
		protected void display(Message msg) {
			display(msg.toString());
		}
		
		protected void disconnect() {
			// send the termination message
			//TODO use MessageFactory
			Message m = new TerminationMessage();
			sendMessage(m);
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
	}
}
