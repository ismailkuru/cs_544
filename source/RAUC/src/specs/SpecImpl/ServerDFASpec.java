package specs.SpecImpl;

/*
* Ismail Kuru - Max Matthes - Lewis Cannalongo
* 
* File : Server.java
* Aim : This file includes the machinery specified for
* Server DFA in design document.
*
* */
import pdu.Message;
import specs.DFASpec;

public class ServerDFASpec extends DFASpec{
	/**
	 * Confirm message for client actions
	 */
	private Message _response;
	

	public ServerDFASpec(){
		
	}
	@Override
	protected Message processClose(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsCommandRequest(Message m) {
	    //change state from waiting commad to command received etc .
		//As in the ACTest create a command from the message
		// apply command 
		// create a ack message to be sent to server
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsAuthenRequest(Message m) {
		// TODO Auto-generated method stub
		return null;
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
