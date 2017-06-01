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


public class QueryResultMessage extends Message{
	
	public QueryResultMessage(HeaderChunk h, ArrayList<ContentChunk> c){
		super(h, c);
	}

	//String _result;
	public QueryResultMessage(String res){
		try{
			this._content= new ArrayList<ContentChunk>();
			//this._result = res;
			this._header = new HeaderChunk(MessageType.OP_INFO,"1");
			ContentChunk rs = new ContentChunk("32", res);
			this._content.add(0, rs);
		}catch(Exception e){
			System.out.println("Error");
		}
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_INFO;
	}

	public String toString(){
		/*
		String strHeader = "";
		String contHeader = "";
		
			strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
			contHeader = "Content=";
			for(int i = 0 ; i<this.getContent().size(); i ++){
				contHeader += "["+ this.getContent().get(i).getSize() + ":"+ this.getContent().get(i).getContent()+  "]";
			}
		
		return strHeader + "#" + contHeader;*/
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
		
		for (ContentChunk c  : this.getContent()) {
			byte[] cByte = new byte[1];
			//Add size of the content chunk
			int sz = Integer.parseInt(c.getSize());
			cByte[0] = (byte)sz;
			l.add(cByte);
			
			//Add content of the chunk
			byte[] cByteCnt = new byte[c.getContent().length()];
			cByteCnt	 = c.getContent().getBytes();
			l.add(cByteCnt);
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
