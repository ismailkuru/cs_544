# cs_544


##Usage
1. Compile & prepare files

  ```
  Make sure that you have externally linked your gson.jar and org.json.jar. 
  ```
  
  ```
  TODO: Giving complete paths of certificates in source.
  ```
  
  ```
  Use eclipse and export as runnable-jar. Before this, make sure that 
  you set two mains (Client.java, SocketListener.java) in you Run-Configurations.
  ```
  
  
  ```
  Export Client and Server (whose main is in SocketListener.java) as executable
  jars. Reference : http://www.wikihow.com/Create-an-Executable-File-from-Eclipse
  ```
2. Start server & client

  server:
  ```
  java -jar Server
  ```
  client:
  ```
  java -jar Client
  ```
3. Customize SSL

  Create a client keystore file
  ```
  keytool -genkey -alias sslclient -keystore sslclientkeys
  ```
  Export client keystore as certification
  ```
  keytool -export -alias sslclient -keystore sslclientkeys -file sslclient.cer
  ```
  Create a server keystore file
  ```
  keytool -genkey -alias sslserver -keystore sslserverkeys
  ```
  Import client's certification into server's truststore
  ```
  keytool -import -alias sslclient -keystore sslservertrust -file sslclient.cer 
  ```
  Import server's certification into client's truststore
  ```
  keytool -import -alias sslserver -keystore sslclienttrust -file sslserver.cer 
  ```
  To view your keystore or trust
  ```
  keytool -list -keystore sslclienttrust
  ```
