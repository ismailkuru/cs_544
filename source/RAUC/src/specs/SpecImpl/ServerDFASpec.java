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
	@Override
	protected Message processClose(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

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
