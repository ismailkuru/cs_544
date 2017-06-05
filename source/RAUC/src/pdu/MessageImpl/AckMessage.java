package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class AckMessage extends Message{
	public AckMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public AckMessage(){
		this._content = new ArrayList<>();
        this._header = new HeaderChunk(MessageType.OP_SUCCESS, 0);
	}
	
	public MessageType getMessageType(){
        return MessageType.OP_SUCCESS;
	}
}
