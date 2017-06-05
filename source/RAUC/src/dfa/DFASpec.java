package dfa;

import pdu.Message;

import static dfa.DFAState.CLOSED;
import static pdu.MessageType.OP_SHUTDOWN;


public abstract class DFASpec {
    protected volatile DFAState state;
    protected volatile DFAState _prev;

    public DFASpec() {
        state = CLOSED;
        _prev = CLOSED;
    }

    public void setState(DFAState s) {
        _prev = state;
        state = s;
    }

    /**
     * Confirms whether a message can be sent in the current DFA state, and transitions the DFA to the next state
     *
     * @param m Message to send
     * @return true if message can be sent
     */
    public boolean send(Message m) {
        // if the incoming message is a shutdown request, return immediately
        if (m.getMessageType() == OP_SHUTDOWN) {
            setState(CLOSED);
            return true;
        }
        switch (state) {
            case INIT:
                return sendInit(m);
            case AUTH:
                return sendAuth(m);
            case ESTABLISHED:
                return sendEstablished(m);
            case WAITCMD:
                return sendWaitcmd(m);
            case WAITQRY:
                return sendWaitqry(m);
            case CLOSED:
            default:
                return sendClosed(m);
        }
    }

    /**
     * Process received message, transition state, and return automated response if applicable
     *
     * @param m Received message
     * @return Response message
     */
    public Message receive(Message m) {
        // if the incoming message is a shutdown request, return immediately
        if (m.getMessageType() == OP_SHUTDOWN) return null;
        // otherwise, process the message in the respective state of the protocol
        switch (state) {
            case INIT:
                return receiveInit(m);
            case AUTH:
                return receiveAuth(m);
            case ESTABLISHED:
                return receiveEstablished(m);
            case WAITCMD:
                return receiveWaitcmd(m);
            case WAITQRY:
                return receiveWaitqry(m);
            case CLOSED:
            default:
                return receiveClosed(m);
        }
    }

    // Receive and Send commands all return unexpected type by default so server and client only need to implement
    // necessary methods

    protected boolean sendUnexpected(Message m) {
        // Refuse unexpected output by default
        return false;
    }

    protected Message receiveUnexpected(Message m) {
        // Refuse unexpected input by default
        // TODO: throw error here or just silently send error message?: return new PermanentErrorMessage();
        throw new IllegalStateException("Unexpected message in "+state+" state: " + m);
    }

    protected boolean sendClosed(Message m) {
        return sendUnexpected(m);
    }

    protected boolean sendInit(Message m) {
        return sendUnexpected(m);
    }

    protected boolean sendAuth(Message m) {
        return sendUnexpected(m);
    }

    protected boolean sendEstablished(Message m) {
        return sendUnexpected(m);
    }

    protected boolean sendWaitcmd(Message m) {
        return sendUnexpected(m);
    }

    protected boolean sendWaitqry(Message m) {
        return sendUnexpected(m);
    }

    protected Message receiveClosed(Message m) {
        return receiveUnexpected(m);
    }

    protected Message receiveWaitcmd(Message m) {
        return receiveUnexpected(m);
    }

    protected Message receiveWaitqry(Message m) {
        return receiveUnexpected(m);
    }

    protected Message receiveAuth(Message m) {
        return receiveUnexpected(m);
    }

    protected Message receiveInit(Message m) {
        return receiveUnexpected(m);
    }

    protected Message receiveEstablished(Message m) {
        return receiveUnexpected(m);
    }

    /**
     * @return DFA state
     */
    public DFAState state() {
        return state;
    }

}
