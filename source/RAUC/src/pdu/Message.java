package pdu;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public abstract class Message{
	public HeaderChunk _header;
	public ArrayList<ContentChunk> _content;
		
	public 	Message(){}	
	public Message(HeaderChunk h, ArrayList<ContentChunk>c){this._content = c; this._header= h;}
	public abstract HeaderChunk getHeader();
	public abstract List<ContentChunk> getContent();
	public abstract MessageType getMessageType();
	public abstract JsonElement toJson();
	public abstract List<byte[]> serialize();
	public abstract byte[][] crunchToBytes(List<byte[]> lb);
	
}
