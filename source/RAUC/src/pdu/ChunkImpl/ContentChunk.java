package pdu.ChunkImpl;

import pdu.Chunk;

public class ContentChunk extends Chunk<String, String>{

		
	public ContentChunk(String sz ,String cnt) {
		super(sz, cnt);
		// TODO Auto-generated constructor stub
	}
	
	public String getSize(){
		return this.getFirst();
		
	}
	
	public String getContent(){
		return this.getSecond();
	}
	public void setSize(String sz){this.setFirst(sz);}
	public void setContent(String cnt){this.setSecond(cnt);}

}
