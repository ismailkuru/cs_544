package components.ComponentImpl;

import java.util.List;

import components.Attribute;
import components.Command;
import components.Component;
import components.ComponentType;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Door.java
* **************************************************
* Definition: This is representation of one of the components that
*  an automobile can have. It is sub type of the Component.java
* *******************************************************
* Requirements:
* - STATEFUL : 
* - SERVICE : 
* ==============================================================================
*/  
public class Door extends Component{
	public Door (String name) {
		super(name, ComponentType.DOOR);
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
	            sb.append('-');
	            sb.append(a.stateToString());
	            //sb.append('"');
	       }
	  return super._name + ":" + sb.toString();
	}
	
	public String attribStateToString(String attrib) {
		return attributes.get(attributes.indexOf(attrib)).stateToString();
	}

	@Override
	public void applyCommand(Command cmd) {
		for(Attribute a : attributes){
			if(a.attribToString().equals(cmd.getAttribute())){
				String val = cmd.getValue();
				a.set(val);
			}
			
		}
		
	}
}
