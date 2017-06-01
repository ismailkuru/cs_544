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

public class UtilityControlReqMessage extends Message{

	
	//String _autoid;
	//String _component;
	//String _attribute;
	//String _value;
	public UtilityControlReqMessage(HeaderChunk h, ArrayList<ContentChunk> c){
		super(h, c);
	}
	public UtilityControlReqMessage(){}
	public UtilityControlReqMessage(String aid, String comp, String attr, String val){
		try{
			this._header = new HeaderChunk(MessageType.OP_COMMAND, "4");
			this._content = new ArrayList<ContentChunk>();

			//Add pass and uname content in addition to header
			ContentChunk autoId = new ContentChunk("32", aid);
			this._content.add(0,autoId );
			ContentChunk compnt = new ContentChunk("32", comp);
			this._content.add(1,compnt);
			ContentChunk attrb = new ContentChunk("32", attr);
			this._content.add(2,attrb);
			ContentChunk vl = new ContentChunk("32", val);
			this._content.add(3,vl);
		}catch(Exception e){
			System.out.println("Error");
		}
		
	}
		
	public MessageType getMessageType() {
		return MessageType.OP_COMMAND;
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
		//We can use this
		//UtilityControlReqMessage um = gson.fromJson(this.toString(), UtilityControlReqMessage.class);
		return element;
	}

	@Override
	public HeaderChunk getHeader() {
		return _header;
	}

	@Override
	public List<ContentChunk> getContent() {	
		return _content;
	}
/*
	public String getAutoId(){
		return _autoid;
	}
	public String getComponent(){	
		return _component;
	}
	public String getAttribute(){	
		return _attribute;
	}	
	public String getValue(){	
		return _value;
	}
	public void setAutoId(String ai){	
		_autoid = ai;
	}
	public void setComponent(String cmp){	
		_component = cmp;
	}
	public void setAttribute(String attr){	
		_attribute = attr;
	}
	public void setValue(String vl){	
		_value = vl;
	}
*/
	
}
