package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class RequestReceivedMessage extends Message{
	
	public RequestReceivedMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}
	
	public RequestReceivedMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED, 1);
		ContentChunk suc = new ContentChunk("1");
		this._content.add(0, suc);
	}

	public MessageType getMessageType() {
		return MessageType.OP_COMMAND_RECEIVED;
	}
}
