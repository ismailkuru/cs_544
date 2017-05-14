package components;

import pdu.*;

/*
 * This file includes implementation of 
 * converting a utility request command 
 * to a command object to be applied on
 * components.
 * 
 * */

public class Command {
	String _autoId;
	ComponentType _comp;
	String _attrb;
	String _val ;
	
	private Command(String autoId, ComponentType comp, String attrb, String val){
		_autoId =autoId;
		_comp = comp;
	    _attrb = attrb;
		_val = val;
			
	}
	
	public static Command createCommand(Message msg) throws Exception{

		if(msg.getMessageType() != MessageType.OP_COMMAND ){
			throw new Exception("Invalid message type : " + msg.getMessageType());	
		}
		else{
			String auid = msg.getContent().get(0).getContent();
			ComponentType cmp = ComponentType.typeFromString(msg.getContent().get(1).getContent());
			String atrb = msg.getContent().get(2).getContent();
			String vl = msg.getContent().get(3).getContent();
			return new Command(auid,cmp,atrb,vl);
		}	
	}
	public String getAutoId(){
		return this._autoId;
	}
	public ComponentType getComponentType(){
		return this._comp;
	}
	public String getAttribute(){
		return this._attrb;
	}
	
	public String getValue(){		
		return this._val;
		
	}
}
