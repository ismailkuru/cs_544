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
* File name: TemporaryErrorMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for 
* acknowledgment of the Client for its temporal failure in communication with Server.
* *******************************************************
* Requirements:
* - STATEFUL : The TemporaryMessage objects are the arrows acknowledging the 
* the client such that command is not taken by the server because of a temporal machine state problem.
*  Temporary errors indicate that the client sent a valid command which can be tried again at a later time
*  without modification.
* - SERVICE : TemporaryError-Acknowledgement Service
* ==============================================================================
*/ 
public class TemporaryErrorMessage extends Message{
	public TemporaryErrorMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}
	
	public TemporaryErrorMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_TMP_ERROR, 0);
	}

	public MessageType getMessageType() {
		//STATEFUL
		return MessageType.OP_TMP_ERROR;
	}
}
