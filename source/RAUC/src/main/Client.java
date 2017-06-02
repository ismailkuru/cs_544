package main;


import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.UserAuthenMessage;
import specs.SpecImpl.ClientDFASpec;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;




public class Client {
	
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
				if (br != null) {
					br.close();
				}
				if (pw != null) {
					pw.close();
				}
				if (sslsocket != null) {
					sslsocket.close();
				}
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
		//System.setProperty("javax.net.ssl.trustStore", "/home/ismail/sslclienttrust");
		// TODO: REMOVE ALL RELATIVE PATHS!
		System.setProperty("javax.net.ssl.trustStore", "/home/maxm/Documents/cs_544/cert/sslclientkeys");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		try {
			sendSocket("localhost", 9999, "ismail", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}