package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;


public class UserAuthenMessage extends Message{
	public UserAuthenMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UserAuthenMessage(String uname, String pass){
        this._content = new ArrayList<>();
        this._header = new HeaderChunk(MessageType.OP_AUTH, 2);

        //Add pass and uname content in addition to header
        ContentChunk unameContent = new ContentChunk(uname);
        this._content.add(unameContent);
        ContentChunk passContent = new ContentChunk(pass);
        this._content.add(passContent);
	}
		
	public MessageType getMessageType() {
		return MessageType.OP_AUTH;
	}

	public String getPassword(){
		return _content.get(1).getContent();
	}
	public String getUserName(){	
		return _content.get(0).getContent();
	}
}
