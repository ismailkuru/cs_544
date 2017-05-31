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
				if (mt.equals(MessageType.OP_AUTH)) {
					setState(DFAState.SC_INIT);
					return true;
				}
				else {
					break;
				}
			}

			case S_AWAITS_REQUEST: { // state only changes when a UCR or QRY is sent in this state
				if (!(mt.equals(MessageType.OP_COMMAND) || mt.equals(MessageType.OP_QUERY))) {
					setState(DFAState.C_AWAITS_RESPONSE);
					return true;
				}
			}
			
			default: { // client cannot send a message from any other state
				return false;
			}
		}
		// not reachable unless there is an error
		return false;
	}
	
	
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



	@Override
	protected Message processServerAwaitsQueryRequest(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
