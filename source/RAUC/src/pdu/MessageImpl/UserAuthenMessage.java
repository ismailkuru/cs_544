package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;


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
