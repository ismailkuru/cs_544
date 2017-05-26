package main;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
public class Server {
	/*
	 * main - listen a specific port. When receiving socket, start a new thread
	 * to process data so that the program can process multiple socket at the
	 * same time
	 */
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