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

import pdu.MessageImpl.UtilityControlReqMessage;




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
	public static String sendSocket(String server, int port, String message) {
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
			pw.println(message);
			pw.flush();
			str += (br.readLine() + "\n");
			System.out.println(str);
			pw.println("END");
			pw.flush();
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
		return str;
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
		//[TEST] Mini Client
		UtilityControlReqMessage umcr = new UtilityControlReqMessage("1", "0" , "0", "4");
		Gson gson = new Gson();
		String json = gson.toJson(umcr);
		System.out.println(json);
		System.out.println();
		System.out.println();
		System.out.println();
		//Send this string to server
		//[TEST] Mini Client ends
		System.out.println(sendSocket("localhost", 9999, json ));
	}
}

