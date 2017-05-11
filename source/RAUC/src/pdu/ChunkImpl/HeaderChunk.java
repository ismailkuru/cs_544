package pdu.ChunkImpl;

import pdu.ChunkImpl.MessageType;

import pdu.Chunk;

public class HeaderChunk extends Chunk<MessageType, byte[]>{

	
	public HeaderChunk(MessageType mt, byte[] cnt) {
		super(mt, cnt);
		// TODO Auto-generated constructor stub
	}
	
	public MessageType getMessageType(){
		return this.getFirst();
	}
	public byte getOPcode(){
		return this.getFirst().getOpcode();
	}
	public byte[] getChunkCount(){
		return this.getSecond();
		
	}
	

}
