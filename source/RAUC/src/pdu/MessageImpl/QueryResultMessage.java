package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;


public class QueryResultMessage extends Message{
	public QueryResultMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public QueryResultMessage(String res){
		this._content= new ArrayList<>();
		//this._result = res;
		this._header = new HeaderChunk(MessageType.OP_INFO,"1");
		this._content.add(0, new ContentChunk("32", res));
	}
	public MessageType getMessageType() {
		return MessageType.OP_INFO;
	}
}
