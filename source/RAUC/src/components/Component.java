package components;


public abstract class Component {
	
	
	//Name of the component
	protected final String _name;
	protected final ComponentType _typeCode;
	protected final String _autoId;
	
	public Component(){this._autoId = "abstract auto id"; this._name = "abstract auto name"; this._typeCode = ComponentType.INVALID_DEVICE;}
	public Component(String name, ComponentType code, String autoId){
		this._typeCode = code;
		this._name = name;
		this._autoId = autoId;
	}
	
	public abstract String getComponentName();
	
	public abstract ComponentType getComponentCode();
	
	public abstract String getComponentAutoId();
	
	public abstract void applyCommand(Command cmd);
	
		
}
