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

	@Override
	protected Message processClose(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processClientAwaitsCommResponse(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsConnRequest(Message m) {
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
	protected Message processClientAwaitsAuthenResponse(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processServerAwaitsQueryRequest(Message m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Message processClientAwaitsQueryResponse(Message m) {
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

	
}
