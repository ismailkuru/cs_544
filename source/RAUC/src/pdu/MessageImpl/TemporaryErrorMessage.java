package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class TemporaryErrorMessage extends Message{

	public TemporaryErrorMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_TMP_ERROR,"0");
	}

	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_TMP_ERROR;
	}

}
