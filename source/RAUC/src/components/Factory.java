package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import users.User;

import components.ComponentImpl.AC;

public class Factory {
	
	private Factory(){}
	public static void initDB(){
		
		//Initial DB
		HashMap<String, HashMap<String , ArrayList<Component>>> _db = new HashMap<String, HashMap<String, ArrayList<Component>>>();
		//automobile --> components  map
		HashMap<String, ArrayList<Component>> acomps = new HashMap<String, ArrayList<Component>>();
	
		
		//Fill the DB
		
		//Create Users
		User u1 = new User("ismail", "1234");
		//Create Automobiles
		Automobile a1 = new Automobile("1");
		//Create Components
		AC ac = new AC("0", ComponentType.AC);
		
		//add to components list
		ArrayList<Component> lcomps = new ArrayList<Component>();
		lcomps.add(ac);
		
		//Fill auto --> list components map
		acomps.put(a1.get_id(),lcomps);
		
	
		//Fill user --> auto,list<comps> map
		_db.put(u1.get_uname(),acomps );
		
		
		//[TODO:] Add auto and components to this factory
		
	}
	
	
	


}
