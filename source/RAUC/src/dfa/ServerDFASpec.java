package dfa;

/*
* Ismail Kuru - Max Mattes - Lewis Cannalongo
* 
* File : Server.java
* Aim : This file includes the machinery specified for
* Server DFA in design document.
*
* */

import components.Component;
import components.Factory;
import pdu.Message;
import pdu.MessageImpl.AckMessage;
import pdu.MessageImpl.RequestReceivedMessage;
import pdu.MessageImpl.TemporaryErrorMessage;
import pdu.MessageImpl.UserAuthenMessage;

import java.util.ArrayList;
import java.util.Map;

import static dfa.DFAState.*;
import static pdu.MessageType.OP_AUTH;

public class ServerDFASpec extends DFASpec {
    // Ideally we'd be connecting to a real database for these, but this works for the prototype
    // automobile --> components  map
    // Loaded inside authentication process
    private Map<String, ArrayList<Component>> componentMap;
    private Map<String, String> users;
    private Factory db;

    public ServerDFASpec(Factory db, Map<String, String> users) {
        this.db = db;
        this.users = users;
        this.state = DFAState.INIT;
    }

    /**
     * Allows validation of authentication or error in AUTH state, and transitions DFA
     *
     * @param m Message to send to client
     * @return true if message can be sent
     */
    @Override
    protected boolean sendAuth(Message m) {
        switch (m.getMessageType()) {
            case OP_SUCCESS:
                setState(ESTABLISHED);
                return true;
            case OP_ERROR:
            case OP_TMP_ERROR:
                setState(INIT);
                return true;
            default:
                return false;
        }
    }

    /**
     * Allows response to command execution in WAITCMD state, and transitions DFA
     *
     * @param m Message to send to client
     * @return true if message can be sent
     */
    @Override
    protected boolean sendWaitcmd(Message m) {
        switch (m.getMessageType()) {
            case OP_ERROR:
            case OP_TMP_ERROR:
            case OP_COMMAND_RECEIVED:
                setState(ESTABLISHED);
                return true;
            default:
                return false;
        }
    }

    /**
     * Allows response to query execution in WAITQRY state, and transitions DFA
     *
     * @param m Message to send to client
     * @return true if message can be sent
     */
    @Override
    protected boolean sendWaitqry(Message m) {
        switch (m.getMessageType()) {
            case OP_ERROR:
            case OP_TMP_ERROR:
            case OP_INFO:
                setState(ESTABLISHED);
                return true;
            default:
                return false;
        }
    }

    /**
     * Confirms authentication and sends response, and transitions DFA
     *
     * @param m Message received from client
     * @return Message to send to client
     */
    @Override
    protected Message receiveInit(Message m) {
        if (m.getMessageType() != OP_AUTH) {
            return receiveUnexpected(m);
        }
        UserAuthenMessage am = (UserAuthenMessage) m;
        setState(AUTH);
        // Scary string password matching for PROTOTYPE ONLY. THIS SHOULD NEVER HAPPEN IN PRODUCTION CODE
        if (users.containsKey(am.getUserName()) && users.get(am.getUserName()).equals(am.getPassword())) {
            componentMap = db.loadUserDB(am.getUserName());
            // Build response
            return new AckMessage();
        } else {
            return new TemporaryErrorMessage();
        }
    }

    /**
     * Executes query and returns response, or executes command and returns success/failure
     *
     * @param m Message received from client
     * @return Message to send to client
     */
    @Override
    protected Message receiveEstablished(Message m) {
        switch (m.getMessageType()) {
            case OP_COMMAND:
                setState(WAITCMD);
                // TODO: actually execute command
                return new RequestReceivedMessage();
            case OP_QUERY:
                setState(WAITQRY);
                // TODO: get requested information
                // Not yet implemented, just return temp error
                return new TemporaryErrorMessage();
            default:
                return receiveUnexpected(m);
        }
    }
}
