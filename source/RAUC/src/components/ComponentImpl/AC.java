package components.ComponentImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import components.Attribute;
import components.Command;
import components.Component;
import components.ComponentType;

public class AC extends Component {
	ArrayList<Attribute> _attribs = new ArrayList<>();
	
	
	public AC(String name, ComponentType compType){	
		super(name,compType);	
		Power p = new Power("OFF");
		_attribs.add(p);
		
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
	       for(Attribute a : _attribs){
	            sb.append(a.attribToString());
	            //sb.append('=').append('"');
	            sb.append(a.stateToString());
	            //sb.append('"');
	       }
	  return sb.toString();
	}
	public String attribStateToString(String attrib) {
		String res = null;
		for(Attribute a : _attribs){
			if(a.attribToString().equals(attrib)){
				res = a.stateToString();
			}
			
		}
		return res;
	}

	@Override
	public void applyCommand(Command cmd) {
		
		String res = null;
		for(Attribute a : _attribs){
			if(a.attribToString().equals(cmd.getAttribute())){
				String val = cmd.getValue();
				a.setState(val);
			}
			
		}
	}
	
}

class Power extends Attribute{
	String _onoff;
	String _name;
	Power(String onoff) {
		this._name = "Power";
		this._onoff = onoff;
	}
	
	public String attribToString(){
		return this._name;
	}
	public String stateToString(){
		return _onoff;
	}

	@Override
	public void setState(String val) {
		this._onoff = val;
	}
	
}