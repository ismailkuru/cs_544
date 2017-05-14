package server;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/*
 * Server Executor
 * 
 * */
public class Server {
    private static final int SERVER_PORT = 8080;
    // These strings are the names of the keystore and truststore files, as weIl
    // as the password for accessing these two files (both files have the same password in this example)
    static final String KEYSTORE = "/home/ismail/Repos/cs_544/source/RAUC/src/server/serverkeystore.ks"; // [TODO] change them to relative paths. Eclipse screwed up
    static final String TRUSTSTORE = "/home/ismail/Repos/cs_544/source/RAUC/src/server/servertruststore.ks";// [TODO] change them to relative paths. Eclipse screwed up
    static final String STOREPASSWD = "123456";
    static final String ALIASPASSWD = "123456";
    
    public static void startServer() throws IOException, KeyStoreException {
    	
    	SSLServerSocket listener = null;
        try {
        	// Set up security. The following statements create an empty keystore and an empty truststore
            // objects and then load them with the contents of the program's keystore and truststore files.
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(new FileInputStream(KEYSTORE), STOREPASSWD.toCharArray() );
            KeyStore ts = KeyStore.getInstance("JCEKS");
            ts.load(new FileInputStream(TRUSTSTORE), STOREPASSWD.toCharArray() );

            // The SSL connection will require access to encryption keys and certificates. For that reason,
            // factory objects to create both KeyManager and TrustManager objects are created and then
            // initialized with the KeyStore and TrustStoreobjects:
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, ALIASPASSWD.toCharArray() );
            TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
            tmf.init(ts);

            // Create an SSLContext object. The input parameter, TLS, indicates that we want to use the
            // Transport Layer Security standard. Once the SSLContext object is created,
            // it is initialized with all the KeyManager and TrustManager objects that the factory
            // objects support. The initialization method will also accept a third parameter, a random
            // number used in the process of generating the secret key that the SSL handshake will use.
            // In this case the null reference is input, so the default random number seed will be used:
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null );
            
            
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
            
        	listener = (SSLServerSocket)ssf.createServerSocket(SERVER_PORT);
        	listener.setEnabledCipherSuites(listener.getSupportedCipherSuites() );
            listener.setNeedClientAuth(true);
            System.out.println("The server is online and waiting for incoming connections.");
            
            while (true) {
            	
                new ServerThread((SSLSocket) listener.accept()).start();
            }
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        while (true) {
            try {
            	startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
