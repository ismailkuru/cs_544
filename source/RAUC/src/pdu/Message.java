package pdu;

import java.util.List;

import com.google.gson.JsonElement;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public abstract class Message{
	public HeaderChunk _header;
	public List<ContentChunk> _content;
		
	public 	Message(){}	
	public abstract HeaderChunk getHeader();
	public abstract List<ContentChunk> getContent();
	public abstract MessageType getMessageType();
	public abstract JsonElement toJson();
	
}
