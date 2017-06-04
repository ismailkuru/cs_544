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
	public UtilityStateQueryMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UtilityStateQueryMessage(String[] params){
		this._header = new HeaderChunk(MessageType.OP_QUERY, Integer.toString(params.length));
        this._content = new ArrayList<>();
        for(int i = 0; i <= params.length; i++) {
			this._content.add(new ContentChunk("32", params[i]));
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OP_QUERY;
	}
}
