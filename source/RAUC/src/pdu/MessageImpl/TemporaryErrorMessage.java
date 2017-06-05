package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class TemporaryErrorMessage extends Message{
	public TemporaryErrorMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}
	
	public TemporaryErrorMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_TMP_ERROR, 0);
	}

	public MessageType getMessageType() {
		return MessageType.OP_TMP_ERROR;
	}
}
