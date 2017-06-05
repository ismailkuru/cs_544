package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;
import java.util.List;

public class UtilityControlReqMessage extends Message {
	//String _autoid;
	//String _component;
	//String _attribute;
	//String _value;

	public UtilityControlReqMessage(HeaderChunk h, List<ContentChunk> c){
		super(h, c);
	}

	public UtilityControlReqMessage(String aid, String comp, String attr, String val){
        this._header = new HeaderChunk(MessageType.OP_COMMAND, 4);
        this._content = new ArrayList<>();

        //Add pass and uname content in addition to header
        ContentChunk autoId = new ContentChunk(aid);
        this._content.add(autoId );
        ContentChunk compnt = new ContentChunk(comp);
        this._content.add(compnt);
        ContentChunk attrb = new ContentChunk(attr);
        this._content.add(attrb);
        ContentChunk vl = new ContentChunk(val);
        this._content.add(vl);
	}
		
	public MessageType getMessageType() {
		return MessageType.OP_COMMAND;
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
