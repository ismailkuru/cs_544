package pdu.ChunkImpl;

import pdu.Chunk;

public class ContentChunk extends Chunk<byte[], byte[]>{

		
	public ContentChunk(byte[] sz, byte[] cnt) {
		super(sz, cnt);
		// TODO Auto-generated constructor stub
	}
	
	public byte getSize(){
		return this.getFirst()[0];
		
	}
	
	public byte[] getContent(){
		return this.getSecond();
	}

}
