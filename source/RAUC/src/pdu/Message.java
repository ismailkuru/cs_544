package pdu;


public abstract class Message{

	MessageType _messageType;
	
	public 	Message(MessageType mtype) {
		this._messageType = mtype;
	}

	public MessageType getMessageType() {
		return _messageType;
	}
	
}
