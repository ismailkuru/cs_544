package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: UserAuthenMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for 
* user to authenticate itself in the system.
* *******************************************************
* Requirements:
* - STATEFUL : The UserAuthenMessage objects are the [AUTH]arrows in the DFA.
* - SERVICE : Authentication Service
* ==============================================================================
*/ 
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
		//STATEFUL
		return MessageType.OP_AUTH;
	}

	public String getPassword(){
		return _content.get(1).getContent();
	}
	public String getUserName(){	
		return _content.get(0).getContent();
	}
}
