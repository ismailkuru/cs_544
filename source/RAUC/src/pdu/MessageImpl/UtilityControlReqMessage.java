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
* File name: UtilityControlReqMessage.java
* **************************************************
* Definition: This file includes implementation of the message which serves to
*  set/change the states of components’ attributes associated with an automobile.
* *******************************************************
* Requirements:
* - STATEFUL : The UtilityControlReqMessage objects are the [COMMAND]arrows in the DFA.
* - SERVICE : Component Control Service
* ==============================================================================
*/ 
public class UtilityControlReqMessage extends Message {

	public UtilityControlReqMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UtilityControlReqMessage(String aid, String comp, String attr, String val){
        this._header = new HeaderChunk(MessageType.OP_COMMAND, 4);
        this._content = new ArrayList<>();

        //Add pass and uname content in addition to header
        ContentChunk autoId = new ContentChunk(aid);
        this._content.add(autoId );
        ContentChunk compnt = new ContentChunk(comp);
        this._content.add(compnt);
        ContentChunk attrb = new ContentChunk(attr);
        this._content.add(attrb);
        ContentChunk vl = new ContentChunk(val);
        this._content.add(vl);
	}
		
	public MessageType getMessageType() {
		//STATEFUL
		return MessageType.OP_COMMAND;
	}
	
}
