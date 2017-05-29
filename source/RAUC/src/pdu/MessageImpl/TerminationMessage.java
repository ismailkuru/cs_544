package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class TerminationMessage extends Message{
	
	
	
	public TerminationMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_SHUTDOWN,"0");
	}

	
	public MessageType getMessageType() {
		return MessageType.OP_SHUTDOWN;
	}

}
