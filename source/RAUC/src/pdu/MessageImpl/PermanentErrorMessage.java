package pdu.MessageImpl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

public class PermanentErrorMessage extends Message{
	
	public PermanentErrorMessage(HeaderChunk h, ArrayList<ContentChunk> c){
		super(h, c);
	}

	public PermanentErrorMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_ERROR,"0");
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_ERROR;
	}

	public String toString(){
		/*
		String strHeader;
		strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
		return strHeader;*/
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
		
	}
	public JsonElement toJson() {
		JsonParser jp = new JsonParser();
		JsonElement element = jp.parse(this.toString());
		return element;
	}

	@Override
	public HeaderChunk getHeader() {
		// TODO Auto-generated method stub
		return this._header;
	}

	@Override
	public List<ContentChunk> getContent() {
		// TODO Auto-generated method stub
		return this._content;
	}
	/*
	 * 
	 * 	@Override
	public List<byte[]> serialize() {
		int cc = Integer.parseInt(this.getHeader().getChunkCount());
		int optype = this.getHeader().getMessageType().getOpcode();
		List<byte[]> l = new ArrayList<byte[]>();
		byte[] hdr = new byte[2];
		hdr[0]= (byte)optype;
		hdr[1] = (byte)cc;
		
		//Add Header Chunk
		l.add(hdr);
		
		if(this.getMessageType().equals(MessageType.OP_SUCCESS_VER))
		//Add Content Chunk
		for (ContentChunk c  : this.getContent()) {
			byte[] cByte = new byte[2];
			//Add size of the content chunk
			cByte[0] = (byte)Integer.parseInt(c.getSize());
			//Add content of the chunk
			cByte[1] = Byte.parseByte(c.getContent());
			l.add(cByte);
		} 
		return l;
	}
	 * */
	@Override
	public List<byte[]> serialize() {
		int cc = Integer.parseInt(this.getHeader().getChunkCount());
		int optype = this.getHeader().getMessageType().getOpcode();
		List<byte[]> l = new ArrayList<byte[]>();
		byte[] hdr = new byte[2];
		hdr[0]= (byte)optype;
		hdr[1] = (byte)cc;
		
		//Add Header Chunk
		l.add(hdr);
		return l;
	}

	public byte[][] crunchToBytes(List<byte[]> lb){
		
		byte[][] bb = new byte[lb.size()][];
		
		for(int i =0 ; i<lb.size(); i++) {
			bb[i] = lb.get(i);
		}
		
		return bb;
		
	}
	

}
