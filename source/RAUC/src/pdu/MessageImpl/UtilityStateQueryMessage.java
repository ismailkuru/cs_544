package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class UtilityStateQueryMessage extends Message{

	public UtilityStateQueryMessage(String[] params){
		try{
		this._content = new ArrayList<ContentChunk>();
		
		
		if(params.length == 0){
			this._header = new HeaderChunk(MessageType.OP_QUERY, "0");
			
		}
		else if(params.length == 1)
		{
			this._header = new HeaderChunk(MessageType.OP_QUERY, "1");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
		}
		else if(params.length == 2){
			this._header = new HeaderChunk(MessageType.OP_QUERY, "2");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
			ContentChunk compnt = new ContentChunk("32", params[1]);
			this._content.add(1,compnt);
		}
		else{
			this._header = new HeaderChunk(MessageType.OP_QUERY, "3");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
			ContentChunk compnt = new ContentChunk("32", params[1]);
			this._content.add(1,compnt);
			ContentChunk attrb = new ContentChunk("32", params[2]);
			this._content.add(2,attrb);
			
		}
		}catch(Exception e ){
			System.out.println("Error");
			
		}
			
	}

	public MessageType getMessageType() {
		return MessageType.OP_QUERY;
	}


}
