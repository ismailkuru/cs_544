package pdu.ChunkImpl;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ContentChunk.java
* **************************************************
* Definition: This file includes definitions for one type of 
* basic unit of a message of which content of the message
* comprises.
* *******************************************************
* Requirements:
* - SERVICE : The relevant requirement the entire file satisfies is partly SERVICE
*  because it is one type of basic units of Messages.
* 
* ==============================================================================
*/
public class ContentChunk {
	String content;

	public ContentChunk(String content) {
	    this.content = content;
	}
	
	public int getSize(){
		return content.length();
	}
	
	public String getContent(){
		return content;
	}
}
