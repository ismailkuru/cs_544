package client;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Scanner;


/**
 *
 */
class ClientThread extends Thread {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 8080;
    
    private static final String KEYSTORE = "/home/ismail/Repos/cs_544/source/RAUC/src/client/PIERkeystore.ks"; // [TODO] change them to relative paths. Eclipse screwed up
    private static final String TRUSTSTORE = "/home/ismail/Repos/cs_544/source/RAUC/src/client/PIERtruststore.ks";// [TODO] change them to relative paths. Eclipse screwed up
    private static final String STOREPASSWD = "123456";
    private static final String ALIASPASSWD = "123456";

    //TEST MESSAGE
    static final String EXIT = "EXIT";
    //
    
    public ClientThread() {

    }
    
    
    @Override
    public void run() {
    
        SSLSocket socket = null;
        BufferedReader in = null;
        OutputStreamWriter out = null;
        
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
        	
        	// Set up security. The following statements create an empty keystore and an empty truststore
            // objects and then load them with the contents of the program's keystore and truststore files.
            KeyStore ks = KeyStore.getInstance( "JCEKS" );
            ks.load( new FileInputStream( KEYSTORE ), STOREPASSWD.toCharArray() );
            KeyStore ts = KeyStore.getInstance( "JCEKS" );
            ts.load( new FileInputStream( TRUSTSTORE ), STOREPASSWD.toCharArray() );

            // The SSL connection will require access to encryption keys and certificates. For that reason,
            // factory objects to create both KeyManager and TrustManager objects are created and then
            // initialized with the KeyStore and TrustStore objects:
            KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
            kmf.init(ks, ALIASPASSWD.toCharArray() );
            TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
            tmf.init(ts);
            SSLContext sslContext = SSLContext.getInstance( "TLS" );
            sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );
        	
            socket = (SSLSocket) sf.createSocket(SERVER_HOSTNAME, SERVER_PORT);
            socket.setEnabledCipherSuites(socket.getEnabledCipherSuites());
          
            in = new BufferedReader(new InputStreamReader(socket.getInputStream() , "UTF-8") );
            out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            
            // TEST-WRITER
            PrintWriter socketOutput = new PrintWriter(socket.getOutputStream(), true );
            //
            
            System.out.println("--------------------------------------------");
            System.out.println("The SSL/TLS handshake was completed.");
            System.out.println("The client is now connected to the server.");
            System.out.println("--------------------------------------------");
            
            //[TEST] : Do some message exchange - client/server   
            // Used to read the user input.
            Scanner scanner = new Scanner(System.in);

            String command;
            boolean running = true;

            // Handles incoming commands from the server.
            while(running){
                // Display the menu.
                displayMenu();

                // Read the input from the user.
                command = scanner.nextLine();

                if(command.equals("1")) {
                	
                    socketOutput.println(EXIT);
                    System.out.println("Exiting the program.");
                    System.exit(0);
                }else{
                    System.exit(0);
                    running = false;
                }
            }
            scanner.close();
            //[TEST]ends
            
                    
            //[TODO: Put message into a box(jsonelement) then send it ]
    		//[TODO: Message processing endsENDS]
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                if (socket != null) {
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
 
    
    
    /** Display the options.
     */
    private void displayMenu(){
        System.out.println("\n" + "-----------------");
        System.out.println("Enter the nr to:");
        System.out.println("1: Exit");
        System.out.println("-----------------");
    }
}

