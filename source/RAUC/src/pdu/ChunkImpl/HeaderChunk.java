package pdu.ChunkImpl;


import pdu.Chunk;
import pdu.MessageType;

public class HeaderChunk extends Chunk<MessageType, String>{
	public HeaderChunk(MessageType mt, String cc) {
		super(mt, cc);
	}
	
	public MessageType getMessageType(){
		return this.getFirst();
	}
	
	public int getChunkCount(){
		return Integer.parseInt(this.getSecond());
		
	}
	public void setMessageType(MessageType mt){this.setFirst(mt);}
	public void setChunkCount(String cc){ this.setSecond(cc);}

}
