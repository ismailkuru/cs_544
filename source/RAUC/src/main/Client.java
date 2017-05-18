package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.google.gson.Gson;

import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageType;
import pdu.MessageImpl.AuthenAckMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import specs.SpecImpl.ClientDFASpec;




public class Client {
	
	ClientDFASpec dfa = null;
	/*
	 * SendSocket - send a socket to a target
	 * 
	 * @param server - target server
	 * @param port - target port
	 * @param message - message
	 * 
	 * @return - received responses
	 */
	public static void CommSocket(String server, int port, String uname, String pass) {
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = "";
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(server, port);
			System.out.println("sslsocket=" + sslsocket);
			
			br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));
			
			ClientDFASpec dfa = new ClientDFASpec(uname, pass);
			
			UserAuthenMessage authm = new UserAuthenMessage(uname, pass);
			
			String authconfim = br.readLine();
			
			Message authresp = MessageFactory.createMessage(authconfim);
			
			if(authresp.getMessageType() == MessageType.OP_SUCCESS){
			
				while (true) {
					// read next message from input
					String line = br.readLine();
					if (line == null) return;
					
					// process input message
					// ---------------------
					Message inMsg = MessageFactory.createMessage(line);
					
					//shutdown
					// handle shutdown
					if (inMsg.getMessageType() == MessageType.OP_SHUTDOWN ||
							inMsg.getMessageType() == MessageType.OP_ERROR) {
						
						break;
					}
					// process output message
					// ---------------------
					Message outMsg = dfa.process(inMsg);
					// error
					if (outMsg == null) {
						throw new RuntimeException("Invalid Out Message.");
					}
					
					// send message to server
					else {
						pw.print(outMsg.toString());
						pw.flush();
						
						// shutdown if user selected to shutdown
						if (outMsg.getMessageType() == MessageType.OP_SHUTDOWN) {
							break;
						}
					}					
				}
		  }			
		  else{
			System.out.println("Authentication is not provided. ");
			
		  }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				sslsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
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
		
		CommSocket("localhost", 9999, "ismail", "123");
	}
}

/////////////////

/*****MINI TEST********/
//[TEST] Mini Client
//UtilityControlReqMessage umcr = new UtilityControlReqMessage("1", "0" , "0", "4");
//Gson gson = new Gson();
//String json = gson.toJson(umcr);
//System.out.println(json);
//System.out.println();
//System.out.println();
//System.out.println();
//Send this string to server
//[TEST] Mini Client ends
/****** MINI TEST ENDS****/