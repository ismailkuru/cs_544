package pdu.ChunkImpl;

import java.awt.TrayIcon.MessageType;

import pdu.Chunk;

public class HeaderChunk extends Chunk<MessageType, byte[]>{

	public HeaderChunk(MessageType mt, byte[] cnt) {
		super(mt, cnt);
		// TODO Auto-generated constructor stub
	}
	

}
