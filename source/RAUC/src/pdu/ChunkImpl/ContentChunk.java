package pdu.ChunkImpl;

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
