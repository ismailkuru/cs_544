package users;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: User.java
* **************************************************
* Definition: This file includes representation of user which is one of the
*  core parts of the protocol service.
* *******************************************************
* Requirements:
* - SERVICE: Authentication of an user with user name and password which is one 
* important part of the protocol’s service. 
* Each Client driver object is created with its user name and password 
* and these inputs are used to create/send authenticate message, UserAuthMessage.java.
* ==============================================================================
*/ 
public class User {
	String _uname;
	String _pass;
	
	public User(String uname, String pass){
		
		_uname = uname;
		_pass = pass;
	}

	public String get_uname() {
		return _uname;
	}

	public void set_uname(String _uname) {
		this._uname = _uname;
	}

	public String get_pass() {
		return _pass;
	}

	public void set_pass(String _pass) {
		this._pass = _pass;
	}

}
