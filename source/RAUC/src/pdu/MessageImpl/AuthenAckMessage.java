package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class AuthenAckMessage extends Message{
	
	//[TODO Add later]String _ver; 
	public AuthenAckMessage(String ver){
		this._content = new ArrayList<ContentChunk>();
	/*	this._ver = ver;*/
		if(ver != null){
			this._header = new HeaderChunk(MessageType.OP_SUCCESS_VER,"1");
			ContentChunk vernum = new ContentChunk("32", ver);
			this._content.add(0, vernum);
		}
		else{
			this._header = new HeaderChunk(MessageType.OP_SUCCESS,"0");
		}
	}
	
	public MessageType getMessageType(){
		//[TODO Add later]
		//if(versionExists())
			//return MessageType.OP_SUCCESS_VER;
		//else
			return MessageType.OP_SUCCESS;
	}

	/*
	public void setVer(String vn){
		this._ver = vn;
		
	}
	public String getVer(){
		return this._ver;
		
	}

	private Boolean versionExists(){
		if(getVer() == null) return false;
		else return true;
		
	}*/

}
