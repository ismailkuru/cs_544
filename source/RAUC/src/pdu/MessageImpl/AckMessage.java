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
* File name: AckMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves for
* acknowledgment of the Client for success authentication.
* *******************************************************
* Requirements:
* - STATEFUL : The AckMessage objects are the [SUCCESS]arrows in the DFA
* - SERVICE : Acknowledgement Service
* 
* ==============================================================================
*/      
public class AckMessage extends Message{
	public AckMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public AckMessage(){
		this._content = new ArrayList<>();
        this._header = new HeaderChunk(MessageType.OP_SUCCESS, 0);
	}
	
	public MessageType getMessageType(){
		//STATEFUL
        return MessageType.OP_SUCCESS;
	}
}
