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
		 * @param message m 
		 * @return : Return a message to be sent after the state transition
		 * 					 in DFA occurs
		 * 
		 * */
		
		
		public Message process(Message m) {
			
			// if the incoming message is a shutdown request, return immediately
			if (m.getMessageType() == MessageType.OP_SHUTDOWN) return m;
			// otherwise, process the message in the respective state of the protocol
			switch (state) {
			case CLOSED:					return processClose(m);
			case S_AWAITS_INIT:				return processServerAwaitsInit(m);
			case C_AWAITS_RESPONSE:			return processClientAwaitsResponse(m);
			case S_AWAITS_AUTHEN:			return processServerAwaitsAuthen(m);
			case S_AWAITS_ACTION:			return processServerAwaitsAction(m);
			case C_AWAITS_CONFIRM:			return processClientAwaitsConfirm(m);
			
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
		 * action response.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processClientAwaitsResponse	(Message m);
		
		/**
		 * Processes the given message when the protocol is in server awaits init
		 * state.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsInit		(Message m);
		
		/**
		 * Processes the given message when the protocol is in server awaits action
		 * state.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsAction	(Message m);
		/**
		 * Processes the given message when the protocol is in server awaits
		 * action authentication req.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processServerAwaitsAuthen	(Message m);
		
		/**
		 * Processes the given message when the protocol is in client awaits
		 * action confirmation.
		 * @param m the message to process.
		 * @return the response message to pass to the other end.
		 */
		protected abstract Message processClientAwaitsConfirm	(Message m);
		
		// [TODO]These two can be removed later for optimization
		protected abstract Message processError(Message m);
		protected abstract Message processFailure(Message m);
		
		// getters
		
		/**
		 * @return the current state of the protocol (the DFA).
		 */
		public DFAState state() {
			return state;
		}

}
