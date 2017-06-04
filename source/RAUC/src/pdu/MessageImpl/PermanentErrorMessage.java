package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class PermanentErrorMessage extends Message{
	public PermanentErrorMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public PermanentErrorMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_ERROR,"0");
	}
	public MessageType getMessageType() {
		return MessageType.OP_ERROR;
	}
}
