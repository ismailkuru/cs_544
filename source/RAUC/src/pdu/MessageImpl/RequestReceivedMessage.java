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

public class RequestReceivedMessage extends Message{
	
	public RequestReceivedMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}
	
	public RequestReceivedMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED,"1");
		ContentChunk suc = new ContentChunk("1", "1");
		this._content.add(0, suc);
	}

	public MessageType getMessageType() {
		return MessageType.OP_COMMAND_RECEIVED;
	}
}
