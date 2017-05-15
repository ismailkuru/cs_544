package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;
import components.Command;
import components.ComponentImpl.AC;

import pdu.MessageImpl.UtilityControlReqMessage;


public class SocketHandler extends Thread {
	private SSLSocket socket = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;

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
				//read a line
				str = br.readLine();
				//[TEST - Mini test showing how one of the process methods in DFA (w/o packet format
				// checking) looks like etc... ]
				Gson gson = new Gson();
				UtilityControlReqMessage as = gson.fromJson(str, UtilityControlReqMessage.class);
				Command cmd = Command.createCommand(as);
				System.out.println("Command: autoID "  + cmd.getAutoId() + " - attrb " + cmd.getAttribute() );
				//[TODO] component type should be passed as String ?
				AC ac = new AC("1",cmd.getComponentType(), "0");
				
				ac.applyCommand(cmd);
				System.out.println();
				System.out.println();
				System.out.println();
				
				System.out.println("Value of attrib 0 is now " +  ac.getValueOfAttrb("0"));
				//[TEST -ENDS]
				
				//if it is "END", disconnect [TODO: All these are going to be pushed neatly
				// to SERVER DFA. process message is going to be called only]
				if (str.equals("END")) {
					pw.println("END");
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
					break;
				}
				//else, send "Message Received"
				pw.println("Message Received");
				pw.flush();
				System.out.println("Client Socket Message:" + str);
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