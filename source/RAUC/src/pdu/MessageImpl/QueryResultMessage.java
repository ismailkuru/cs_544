package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;


public class QueryResultMessage extends Message{

	String _result;
	public QueryResultMessage(String res){
		try{
			this._content= new ArrayList<ContentChunk>();
			this._result = res;
			this._header = new HeaderChunk(MessageType.OP_INFO,"1");
			ContentChunk rs = new ContentChunk("32", this._result);
			this._content.add(0, rs);
		}catch(Exception e){
			System.out.println("Error");
		}
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_INFO;
	}


}
