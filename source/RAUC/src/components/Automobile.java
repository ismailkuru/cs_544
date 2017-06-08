package components;

import java.util.ArrayList;
import components.ComponentImpl.*;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Automobile.java
* **************************************************
* Definition: This file includes logical representation of an automobile. 
* It includes list of components which can be AC, Radio etc.
* *******************************************************
* Related Requirements:
* - SERVICE : This file includes implementation which is part of SERVICE
* requirement as they are query/change the values of attributes of components
* ==============================================================================
*/ 
public class Automobile {
	String _id;
	
	public Automobile(String id ) {
		_id = id;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
		
}
