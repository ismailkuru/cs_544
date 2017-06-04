package pdu.ChunkImpl;

import pdu.MessageType;

public class HeaderChunk {
	private MessageType type;
	private int chunks;

	public HeaderChunk(MessageType type, int cc) {
		this.type = type;
		this.chunks = cc;
	}
	
	public MessageType getMessageType(){
		return type;
	}
	
	public int getChunkCount(){
		return chunks;
		
	}
}
