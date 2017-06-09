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
* File name: TerminationMessage.java
* **************************************************
* Definition: This file includes implementation of Termination message
* *******************************************************
* Requirements:
* - STATEFUL : The TerminationMessage objects are the [SHUTDOWN]arrows in the DFA.
* - SERVICE : Communication Termination Request Service
* ==============================================================================
*/ 

public class TerminationMessage extends Message{
	public TerminationMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public TerminationMessage(){
		this._content = new ArrayList<>();
		this._header = new HeaderChunk(MessageType.OP_SHUTDOWN, 0);
	}
	
	public MessageType getMessageType() {
		return MessageType.OP_SHUTDOWN;
	}
}
