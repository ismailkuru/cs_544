package components;


public abstract class Component {
	
	
	//Name of the component
	protected final String _name;
	protected final ComponentType _typeCode;
	
	
	public Component(){ 
		this._name = "abstract component name"; 
		this._typeCode = ComponentType.INVALID_DEVICE;
	}
	public Component(String name, ComponentType code){
		this._typeCode = code;
		
		this._name = name;
		//this._autoId = autoId;
	}
	public abstract String getComponentName();	
	public abstract ComponentType getComponentCode();
	public abstract String attribToStringtoString(String attrib);
	public abstract String toString();
	public abstract void applyCommand(Command cmd);
	
		
}
