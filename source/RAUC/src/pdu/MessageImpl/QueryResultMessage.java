package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;


public class QueryResultMessage extends Message{
	public QueryResultMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public QueryResultMessage(String res){
		this._content= new ArrayList<>();
		//this._result = res;
		this._header = new HeaderChunk(MessageType.OP_INFO, 1);
		this._content.add(0, new ContentChunk(res));
	}
	public MessageType getMessageType() {
		return MessageType.OP_INFO;
	}
}
