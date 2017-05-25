package specs;

import pdu.Message;
import pdu.MessageType;


public abstract class DFASpec {

	// common server and client fields
	
		//State of the protocol initialized with CLOSED
		protected DFAState state = DFAState.CLOSED;
	
		public DFASpec() {
		
		}
		
		// common procedures
		
		/*Procedure called when a message is taken. It it dispatches the processing
		 * of the message to state specific message processing procedure. A message is
		 * is consumed and transition in the DFA happens and returns a message to other
		 * side.
		 *  
		 * @param message m (incoming message)
		 * @return : Return the response message after the state in DFA changes
		 * 
		 * */
		
		
		public Message process(Message m) {
			
			// if the incoming message is a shutdown request, return immediately
			if (m.getMessageType() == MessageType.OP_SHUTDOWN) return m;
			System.out.println("--- Type of message is " + m.getMessageType());
			// otherwise, process the message in the respective state of the protocol
			switch (state) {
				case CLOSED:					return processClose(m);
				case SC_INIT:					return processInit(m);
				case S_AWAITS_AUTHEN_REQUEST:	return processServerAwaitsAuthenRequest(m);
				case S_AWAITS_COMM_REQUEST:		return processServerAwaitsCommandRequest(m);
				case S_AWAITS_QUERY_REQUEST:	return processServerAwaitsQueryRequest(m);
				case C_AWAITS_RESPONSE:			return processClientAwaitsResponse(m);
				default:						return  processError(m); // should not get here error state
			}
		}

		
		// abstract methods
		// a method for each state of the DFA
		
		/**
		 * Processes the given message when the protocol is in CLOSED  state.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processClose					(Message m);
		
		/**
		 * Processes the given message when the protocol is in client awaits
		 * command/query response.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processClientAwaitsResponse (Message m);
		
		/**
		 * 
		 * Processes the given message when the protocol is in server awaits init
		 * state. * Processes the given message when the protocol is in init state and client awaits
		 * authentication confirmation.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processInit		(Message m);
		
		/**
		 * Processes the given message when the protocol is in server awaits command
		 * state.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsCommandRequest	(Message m);
		/**
		 * Processes the given message when the protocol is in server awaits
		 * action authentication req.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsAuthenRequest	(Message m);	
		
		/**
		 * Processes the given message when the protocol is in server awaits
		 * query requests.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsQueryRequest (Message m);
		
		
		// [TODO]These two can be removed later for optimization
		protected abstract Message processError(Message m);
		protected abstract Message processFailure(Message m); // [TODO] This may get resolved/processed in processError method
		
		// getters
		
		/**
		 * @return the current state of the protocol (the DFA).
		 */
		public DFAState state() {
			return state;
		}

}
