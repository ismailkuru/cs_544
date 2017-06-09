package components;

import java.util.List;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Component.java
* **************************************************
* Definition: This file includes abstract class for a component that can be 
* part of an automobile controlled by the protocol. 
* *******************************************************
* Requirements:
* - SERVICE : It is part of the protocol’s
*  service because Component.java specifies that each component sub type, 
*  ex:AC.java, needs to implement applyCommand, 
*  which enables applying a command to change component’s state of attributes
* ==============================================================================
*/
public abstract class Component {
	
	
	//Name of the component
	protected final String _name;
	protected final ComponentType _typeCode;
	
	// SERVICE : part of the data being serviced-the attrs housed by this component
	protected List<Attribute> attributes;
	
	
	public Component(){ 
		this._name = "abstract component name"; 
		this._typeCode = ComponentType.INVALID_DEVICE;
	}
	public Component(String name, ComponentType code){
		this._typeCode = code;
		
		this._name = name;
		
		this.attributes = ComponentFactory.getDefault(_typeCode);
	}
	public abstract String getComponentName();	
	public abstract ComponentType getComponentCode();
	public abstract String attribToStringtoString(String attrib);
	public String toString() {return _name;}
	public abstract void applyCommand(Command cmd);
	public String attribStateToString(String attrib) {return attributes.get(attributes.indexOf(attrib)).stateToString();}
	public List<Attribute> getAttrs() {return attributes;}
		
}