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
import specs.DFASpec;

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
