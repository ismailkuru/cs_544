package pdu.MessageImpl;

import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class UtilityStateQueryMessage extends Message{

	
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return null;
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
