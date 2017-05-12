package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class AuthenAckMessage extends Message{
	
	String _ver = ""; 
	public AuthenAckMessage(String ver){
		this._content = new ArrayList<ContentChunk>();
		setVer(ver);
		
		if(versionExists()){
			this._header = new HeaderChunk(MessageType.OP_SUCCESS_VER,"1");
			ContentChunk vernum = new ContentChunk("32", ver);
			this._content.add(0, vernum);
		}
		else{
			this._header = new HeaderChunk(MessageType.OP_SUCCESS,"0");
		}
	}
	
	public MessageType getMessageType(){
		if(versionExists())
			return MessageType.OP_SUCCESS_VER;
		else
			return MessageType.OP_SUCCESS;
	}

	public JsonElement toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HeaderChunk getHeader() {
		return _header;
	}

	@Override
	public List<ContentChunk> getContent() {	
		return _content;
	}	
	public void setVer(String vn){
		this._ver = vn;
		
	}
	public String getVer(){
		return this._ver;
		
	}

	private Boolean versionExists(){
		if(getVer().isEmpty()) return false;
		else return true;
		
	}

}
