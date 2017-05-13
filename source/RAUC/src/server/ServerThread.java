package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



import javax.net.ssl.SSLSocket;


public class ServerThread extends Thread {
	//TEST MESSAGE
	static final String EXIT = "EXIT";	
	//
	
	private SSLSocket socket;
	
	public ServerThread(SSLSocket socket) {
	        this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
        OutputStreamWriter out = null;
     
        try {
        	
        	//Connection-Establishment
            System.out.println("\nConnected to client");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            
            //[TEST] : This is test of sending/receiving in between client/server
            String line = null;

    		if (((line = in.readLine()) != null)) {
    			System.out.println(line);
    			
    			out.write("\nServer side is ready.\n\n");
    			out.flush();
    		}
    		
    		//[TEST] : do some message exchange - client/server
    		String temp;
            boolean running = true;
            // Handles all of the requests sent from the clients.
            while(running){
                temp = in.readLine();
                if(temp.equals("EXIT")) {
                    System.out.println("Exiting the program.");
                    running = false;
                }else{
                    System.out.println("Unexpected input.");
                }
            }
    		//[TEST] ends
    		
           
            
            //[TODO]:Messages-From
            // This part is going to call process packet etc. and will replace [test] above
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
            	if(socket !=null){
            		System.out.println("Closing Socket");
            		socket.close();
            	}
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
}