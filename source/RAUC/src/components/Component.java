package components;


public abstract class Component {
	
	
	//Name of the component
	protected final String _name;//[TODO] Remove this each autor can have one component of each type.
	protected final ComponentType _typeCode;
	
	//[TODO] : We may prefer to remove the this autoid and use 
	// the map inside factory. It is an design issue
	//protected final String _autoId;
	
	public Component(){/*this._autoId = "abstract auto id";*/ this._name = "abstract component name"; this._typeCode = ComponentType.INVALID_DEVICE;}
	public Component(String name, ComponentType code){
		this._typeCode = code;
		
		this._name = name;
		//this._autoId = autoId;
	}
	
	public abstract String getComponentName();
	
	public abstract ComponentType getComponentCode();
	
	//public abstract String getComponentAutoId();
	
	public abstract void applyCommand(Command cmd);
	
		
}
