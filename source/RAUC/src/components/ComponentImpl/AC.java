package components.ComponentImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import components.Command;
import components.Component;
import components.ComponentType;

public class AC extends Component {
	ACAttribute _state;
	

	//attributes mapped to their values
	protected static Map<ACAttribute,String> _commandParamMap;
	static {

		// map for commands and its parameters
		_commandParamMap = new HashMap<ACAttribute, String>();
		_commandParamMap.put(ACAttribute.ON, "1");
		_commandParamMap.put(ACAttribute.OFF, "0");
			
	}
	
	public AC(String name, ComponentType compType){	
		super(name,compType);
		
	}

	@Override
	public String getComponentName() {
		
		return this._name;
	}

	@Override
	public ComponentType getComponentCode() {		
		return this._typeCode;
	}
	public String toString(){
	     StringBuilder sb = new StringBuilder();
	        Iterator<Entry<ACAttribute, String>> iter = _commandParamMap.entrySet().iterator();
	        while (iter.hasNext()) {
	        	Entry<ACAttribute, String> entry = iter.next();
	            sb.append(entry.getKey());
	            sb.append('=').append('"');
	            sb.append(entry.getValue());
	            sb.append('"');
	            if (iter.hasNext()) {
	                sb.append(',').append(' ');
	            }
	        }
	        return sb.toString();
	}
	public String getValueOfAttrb(String attrb){
		
		ACAttribute atrb = ACAttribute.typeFromStringCode(attrb);	
		return AC._commandParamMap.get(atrb);
		
	}
	@Override	
	public void applyCommand(Command cmd) {
			
		String val = cmd.getValue();
		ACAttribute atrb = ACAttribute.typeFromStringCode(cmd.getAttribute());	
		AC._commandParamMap.put(atrb, val);
		
	}
	
	public Map<ACAttribute,String> getComponentMap(){
		return AC._commandParamMap;
	}

	@Override
	public String attribToStringtoString(String attrib) {
		StringBuilder sb = new StringBuilder();
		ACAttribute atrb = ACAttribute.typeFromStringCode(attrib);
		String sval = _commandParamMap.get(atrb);
        sb.append(atrb);
        sb.append('=').append('"');
        sb.append(sval);
        sb.append('"');
		return sb.toString();
	}

	
}

enum ACAttribute {
	OFF	(0),
	ON	(1);
	
	private ACAttribute(Integer onoff) {
		this._status = onoff;
	}
	
	private Integer _status;
	
	public Integer getStatus() {
		return _status;
	}
	public static ACAttribute typeFromStringCode(String code) {
		Integer sc = Integer.parseInt(code);
		switch (sc) {
		case 0: return OFF;
		case 1: return ON;
		default: {
			throw new RuntimeException("Invalid state : " + code);
		}
	}
	}
	
	public static ACAttribute typeFromIntegerCode(Integer code) {
		switch (code) {
			case 0: return OFF;
			case 1: return ON;
			default: {
				throw new RuntimeException("Invalid state : " + code);
			}
		}
	}	
}