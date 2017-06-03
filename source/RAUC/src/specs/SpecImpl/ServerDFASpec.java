package specs.SpecImpl;

/*
* Ismail Kuru - Max Matthes - Lewis Cannalongo
* 
* File : Server.java
* Aim : This file includes the machinery specified for
* Server DFA in design document.
*
* */
import java.util.ArrayList;
import java.util.HashMap;

import components.Command;
import components.Component;
import components.Factory;
import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageType;
import pdu.MessageImpl.*;
import pdu.MessageImpl.AckMessage;
import pdu.MessageImpl.PermanentErrorMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import specs.DFASpec;
import specs.DFAState;

public class ServerDFASpec extends DFASpec{
	/**
	 * Confirm message for client actions
	 */
	private Message _response;
	private Factory _db;
	
	//automobile --> components  map
	HashMap<String, ArrayList<Component>> _acomps; // Loaded inside authentication process 

	public ServerDFASpec(Factory db){
		_db = db;
		//[TODO:] REMOVE THIS STATE SETTING : JUST FOR TESTING !
		//Setting state is going to be done inside process methods.
		this.state = DFAState.S_AWAITS_AUTHEN_REQUEST;
	}
	
	/**
	 *  called when server sends a message to update DFA conversation state.
	 * @param m the message to send
	 * @return whether or not the message is valid to send
	 */
	public boolean send(Message m) {
		MessageType mt = m.getMessageType();
		if (mt.equals(MessageType.OP_SHUTDOWN)) { // client may disconnect at any time
			setState(DFAState.CLOSED);
			return true;
		}
		switch (state) {
		
			case S_AWAITS_AUTHEN_REQUEST: { // only sending an OP_success message can change state while server is awaiting auth
				if (mt.equals(MessageType.OP_SUCCESS)) { // if we are sending an auth ack message
					setState(DFAState.S_AWAITS_REQUEST); // we will be waiting for a command
					return true;
				} else if (mt.equals(MessageType.OP_ERROR) || mt.equals(MessageType.OP_TMP_ERROR)) { // if we are sending an error message
					setState(DFAState.S_AWAITS_AUTHEN_REQUEST);
					return true; // we will not change the state and instead still await auth
				}
			}

			case S_AWAITS_REQUEST: { // state only changes when a UCR or QRY is sent in this state
				if (!(mt.equals(MessageType.OP_INFO) || mt.equals(MessageType.OP_COMMAND_RECEIVED))) { // if we send a qry result or command received
					setState(DFAState.S_AWAITS_REQUEST);
					return true;
				} else if (mt.equals(MessageType.OP_ERROR) || mt.equals(MessageType.OP_TMP_ERROR)) {
					setState(DFAState.S_AWAITS_REQUEST);
					return true; // we will not change the state and instead await a new command
				} else {
					return false; // we will not send this message nor change state
				}
			}
			
			default: { // sever cannot send message from any other state
				return false; // we will not be sending this message
			}
		}
	}
	
	@Override
	protected Message processClose(Message m) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected Message processServerAwaitsAuthenRequest(Message m) {
	
		//[TODO] assert type of messages etc. 
		//[TODO] assert dfa state
		UserAuthenMessage aum = (UserAuthenMessage)m;
		
		//[TODO:] Checks for username and passwords
		//load user DB
		_acomps = _db.loadUserDB(aum.getUserName());
		
		//Create a AuthenAckMessage
		_response = new AckMessage(null); // without a verion. w/version passes "vernum"
		
		//[TODO] Set the state // this.state = DFAState.X
		//[TODO] I guess I have put  redundant state 
		
		//return the message to be sent.
		return _response;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * UCR and QRY branch from the same starting state, thus must be handled in the same process() subroutine
	 */
	protected Message processServerAwaitsRequest(Message m) {
		// handling for QRY and UCR messages belongs in this process()
		
		/*
		 * if !(message type = UCR || message type = QRY)
		 * 		return permanent error 
		 * if message type = UCR
		 * 		return command accepted or error
		 * if message type = QRY
		 * 		return query result or error
		 */
		
		MessageType mt = m.getMessageType();
		
		if (!(mt.equals(MessageType.OP_COMMAND) || mt.equals(MessageType.OP_QUERY)) ) {
			setState(DFAState.S_AWAITS_REQUEST);
			return new PermanentErrorMessage();
			
		}
		
		if (mt.equals(MessageType.OP_COMMAND)) {
			// for now, we are returning default messages that always say succesful delivery
			
			// process a command
			
			setState(DFAState.S_AWAITS_REQUEST);
			
			// return the command result
			return new RequestReceivedMessage(true);
		}
		
		if (mt.equals(MessageType.OP_QUERY)) {
			
			// process a query
			
			// qry result messages not fully impl yet, so
			// return a temporary error here for testing so we can tell if the block is reached
			setState(DFAState.S_AWAITS_REQUEST);
			return new TemporaryErrorMessage();
		}
		
		return null;
	}
	
	
	
	
	// anything below this line should be unreachable for server
	// ---------------------------
	

	@Override
	protected Message processServerAwaitsCommandRequest(Message m) {
		//[TODO] assert type of messages etc. 
		//[TODO] assert dfa state
		UtilityControlReqMessage ucm = (UtilityControlReqMessage)m;
		
		//create a command from the message
		Command cmd = null;
		try {
			 cmd = Command.createCommand(ucm);
		} catch (Exception e) {
			System.out.println("Error in creating command from Clients message");
		}
		
		// Find the component in db
		//[TODO] assert user db loaded properly in authentication processing.
		ArrayList<Component> comps = _acomps.get(cmd.getAutoId());
		Component comp =  null;
		for (Component component : comps) {
			if(component.getComponentCode() == cmd.getComponentType()){
				comp = component;
				break;
			}
		}
		
		//Apply the command
		comp.applyCommand(cmd);
		
		// [TODO]create a ack message to be sent to client succ failure or error.
		//  [FOR testing] error message as ack  
		_response = new PermanentErrorMessage();
		
		//[TODO] Set the state // this.state = DFAState.X
		return _response;
	}


	
	

	@Override
	protected Message processServerAwaitsQueryRequest(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processError(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processFailure(Message m) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected Message processClientAwaitsResponse(Message m) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected Message processInit(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
