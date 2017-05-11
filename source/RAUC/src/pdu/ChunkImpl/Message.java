package pdu.ChunkImpl;

import java.util.List;


public class Message{

	HeaderChunk _header;
	List<ContentChunk> _content;
		
	public 	Message() {

	}
	
	public HeaderChunk getHeader(){
		return this._header;
	}
	public byte getMessageTypeCode(){
		return this.getHeader().getOPcode();
	}
	public MessageType getMessageType(){
		return this.getHeader().getMessageType();
	}
	
	
}
