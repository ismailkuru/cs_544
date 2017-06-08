package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import users.User;

import components.ComponentImpl.AC;
import components.ComponentImpl.Door;
import components.ComponentImpl.Radio;

public class Factory {
	HashMap<String, HashMap<String , ArrayList<Component>>> _db;
	public Factory(){
		
		//Initial DB
		_db = new HashMap<String, HashMap<String, ArrayList<Component>>>();
		
		//automobile1 --> components  map
		HashMap<String, ArrayList<Component>> acomps = new HashMap<String, ArrayList<Component>>();
		//automobile2 --> components  map
		HashMap<String, ArrayList<Component>> acomps1 = new HashMap<String, ArrayList<Component>>();
		
		//Fill the DB
		//Create Users
		
		//first user
		User u1 = new User("user1", "pass1");
		//Create Automobiles
		Automobile a1 = new Automobile("Auto1");
		//Create Components
		AC ac = new AC("AC0");
		Radio rd = new Radio("RD0");
		Door dr = new Door("DR0");
		//add to components list
		ArrayList<Component> lcomps = new ArrayList<Component>();
		lcomps.add(ac);
		lcomps.add(rd);
		lcomps.add(dr);
		//Fill auto --> list components map
		acomps.put(a1.get_id(),lcomps);
		//Fill user --> auto,list<comps> map
		_db.put(u1.get_uname(),acomps );
		
		
		//second user
		User u2 = new User("user2","pass2");
		//Create Automobile
		Automobile a2 = new Automobile("Auto2");
		//Create Components
		AC ac1 = new AC("1");
		Radio rd1 = new Radio("RD1");
		Door dr1 = new Door("DR1");
		//add to components list
		ArrayList<Component> lcomps1 = new ArrayList<Component>();
		lcomps1.add(ac1);
		lcomps1.add(rd1);
		lcomps.add(dr1);
		//Fill auto --> list components map
		acomps1.put(a2.get_id(),lcomps);
		//Fill user --> auto,list<comps> map
		_db.put(u2.get_uname(),acomps1 );
						
	}
	
	public HashMap<String, ArrayList<Component>> loadUserDB(String uname){
		
		return _db.get(uname) ;
		
	}
	


}
