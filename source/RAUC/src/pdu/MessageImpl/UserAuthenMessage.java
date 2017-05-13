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


public class UserAuthenMessage extends Message{
	String _username;
	String _password;
	
	public UserAuthenMessage(){}
	public UserAuthenMessage(String uname, String pass){
		try{
			this._content = new ArrayList<ContentChunk>();
			this._header = new HeaderChunk(MessageType.OP_AUTH,"2");

			//Add pass and uname content in addition to header
			ContentChunk unameContent = new ContentChunk("32", uname);
			this._content.add(0,unameContent );
			ContentChunk passContent = new ContentChunk("256", pass);
			this._content.add(1,passContent);
		}catch(Exception e){
			System.out.println("");
			}
		
	}
		
	public MessageType getMessageType() {
		return MessageType.OP_AUTH;
	}
	
	public String toString(){
		/*
		String strHeader = "";
		String contHeader = "";
		
			strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
			contHeader = "Content=";
			for(int i = 0 ; i<this.getContent().size(); i ++){
				contHeader += "["+ this.getContent().get(i).getSize() + ":"+ this.getContent().get(i).getContent()+  "]";
			}
		
			
	
		return strHeader + "#" + contHeader;*/
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

	public String getPassword(){
		return _password;
	}
	public String getUserName(){	
		return _username;
	}
	
	public void setPassword(String ps){
		_password = ps;
	}
	public void setUserName(String un){	
		_username = un;
	}
	
	
}
