package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class RequestReceivedMessage extends Message{
	
	Boolean isFailure;
	
	public RequestReceivedMessage(Boolean f){
		try{
			this._content = new ArrayList<ContentChunk>();
			this.isFailure = f;
			
			if(!f){
				this._header = new HeaderChunk(MessageType.OP_FAILURE,"0");
				//System.out.print(this._header.getMessageType());
				//System.out.print(this._header.getChunkCount());
			}
			else{
				this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED,"1");
				ContentChunk suc = new ContentChunk("1", "1");
				this._content.add(0, suc);
			}
		}catch(Exception e){
			System.out.println("Error");
			
		}
		
	}
	public MessageType getMessageType() {
		if(!this.isFailure)
			return MessageType.OP_FAILURE;
		else
			return MessageType.OP_COMMAND_RECEIVED;
	}

	

}
