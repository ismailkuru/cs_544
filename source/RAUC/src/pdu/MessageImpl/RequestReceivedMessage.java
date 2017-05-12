package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class RequestReceivedMessage extends Message{
	
	Boolean isFailure;
	
	public RequestReceivedMessage(Boolean f){
		this._content = new ArrayList<ContentChunk>();
		setIsFailure(f);
		if(isFailure){
			this._header = new HeaderChunk(MessageType.OP_FAILURE,"0");
		}
		else{
			this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED,"1");
			ContentChunk suc = new ContentChunk("1", "1");
			this._content.add(0, suc);
		}
		
	}
	public MessageType getMessageType() {
		if(isFailure())
			return MessageType.OP_FAILURE;
		else
			return MessageType.OP_COMMAND_RECEIVED;
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

	public Boolean getIsFailure(){
		return isFailure;
		
	}
	public void setIsFailure(Boolean isf){
		this.isFailure = isf;
	}
	public Boolean isFailure(){if(isFailure)return true; else return false;}

}
