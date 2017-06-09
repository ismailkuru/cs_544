package components.ComponentImpl;

import java.util.*;

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
* File name: AC.java
* **************************************************
* Definition: This is representation of one of the components that
*  an automobile can have. It is sub type of the Component.java
* *******************************************************
* Requirements:
* - SERVICE : It is sub-type of the Component.java
* ==============================================================================
*/      
public class AC extends Component {
	
	public AC(String name) {
		super(name, ComponentType.AC);
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
	// return the name of this component
	public String toString() {
		return super.toString();
	}
	
	public String attribStateToString(String attrib) {
		return super.attribStateToString(attrib);
	}

	@Override
	public void applyCommand(Command cmd) {
		//String res = null;
		for(Attribute a : attributes){
			if(a.attribToString().equals(cmd.getAttribute())){
				String val = cmd.getValue();
				a.set(val);
			}
			
		}
		
	}
	
}