package components.ComponentImpl;

import java.util.List;

import components.Attribute;
import components.Command;
import components.Component;
import components.ComponentType;

public class Radio extends Component {
	
	public Radio (String name) {
		super(name, ComponentType.RADIO);
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
	public String attribToStringtoString(String attrib) {
		String res = null;
		for(Attribute a : getAttributes()){
			if(a.attribToString().equals(attrib)){
				res = a.stateToString();
			}
			
		}
		return res;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	@Override
	public String toString() {
	     StringBuilder sb = new StringBuilder();
	       for(Attribute a : attributes){
	            sb.append(a.attribToString());
	            //sb.append('=').append('"');
	            sb.append(a.stateToString());
	            //sb.append('"');
	       }
	  return sb.toString();
	}

	@Override
	public void applyCommand(Command cmd) {
		// TODO Auto-generated method stub
		
	}
	
}
