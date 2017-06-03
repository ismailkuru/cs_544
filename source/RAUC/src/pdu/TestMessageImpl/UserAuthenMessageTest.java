package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.junit.Test;

import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.UserAuthenMessage;

public class UserAuthenMessageTest {

	// TODO: this entire thing relies on the old serialization
	/*@Test
	public void test() throws Exception {

		UserAuthenMessage um = new UserAuthenMessage("ismail", "1234");
		UserAuthenMessage m = null;
		
		 // create a new output stream
        OutputStream os = new FileOutputStream("test.txt");

        // craete a new input stream
        InputStream is = new FileInputStream("test.txt");
		
			
		writeSocket(os, um.toBytes());
		byte[][] bt = readSocket(is);
		
		
		m  = (UserAuthenMessage) MessageFactory.createMessage(bt);
		System.out.println(m.toString());
		assertNotNull(m);
		
		assertNotNull(um);
		//System.out.println(m.getUserName());
	}

	
	public static byte[][] readSocket(InputStream in) throws IOException, ClassNotFoundException {
	    ObjectInputStream ois = new ObjectInputStream(in);
	    return (byte[][]) ois.readObject();
	}

	public static void writeSocket(OutputStream out, byte[][] board) throws IOException {
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(board);
	}*/
}
