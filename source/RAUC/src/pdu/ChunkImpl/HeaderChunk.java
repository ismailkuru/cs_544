package pdu.ChunkImpl;

import pdu.MessageType;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: HeaderChunk.java
* **************************************************
* Definition: This file includes definitions for one type of basic unit
*  of a message. HeaderChunk includes type of the message in its first byte
*  and number of chunks following header chunk.
* *******************************************************
* Requirements:
* - SERVICE : SERVICE Entire file includes definitions for one type of
*  basic unit of a message which represents header of the file.
* - STATEFUL This file defines the Message Type which is partly a part of being statefulness. 
* The reason is that Message Type is tightly coupled with Client/Server DFAs’ state 
* when they are processing the message taken from other end.
* 
* ==============================================================================
*/
public class HeaderChunk {
	private MessageType type;
	private int chunks;

	public HeaderChunk(MessageType type, int cc) {
		this.type = type;
		this.chunks = cc;
	}
	
	public MessageType getMessageType(){
		return type;
	}
	
	public int getChunkCount(){
		return chunks;
		
	}
}
