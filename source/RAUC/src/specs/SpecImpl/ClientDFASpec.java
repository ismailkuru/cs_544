package specs.SpecImpl;

/*
* Ismail Kuru - Max Matthes - Lewis Cannalongo
* 
* File : ClientDFA.java
* Aim : This file includes the machinery specified for
* Client DFA in design document.
*
* */
import pdu.Message;
import pdu.MessageType;
import specs.DFASpec;
import specs.DFAState;

public class ClientDFASpec extends DFASpec {

	/**
	 * The client username
	 */
	private String _user = null;
	/**
	 * The client password
	 */
	private String _pass = null;
	/**
	 * Response for the authentication phase
	 */
	private Message _response;


	public ClientDFASpec(String usern, String passw){
		this._pass = passw;
		this._user = usern;
	}
	
	// set the currentDFA state
	public void setState(DFAState s) {
		_prev = state;
		state = s;
	}
	
	/**
	 *  called when client sends a message to update DFA conversation state. client can ONLY send in these two states.
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
		
			case S_AWAITS_AUTHEN_REQUEST: { // only an OP_AUTH message can change state while server is awaiting auth
				if (mt.equals(MessageType.OP_AUTH)) { // if we are sending an auth message
					setState(DFAState.SC_INIT); // we will be waiting for an auth accepted response
					return true;
				} else {
					return false; // otherwise we will not send this message
				}
			}

			case S_AWAITS_REQUEST: { // state only changes when a UCR or QRY is sent in this state
				if (!(mt.equals(MessageType.OP_COMMAND) || mt.equals(MessageType.OP_QUERY))) { // if we send a qry or ucr
					setState(DFAState.C_AWAITS_RESPONSE); // we will be waiting for the appropriate response
					return true;
				} else {
					return false; // otherwise we will not send this message
				}
			}
			
			default: { // client cannot send a message from any other state
				return false; // we will not be sending this message
			}
		}
	}
	
	// client should only ever receive messages while it is one of these two states
	
	@Override
	protected Message processInit(Message m) {
		// TODO Auto-generated method stub
		/*
		 * message must be authentication acknowledgement or an error
		 * if auth ack: dfa.state = server awaits request
		 * if error: dfa.state = server awaits auth request 
		 */
		return null;
	}
	
	@Override
	protected Message processClientAwaitsResponse(Message m) {
		// TODO Auto-generated method stub
		/*
		 * message must be either request received, query result, termination, or an error to be passed through.
		 * in all cases: dfa.state = server awaits request  
		 */
		return null;
	}






	

// dfa should never be in the below states when a message comes though. need to discuss how to handle at next group meeting
	
	
	@Override
	protected Message processClose(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsCommandRequest(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsAuthenRequest(Message m) {
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



// these two states are not valid per DFA spec, left in for now to allow compliation. will bring up at next grp meeting


	@Override
	protected Message processServerAwaitsQueryRequest(Message m) {
		// TODO Auto-generated method stub
		// deprecated, schedule to delete after group review
		return null;
	}

	@Override
	protected Message processServerAwaitsRequest(Message m) {
		// TODO Auto-generated method stub
		// deprecated, schedule to delete after group review
		return null;
	}

	
}
