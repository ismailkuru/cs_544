package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;


public class QueryResultMessage extends Message{

	String _result;
	public QueryResultMessage(String res){
		this._content= new ArrayList<ContentChunk>();
		this._result = res;
		this._header = new HeaderChunk(MessageType.OP_INFO,"1");
		ContentChunk rs = new ContentChunk("32", this._result);
		this._content.add(0, rs);
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_INFO;
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
