package components;

import java.util.List;
import java.util.ArrayList;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Attribute.java
* **************************************************
* Definition: Definitions of attributes associated with each component of an 
* automobile included in this file.
* *******************************************************
* Related Requirements:
* - SERVICE : It is part of the service of the protocol as we query the 
* current values of the attributes in read only queries, UtilityStateQuery
* and change the current value of an attribute through UtilityReqCommand.
* ==============================================================================
*/ 
public class Attribute {
	private String _attr;
	private ArrayList<String> _values;
	private String _currentValue;
	
	public Attribute(String attr, ArrayList<String> values) {
		_attr = attr;
		_values =  values;
		_currentValue = _values.get(0); // make the first setting the default setting
	}
	
	public ArrayList<String> getValues() {
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
