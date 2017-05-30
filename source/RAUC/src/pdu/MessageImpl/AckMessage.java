package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class AckMessage extends Message{
	
	//[TODO Add later]String _ver; 
	public AckMessage(String ver){
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
	
	public String toString(){
		/*
		String strHeader = "";
		String contHeader = "";
	
			if(versionExists()){
				strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";

				contHeader = "Content=";
				for(int i = 0 ; i<this.getContent().size(); i ++){
					contHeader += "["+ this.getContent().get(i).getSize() + ":"+ this.getContent().get(i).getContent()+  "]";
				}
				return strHeader + "#" + contHeader;
			}
			else{
				strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
				return strHeader;
			}
			*/
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
		
	}
	public JsonElement toJson() {
		JsonParser jp = new JsonParser();
		JsonElement element = jp.parse(this.toString());
		return element;
	}

	@Override
	public HeaderChunk getHeader() {
		return _header;
	}

	@Override
	public List<ContentChunk> getContent() {	
		return _content;
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
