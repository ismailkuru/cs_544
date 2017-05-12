package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class UtilityStateQueryMessage extends Message{

	public UtilityStateQueryMessage(String... params){
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
			
	}
	
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
