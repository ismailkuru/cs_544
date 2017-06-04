package dfa;

/*
* Ismail Kuru - Max Mattes - Lewis Cannalongo
* 
* File : ClientDFA.java
* Aim : This file includes the machinery specified for
* Client DFA in design document.
*
* */

import pdu.Message;

import static dfa.DFAState.*;

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
                setState(WAITCMD);
                return true;
            case OP_QUERY:
                setState(WAITQRY);
                return true;
            default:
                return false;
        }
    }


    /**
     * Receives server response after authentication sent. Moves to ESTABLISHED if successful, else return to INIT
     *
     * @param m Message received from server
     * @return m if valid message, else error
     */
    @Override
    protected Message receiveAuth(Message m) {
        switch (m.getMessageType()) {
            case OP_SUCCESS:
                // Successfully authenticated
                setState(ESTABLISHED);
                return m;
            case OP_TMP_ERROR:
            case OP_ERROR:
                // Failed authentication; retry
                setState(AUTH);
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
            case OP_TMP_ERROR:
            case OP_ERROR:
                // Successfully executed command or received error from server
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
