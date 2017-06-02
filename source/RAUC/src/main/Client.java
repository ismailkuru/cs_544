	package main;


	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.OutputStreamWriter;
	import java.io.PrintWriter;
	import java.io.*;
	import java.net.*;
	import java.util.*;

	import javax.net.ssl.SSLSocket;
	import javax.net.ssl.SSLSocketFactory;

	import pdu.Message;
	import pdu.MessageFactory;
	import pdu.MessageImpl.*;
	import specs.*;
	import specs.SpecImpl.ClientDFASpec;




	@SuppressWarnings("unused")
	public class Client {
		// for I/O
		private ObjectInputStream sInput;		// read from socket
		private ObjectOutputStream sOutput;		// write to socket
		private SSLSocket socket;	

		// connection details
		private String server, username, pass;
		private int port;
		
		// if using a gui
		private ClientGUI cg;
		
		// dfa to make client respect RAUC protocol
		private ClientDFASpec dfa;
		
		/*
		 * Common constructor / Constructor used via a GUI 
		 * in CLI mode the ClientGUI parameter is null
		 */
		Client(String server, int port, String username, String pass, ClientGUI cg) {
			this.server = server;
			this.port = port;
			this.username = username;
			this.pass = pass;
			// save if we are in GUI mode or not
			this.cg = cg;
			dfa = new  ClientDFASpec(username,pass);
		}
		
		// constructor for CLI
		Client(String server, int port, String username, String pass) {
			// which calls the common constructor with the GUI set to null
			this(server, port, username, pass, null);
		}
		
		
		// establishes the connection to the provided address
		public boolean start() {
			// establish the connection
			try {
				SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
				socket = (SSLSocket) sf.createSocket(server, port);
				display("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			}
			// end if connection cannot be established
			catch (Exception e) {
				display("Error connecting to server: " + e);
				return false;
			}
			
			// establish the data streams
			try {
				sInput  = new ObjectInputStream(socket.getInputStream());
				sOutput = new ObjectOutputStream(socket.getOutputStream());
			}
			catch (IOException eIO) {
				display("Error creating in/out streams: " + eIO);
				return false;
			}
			
			// all further DFA state changes will be handled by dfa.process(message)
			dfa.setState(DFAState.SC_INIT);
			dfa.setState(DFAState.S_AWAITS_AUTHEN_REQUEST);
			
			// create the thread that listens for server responses
			new ListenThread().start();
			
			//TODO use the message factory
			// send the authentication message
			Message auth = new UserAuthenMessage(username, pass);
			sendMessage(auth);

			return true;
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

		
		// prints a string to output
		protected void display(String msg) {
			if(cg == null)
				System.out.println(msg);      // print to sout in in CLI mode
			else
				cg.display(msg + "\n");		// append to the ClientGUI conversation window
		}
		
		// prints a message to output
		protected void display(Message msg) {
			display("<<< " + msg.toString());
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
				while(true) {
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
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// if dfa.state == ok to send, reenable sending of messages from client
			}
		}
		
		
		
		// extraneous code below this line		
		// -------------------------------------------------------

		
		/* here implement main() for CLI
		
		
		
		
		
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
				br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream(),"UTF-8"));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream(),"UTF-8")));
				ClientDFASpec dfa = new  ClientDFASpec(uname,pass);
				
				
				//[TEST - THIS IS A MINI CLIENT -- WILL BE REPLACED WITH while(true){dfa.process()} ] 
				//This mimics one of messages processing of dfa.process
				UserAuthenMessage uauth = new UserAuthenMessage(uname, pass);
				
				pw.println(uauth.toString() + "\n");
				pw.flush();
				
				str = (br.readLine() + "\n");
				//[TODO: Test - Json String validation and creating java object]
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
		
		/*
		 * main - send a socket from system command
		 * 
		 * @param args[0] target address
		 * @param args[1] target port
		 * @param args[2] message
		 * 
		 * @print received responses
		 */
		public static void main(String[] args) {
			System.setProperty("javax.net.ssl.trustStore", "/home/ismail/sslclienttrust");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			
			sendSocket("localhost", 9999, "ismail", "123" );
		}
	}