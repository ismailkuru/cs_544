package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;
import components.Command;
import components.ComponentImpl.AC;

import pdu.*;
import pdu.MessageImpl.UtilityControlReqMessage;
import specs.SpecImpl.ServerDFASpec;


public class SocketHandler extends Thread {
	private SSLSocket socket = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private ServerDFASpec dfa;
	private Boolean isDown = false;
	/*
	 * Constructor - initialize variables
	 * 
	 * @param s - an ssl socket created by SocketListener
	 */
	public SocketHandler(SSLSocket s) {
		socket = s;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			dfa = new ServerDFASpec();
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			// read user input
			String input = null;
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
				Message outMsg = dfa.process(inMsg);
				
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
				pw.println(outMsg.toString());
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





/*********MINI TEST*********/
//[TEST - Mini test showing how one of the process methods in DFA (w/o packet format
// checking) looks like etc... ]
//Gson gson = new Gson();
//UtilityControlReqMessage as = gson.fromJson(str, UtilityControlReqMessage.class);
//Command cmd = Command.createCommand(as);
//System.out.println("Command: autoID "  + cmd.getAutoId() + " - attrb " + cmd.getAttribute() );
//[TODO] component type should be passed as String ?
//AC ac = new AC("1",cmd.getComponentType(), "0");

//ac.applyCommand(cmd);
//System.out.println();
//System.out.println();
//System.out.println();

//System.out.println("Value of attrib 0 is now " +  ac.getValueOfAttrb("0"));
//[TEST -ENDS]
//[TODO: All these are going to be pushed neatly
	// to SERVER DFA. process message is going to be called only]
/*******MINI TEST ENDS**********/