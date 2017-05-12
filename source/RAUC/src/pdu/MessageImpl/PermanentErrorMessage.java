package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class PermanentErrorMessage extends Message{

	public PermanentErrorMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_ERROR,"0");
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_ERROR;
	}

	public JsonElement toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HeaderChunk getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContentChunk> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
