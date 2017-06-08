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
* - STATEFUL : 
* - SERVICE : the messages are an important part in the protocol service
*   definition: initiating a connection, initializing states of components of the
*   automobile at the client/server side ex: Command / ACK  etc.
* 
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
