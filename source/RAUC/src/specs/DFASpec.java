package specs;

import pdu.*;
import pdu.MessageImpl.PermanentErrorMessage;

public abstract class DFASpec {

	// common server and client fields
	
		/**
		 * The state of the protocol
		 */
		protected DFAState state = DFAState.CLOSED;
	

		/**
		 * Initializes the DFA with the given house.
		 */
		public DFASpec() {
		
		}
		
		// common procedures
		
		/**
		 * Main DFA message processing procedure. Processes the given message with
		 * respect to the current state of the DFA, changes the state of the DFA
		 * accordingly and returns the message to be sent to the other side.
		 * @param m incoming message to process at the current state.
		 * @return the response message after the DFA state is changed, corresponding
		 * to the input message.
		 */
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
			
			default:						return ((Message) new PermanentErrorMessage(MessageType.ERROR)); // should not get here error state
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
	
		
		// getters
		
		/**
		 * @return the current state of the protocol (the DFA).
		 */
		public DFAState state() {
			return state;
		}

}
