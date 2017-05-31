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

public class RequestReceivedMessage extends Message{
	
	public RequestReceivedMessage(HeaderChunk h, ArrayList<ContentChunk> c){
		super(h, c);
	}
	
	public RequestReceivedMessage(Boolean f){
		try{
			this._content = new ArrayList<ContentChunk>();
			//this.isFailure = f;
			
			if(!f){
				this._header = new HeaderChunk(MessageType.OP_FAILURE,"0");
				//System.out.print(this._header.getMessageType());
				//System.out.print(this._header.getChunkCount());
			}
			else{
				this._header = new HeaderChunk(MessageType.OP_COMMAND_RECEIVED,"1");
				ContentChunk suc = new ContentChunk("1", "1");
				this._content.add(0, suc);
			}
		}catch(Exception e){
			System.out.println("Error");
			
		}
		
	}
	public MessageType getMessageType() {
		if(this.getContent().size() == 0)
			return MessageType.OP_FAILURE;
		else
			return MessageType.OP_COMMAND_RECEIVED;
	}
	
	public String toString(){
		/*
		String strHeader = "";
		String contHeader = "";
		
		if(this.isFailure){
			strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
			contHeader = "Content=";
			for(int i = 0 ; i<this.getContent().size(); i ++){
				contHeader += "["+ this.getContent().get(i).getSize() + ":"+ this.getContent().get(i).getContent()+  "]";
			}
			return strHeader + "#" + contHeader;
		}
		else{
			strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
			return strHeader;
		}
		*/
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
		
		if(this.getMessageType().equals(MessageType.OP_COMMAND_RECEIVED)){
			//Add Content Chunk
			for (ContentChunk c  : this.getContent()) {
				byte[] cByte = new byte[1];
				//Add size of the content chunk
				cByte[0] = (byte)Integer.parseInt(c.getSize());
				l.add(cByte);
				
				//Add content of the chunk
				byte[] cByteCnt = new byte[c.getContent().length()];
				cByte	 = c.getContent().getBytes();
				l.add(cByteCnt);
			} 
		}
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
