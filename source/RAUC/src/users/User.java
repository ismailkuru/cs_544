package users;

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
