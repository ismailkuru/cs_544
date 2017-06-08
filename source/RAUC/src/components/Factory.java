package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import users.User;

import components.ComponentImpl.AC;

public class Factory {
	HashMap<String, HashMap<String , ArrayList<Component>>> _db;
	public Factory(){
		
		//Initial DB
		_db = new HashMap<String, HashMap<String, ArrayList<Component>>>();
		//automobile --> components  map
		HashMap<String, ArrayList<Component>> acomps = new HashMap<String, ArrayList<Component>>();
	
		
		//Fill the DB
		
		//Create Users
		User u1 = new User("user", "pass");
		//Create Automobiles
		Automobile a1 = new Automobile("1");
		//Create Components
		AC ac = new AC("0");
		
		//add to components list
		ArrayList<Component> lcomps = new ArrayList<Component>();
		lcomps.add(ac);
		
		//Fill auto --> list components map
		acomps.put(a1.get_id(),lcomps);
		
	
		//Fill user --> auto,list<comps> map
		_db.put(u1.get_uname(),acomps );
		
		
		//[TODO:] Add auto and components to this factory
		
	}
	
	public HashMap<String, ArrayList<Component>> loadUserDB(String uname){
		
		return _db.get(uname) ;
		
	}
	


}
