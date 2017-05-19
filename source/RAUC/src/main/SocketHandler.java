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
import pdu.MessageImpl.AuthenAckMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import specs.SpecImpl.ServerDFASpec;

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
			dfa = new ServerDFASpec();
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			String str;
			try {

				str = br.readLine();
				
				//[THIS IS MINI TEST] : will be repaced with dfa.process() ////////////////
				//TODO: Test - Json String validation and creating java object] Message m = MessageFactory.createMessage(str);
				System.out.println("Message received from client:" + str);
				
				if (str.equals("END")) {
					pw.println("END");
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
					break;
				}
				
				//else, send "Message Received"
				AuthenAckMessage authack = new AuthenAckMessage(null);
				System.out.println(authack.toString());
				
				pw.println(authack.toString());
				pw.flush();
				//[MINI TEST ENDS] /////////////////////////
				
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
