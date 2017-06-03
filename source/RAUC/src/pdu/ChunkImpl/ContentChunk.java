package pdu.ChunkImpl;

import pdu.Chunk;

public class ContentChunk extends Chunk<String, String>{
	public ContentChunk(String sz ,String cnt) {
		super(sz, cnt);
	}
	
	public int getSize(){
		return Integer.parseInt(this.getFirst());
		
	}
	
	public String getContent(){
		return this.getSecond();
	}
	public void setSize(String sz){this.setFirst(sz);}
	public void setContent(String cnt){this.setSecond(cnt);}

}
