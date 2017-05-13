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

public class TemporaryErrorMessage extends Message{

	public TemporaryErrorMessage(){
		
		this._content = new ArrayList<ContentChunk>();
		this._header = new HeaderChunk(MessageType.OP_TMP_ERROR,"0");
	}
	public String toString(){
		/*String strHeader;
		strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
		return strHeader;*/
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
		
	}
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.OP_TMP_ERROR;
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
