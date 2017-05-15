# cs_544


##Usage
1. Compile & prepare files

  ```
  javac -d ../bin src/main/*.java
  cp src/ssl* ../bin
  cd ../bin
  ```
2. Start server & client

  server:
  ```
  java main.SocketListener
  ```
  client:
  ```
  java main.Client
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
