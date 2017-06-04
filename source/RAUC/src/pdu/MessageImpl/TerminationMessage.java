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

public class TerminationMessage extends Message{
	public TerminationMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public TerminationMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_SHUTDOWN,"0");
	}
	
	public MessageType getMessageType() {
		return MessageType.OP_SHUTDOWN;
	}
}
