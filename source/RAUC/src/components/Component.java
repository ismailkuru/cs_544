package components;

import java.util.List;

public abstract class Component {
	
	
	//Name of the component
	protected final String _name;
	protected final ComponentType _typeCode;
	
	// the attrs housed by this component
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
	public abstract String toString();
	public abstract void applyCommand(Command cmd);
	public abstract String attribStateToString(String attrib);
		
}