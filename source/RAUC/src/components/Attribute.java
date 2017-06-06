package components;

import java.util.List;

public class Attribute {
	private String _attr;
	private List<String> _values;
	private String _currentValue;
	
	public Attribute(String attr, List<String> values) {
		_attr = attr;
		_values =  values;
		_currentValue = _values.get(0); // make the first setting the default setting
	}
	
	public List<String> getValues() {
		return _values;
	}
	
	public String getCurrent() {
		return _currentValue;
	}
	
	public String getName() {
		return _attr;
	}
	
	public String attribToString() {
		return this._attr;
	}
	
	public String stateToString() {
		return this._currentValue;
	}
	 public void set(String value) {
		 if (_values.contains(value)) {
			 _currentValue = value;
		 }
	}
}
