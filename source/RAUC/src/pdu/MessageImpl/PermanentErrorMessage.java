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
* File name: PermanentErrorMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for 
* acknowledgment of the Client for unsuccessful communication.
* *******************************************************
* Requirements:
* - STATEFUL : The PermanentErrorMessage objects are the [ERROR]arrows in the DFA
* - SERVICE : Error-Acknowledgement.
* ==============================================================================
*/ 
public class PermanentErrorMessage extends Message{
	public PermanentErrorMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public PermanentErrorMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_ERROR, 0);
	}
	public MessageType getMessageType() {
		//STATEFUL
		return MessageType.OP_ERROR;
	}
}
