# CS 544 - Group 2
Note: Runs on Linux (tested on Debian and Kali Linux)

Group Grading: Ismail:10 Max:10 Lewis:10
## To Run
* `ant all` to build
* `ant server` to run commandline server, or `ant serverGUI` for GUI server
* `ant client` to run quick test of client. `ant clientGUI` will open up the
  GUI for interactive message sending

Note that the server must be running for the client to be able to connect

## Robustness
The client and server are both written to specification and thus should be no
less robust than what is written there. They should both be capable of handling
messages longer or shorter than the size specified in the header or content
chunk by returning an error to the peer. Any garbage data (random bits) will
result in an error unless it validly fits the specified protocol. The security
provided by TLS prevents any unauthorized access, making the server robust
against those types of intrusions, as discussed in the paper. Ultimately the
one major vulnerability is the same shared with every TLS service due to the
overhead: DOS. This is easier to handle outside of the server itself through
firewalls and other methods of blocking malicious IPs, so no protections have
been instated in the code here.
