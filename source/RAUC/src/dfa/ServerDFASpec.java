package dfa;

import components.Command;
import components.Component;
import components.Factory;
import components.QueryExecutor;
import pdu.Message;
import pdu.MessageImpl.AckMessage;
import pdu.MessageImpl.QueryResultMessage;
import pdu.MessageImpl.RequestReceivedMessage;
import pdu.MessageImpl.TemporaryErrorMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.PermanentErrorMessage;

import java.util.ArrayList;
import java.util.Map;

import static dfa.DFAState.*;
import static pdu.MessageType.OP_AUTH;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ServerDFASpec.java
* **************************************************
* Definition: This file includes the definitions for specification of 
* the Server side functionality. It is sub type of DFASpec.java.
* *******************************************************
* Requirements:
* - STATEFUL :STATEFUL : Entire file defines valid client side states and admissible 
* valid transitions between these states. All functions([Details below ServerSpecDFA 
* sendAuth, sendWaitcmd, sendWaitqry, receiveInit,receiveEstablished]) inherited from
*  DFASpec.java defines the valid transition relation between server side states, 
*  setting state of server dfa in each of these functions, triggered by Message m.
* - SERVICE : Server side functionality is specified in this file.
* 
* ==============================================================================
*/
public class ServerDFASpec extends DFASpec {
    /*
     *  SERVICE:Ideally we'd be connecting to a real database for these, but this works for the prototype
     *  automobile --> components  map Loaded inside authentication process
     */
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
            	//STATEFUL:Authentication is provided
                setState(ESTABLISHED);
                return true;
            case OP_ERROR:
            case OP_TMP_ERROR:
            	//STATEFUL:Authentication fails
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
            	//STATEFUL:Set state connection established
            	//         now commands can be received
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
            	//STATEFUL:Set state connection established
            	//         now commands can be received 
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
        //STATEFUL:Server is in state to receive authentication requests
        setState(AUTH);
        // Scary string password matching for PROTOTYPE ONLY. THIS SHOULD NEVER HAPPEN IN PRODUCTION CODE
        if (users.containsKey(am.getUserName()) && users.get(am.getUserName()).equals(am.getPassword())) {
        	// populate this session with the components (autos/components/etc) that are tied to this user
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
            	//STATEFUL: Server waits commands
                setState(WAITCMD);
			try {
				Command cmd = Command.createCommand(m);
				String autoId = cmd.getAutoId();
				ArrayList<Component> lcomps = componentMap.get(autoId);
				Component compApplyTo = null;
				for(Component cmp : lcomps){
					if(cmp.getComponentCode().equals(cmd.getComponentType()))
						compApplyTo = cmp;
				}
				compApplyTo.applyCommand(cmd);
					
			} catch (Exception e) {
				System.out.println("Error : Invalid Command Creation");
			}
                return new RequestReceivedMessage();
            case OP_QUERY:
            	//STATEFUL: Server waits query requests
                setState(WAITQRY);
                String qres = null;
                try {
                	qres = QueryExecutor.executeQuery(m, componentMap);
                } catch (Exception e) {
                	// TODO Auto-generated catch block
                	System.out.println("Error : Invalid Query Creation");
                	e.printStackTrace();
                	// a bad query returns a permanent error - we don't return info on cars/components/attributes that don't exist
                	return new PermanentErrorMessage();                
                }
                return new QueryResultMessage(qres);
            default:
                return receiveUnexpected(m);
        }
    }
}
