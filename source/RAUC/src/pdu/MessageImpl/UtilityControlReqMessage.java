package pdu.MessageImpl;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.Message;
import pdu.MessageType;

import java.util.ArrayList;

public class UtilityControlReqMessage extends Message{

	
	String _autoid;
	String _component;
	String _attribute;
	String _value;
	
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

	
}
