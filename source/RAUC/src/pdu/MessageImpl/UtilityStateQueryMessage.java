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
* File name: UtilityStateQueryMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves to
*  query the states of components’ attributes associated with an automobile.
* *******************************************************
* Requirements:
* - STATEFUL : The UtilityStateQueryMessage objects are the [QUERY]arrows in the DFA.
* - SERVICE : Query Service for each user's automobiles and their components' internal states
* ==============================================================================
*/ 
public class UtilityStateQueryMessage extends Message{
	public UtilityStateQueryMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UtilityStateQueryMessage(String... params){
		int i = 0;
        this._content = new ArrayList<>();
		for (String param : params) {
			if (param != null) {
				this._content.add(new ContentChunk(param));
				i++;
			}
			this._header = new HeaderChunk(MessageType.OP_QUERY, i);
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OP_QUERY;
	}
}
