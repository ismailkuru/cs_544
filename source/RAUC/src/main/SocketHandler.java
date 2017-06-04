package main;

import components.Factory;
import dfa.ServerDFASpec;
import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageType;

import javax.net.ssl.SSLSocket;
import java.io.*;

public class SocketHandler extends Thread {
	private SSLSocket socket = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private ServerDFASpec dfa;
	/*
	 * Constructor - initialize variables
	 * 
	 * @param s - an ssl socket created by SocketListener
	 */

	public SocketHandler(SSLSocket s) {
		socket = s;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")), true);
			//[TODO] we don't need to pass whole db to each user but keep this for a while.
			dfa = new ServerDFASpec(new Factory());
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while (true) {
			
			// read user input
			String input = null ;
			try{

				while (input == null) {
					input = br.readLine();
					if (input == null) {
						//client closed connection.
						socket.close();
						return;
					}
				}
				// process client message and generate response
				Message inMsg = MessageFactory.createMessage(input);
				Message outMsg = dfa.receive(inMsg);

				// check for shutdown / error				
				if (outMsg.getMessageType() == MessageType.OP_SHUTDOWN ||	outMsg.getMessageType() == MessageType.OP_ERROR) {
					//pw.println("END");
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
					break;
				}
				//else, send "Message Received"
				// send response to client
				pw.write(outMsg.toString());
				pw.flush();
				pw.write("\n");
				//pw.println("Message Received");
				pw.flush();
				} catch (Exception e) {
					try {
						br.close();
						pw.close();
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			
			}
		}
}
