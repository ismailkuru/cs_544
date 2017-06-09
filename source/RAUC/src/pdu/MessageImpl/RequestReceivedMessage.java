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
* File name: RequestReceivedMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for 
* acknowledgment of the Client for its successful utility control request messages.
* *******************************************************
* Requirements:
* - STATEFUL : The RequestReceivedMessage objects are the arrows acknowledging the 
* the client such that utility control request command is received by Server
* - SERVICE : Acknowledgement Service
* ==============================================================================
*/ 
public class RequestReceivedMessage extends Message{
	
	public RequestReceivedMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}
	
	public RequestReceivedMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED, 1);
		ContentChunk suc = new ContentChunk("1");
		this._content.add(0, suc);
	}

	public MessageType getMessageType() {
		//STATEFUL
		return MessageType.OP_COMMAND_RECEIVED;
	}
}
