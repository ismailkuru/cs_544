package components;

import java.util.ArrayList;
import components.ComponentImpl.*;

public class Automobile {
//[TODO] User , automobile etc. need to be added. A random autonmobile and component generator
// might be implemented ....
	String _id;
	ArrayList<Component> components;
	
	public Automobile(String id ) {
		components = new ArrayList<Component>();
		_id = id;
		addComponent(new AC("ac1"));
		addComponent(new Radio("radio1"));
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public void addComponent(Component c) {
		components.add(c);
	}
	
}
