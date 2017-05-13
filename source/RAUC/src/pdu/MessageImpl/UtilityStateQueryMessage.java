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

public class UtilityStateQueryMessage extends Message{

	public UtilityStateQueryMessage(String[] params){
		try{
		this._content = new ArrayList<ContentChunk>();
		
		
		if(params.length == 0){
			this._header = new HeaderChunk(MessageType.OP_QUERY, "0");
			
		}
		else if(params.length == 1)
		{
			this._header = new HeaderChunk(MessageType.OP_QUERY, "1");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
		}
		else if(params.length == 2){
			this._header = new HeaderChunk(MessageType.OP_QUERY, "2");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
			ContentChunk compnt = new ContentChunk("32", params[1]);
			this._content.add(1,compnt);
		}
		else{
			this._header = new HeaderChunk(MessageType.OP_QUERY, "3");
			ContentChunk autoId = new ContentChunk("32", params[0]);
			this._content.add(0, autoId);
			ContentChunk compnt = new ContentChunk("32", params[1]);
			this._content.add(1,compnt);
			ContentChunk attrb = new ContentChunk("32", params[2]);
			this._content.add(2,attrb);
			
		}
		}catch(Exception e ){
			System.out.println("Error");
			
		}
			
	}
	
	
	/*
	 * 
	 * 
	 * { 
	 * 	"header" : {"command_type":"num_chunks"},
	 * 	"data_chunks" : [{"size","content"}]  	
	 * 
	 * }
	 * 
	 * */
	public String toString(){
		/*
		String strHeader = "";
		String contHeader = "";
		
			strHeader = "{\"_header\":" + "{\""+ this.getHeader().getMessageType() + "\":\"" + this.getHeader().getChunkCount() + "\"},";
			contHeader = "\"_content\":" + "[";
			for(int i = 0 ; i<this.getContent().size(); i ++){
				contHeader += "{\""+this.getContent().get(i).getSize() + "\":\""+ this.getContent().get(i).getContent()+ "\"},"  ;
			}
		
			int index = contHeader.lastIndexOf(',');
			if(index != -1) {
			    contHeader = contHeader.substring(0,index);
			}
			contHeader+= "]}";
	
		return strHeader + contHeader;*/
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
		
	}
	public JsonElement toJson() {
		JsonParser jp = new JsonParser();
		JsonElement element = jp.parse(this.toString());
		//We can use this
		//UtilityStateQueryMessage um = gson.fromJson(this.toString(), UtilityStateQueryMessage.class);
		return element;
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_QUERY;
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

	//[TODO : add more getter setter if needed]

}
