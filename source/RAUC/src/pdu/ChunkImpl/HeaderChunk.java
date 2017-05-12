package pdu.ChunkImpl;


import pdu.Chunk;
import pdu.MessageType;

public class HeaderChunk extends Chunk<MessageType, String>{


	public HeaderChunk(MessageType mt, String cc) {
		super(mt, cc);
		// TODO Auto-generated constructor stub
	}
	
	public MessageType getMessageType(){
		return this.getFirst();
	}
	
	public String getChunkCount(){
		return this.getSecond();
		
	}
	public void setMessageType(MessageType mt){this.setFirst(mt);}
	public void setChunkCount(String cc){ this.setSecond(cc);}

}
