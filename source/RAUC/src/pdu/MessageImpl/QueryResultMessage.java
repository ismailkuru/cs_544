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
* File name: QueryResultMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for 
* acknowledgment of the Client with a query result for its successful queries.
* *******************************************************
* Requirements:
* - STATEFUL : The QueryResultMessage objects are the [INFO]arrows in the DFA
* - SERVICE : Service for Retrieval of information from user account in ACS Server : 
* ex: list of autos, autos' Components’ States etc.
* ==============================================================================
*/ 

public class QueryResultMessage extends Message{
	public QueryResultMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public QueryResultMessage(String res){
		this._content= new ArrayList<>();
		//this._result = res;
		this._header = new HeaderChunk(MessageType.OP_INFO, 1);
		this._content.add(0, new ContentChunk(res));
	}
	public MessageType getMessageType() {
		return MessageType.OP_INFO;
	}
}
