package dfa;

import pdu.Message;

import static dfa.DFAState.*;

/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ClientDFASpec.java
* **************************************************
* Definition: This file includes the definitions for specification of the 
* Client side functionality. It is sub type of DFASpec.java.
* *******************************************************
* Requirements:
* - CLIENT : Client side functionality is specified in this file.
* - STATEFUL : Entire file defines valid client side states and admissible 
* valid transitions between these states. All functions
* ([Details below: ClientSpecDFA sendInit, receiveAuth, sendEstablished, 
* receiveAuth, receiveWaitcmd, receiveWaitqry])inherited from DFASpec.java defines 
* the valid transition relation between client side states,
* setting state of client dfa in each of these functions, triggered by Message m.
* 
* ==============================================================================
*/
public class ClientDFASpec extends DFASpec {
    public ClientDFASpec() {

    }

    /**
     * Allows sending of client authentication messages in the INIT state
     *
     * @param m Message to send to server
     * @return true if message is correct type
     */
    @Override
    protected boolean sendInit(Message m) {
        switch (m.getMessageType()) {
            case OP_AUTH:
            	//STATEFUL; Client can send Authentication message
                setState(AUTH);
                return true;
            default:
                return false;
        }
    }

    /**
     * Allows sending of commands and queries in ESTABLISHED state
     *
     * @param m Message to send to server
     * @return true if message is correct type
     */
    @Override
    protected boolean sendEstablished(Message m) {
        switch (m.getMessageType()) {
            case OP_COMMAND:
            	//STATEFUL:Client can send command to server
                setState(WAITCMD);
                return true;
            case OP_QUERY:
            	//STATEFUL:Client can send query to server
                setState(WAITQRY);
                return true;
            default:
                return false;
        }
    }


    /**
     * Receives server response after authentication sent. 
     * Moves to ESTABLISHED if successful, else return to INIT
     *
     * @param m Message received from server
     * @return m if valid message, else error
     */
    @Override
    protected Message receiveAuth(Message m) {
        switch (m.getMessageType()) {
            case OP_SUCCESS:
                //STATEFUL: Successfully authenticated
                setState(ESTABLISHED);
                return m;
            case OP_TMP_ERROR:
            	setState(INIT);
            	return m;
            case OP_ERROR:
                //STATEFUL Failed authentication; retry
                setState(INIT);
                return m;
            default:
                // Something went wrong, received unexpected message
                return receiveUnexpected(m);
        }
    }

    /**
     * Receives server response after command sent. Moves to ESTABLISHED (if message type is correct)
     *
     * @param m Message received from server
     * @return m if valid message, else error
     */
    @Override
    protected Message receiveWaitcmd(Message m) {
        switch (m.getMessageType()) {
            case OP_COMMAND_RECEIVED:
            	//STATEFUL : Connection is establised
            	setState(ESTABLISHED);
            case OP_TMP_ERROR:
            case OP_ERROR:
                // STATEFUL: Successfully executed command or received error from server
                setState(ESTABLISHED);
                return m;
            default:
                // Something went wrong, received unexpected message
                return receiveUnexpected(m);
        }
    }

    /**
     * Receives server response after query sent. Moves to ESTABLISHED (if message type is correct)
     *
     * @param m Message received from server
     * @return m if valid message, else error
     */
    @Override
    protected Message receiveWaitqry(Message m) {
        switch (m.getMessageType()) {
            case OP_INFO:
            case OP_TMP_ERROR:
            case OP_ERROR:
                // Successfully received query response or received error from server
                setState(ESTABLISHED);
                return m;
            default:
                // Something went wrong, received unexpected message
                return receiveUnexpected(m);
        }
    }
}
