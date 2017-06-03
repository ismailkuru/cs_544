package main;

import pdu.MessageImpl.UserAuthenMessage;

public class ClientTest {
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "/home/maxm/Documents/cs_544/cert/sslclienttrust");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        Client client = new Client("localhost", 1500, "user", "pass");
        System.out.println("Starting");
        client.start();
        System.out.println("Sending message");
        client.sendMessage(new UserAuthenMessage("user", "pass"));
    }
}
