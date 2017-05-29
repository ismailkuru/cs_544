package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class PermanentErrorMessage extends Message{

	public PermanentErrorMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_ERROR,"0");
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_ERROR;
	}

	/*public ByteArrayOutputStream toBytes(){
		return new ByteArrayOutputStream();
	}
	*/
	

}
