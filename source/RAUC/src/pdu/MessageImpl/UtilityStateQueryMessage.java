package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class UtilityStateQueryMessage extends Message{
	public UtilityStateQueryMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UtilityStateQueryMessage(String... params){
		int i = 0;
        this._content = new ArrayList<>();
		for (String param : params) {
			if (param != null) {
				this._content.add(new ContentChunk(param));
				i++;
			}
			this._header = new HeaderChunk(MessageType.OP_QUERY, i);
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OP_QUERY;
	}
}
