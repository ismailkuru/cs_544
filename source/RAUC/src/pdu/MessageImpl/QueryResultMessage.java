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

	String _result;
	public QueryResultMessage(String res){
		try{
			this._content= new ArrayList<ContentChunk>();
			this._result = res;
			this._header = new HeaderChunk(MessageType.OP_INFO,"1");
			ContentChunk rs = new ContentChunk("32", this._result);
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


}
