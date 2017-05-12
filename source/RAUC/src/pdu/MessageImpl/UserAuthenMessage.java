package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;


public class UserAuthenMessage extends Message{
	String _username;
	String _password;
	
	public UserAuthenMessage(){}
	public UserAuthenMessage(String uname, String pass){
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_AUTH,"2");

		//Add pass and uname content in addition to header
		ContentChunk unameContent = new ContentChunk("32", uname);
		this._content.set(0,unameContent );
		ContentChunk passContent = new ContentChunk("256", pass);
		this._content.add(1,passContent);
		
	}
		
	public MessageType getMessageType() {
		return MessageType.OP_AUTH;
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
