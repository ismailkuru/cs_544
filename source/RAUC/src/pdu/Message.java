package pdu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;

import java.util.ArrayList;
import java.util.List;

public abstract class Message{
	public HeaderChunk _header;
	public ArrayList<ContentChunk> _content;
		
	public 	Message(){}

	//TODO: Actually implement this. Should this be abstract or inherited?
	//public abstract ByteArrayOutputStream toBytes();

	public abstract MessageType getMessageType();

	public HeaderChunk getHeader(){
		return this._header;
	}

	public List<ContentChunk> getContent(){
		return this._content;
	}

	public JsonElement toJson(){
		//We can use this
		//<MessageClass> um = gson.fromJson(this.toString(), <MessageClass>.class);
		return new JsonParser().parse(this.toString());
	}

	public String toString(){
		return new Gson().toJson(this);
	}

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
}
