package components.ComponentImpl;

import java.util.HashMap;
import java.util.Map;

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
			_commandParamMap.put(ACAttribute.ON, null);
			_commandParamMap.put(ACAttribute.OFF, null);
			
		}
	
	public AC(String name, ComponentType compType, String autoId){	
		super(name,compType,autoId);
		
	}

	@Override
	public String getComponentName() {
		
		return this._name;
	}

	@Override
	public ComponentType getComponentCode() {		
		return this._typeCode;
	}

	@Override
	public String getComponentAutoId() {
		
		return this._autoId;
	}

	@Override	
	public void applyCommand(Command cmd) {
			
		String val = cmd.getValue();
		ACAttribute atrb = ACAttribute.typeFromStringCode(cmd.getAttribute());	
		AC._commandParamMap.put(atrb, val);
		
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
		if(code == "OFF"){		
			return OFF;
		}
		else if(code == "OFF"){
			return OFF;
			
		}else{
			
			throw new RuntimeException("Invalid state : " + code);
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