package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import users.User;

import components.ComponentImpl.AC;
import components.ComponentImpl.Door;
import components.ComponentImpl.Radio;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Factory.java
* **************************************************
* Definition: This file includes instantiation of couple of objects (User, Automobile, Components
etc.) to demonstrate that protocol is servicing properly.
* *******************************************************
* Requirements:
* - SERVICE : This file partly satisfies the SERVICE requirement as it instantiates the 
* data which is needed for client and server message exchange.
* ==============================================================================
*/ 
public class Factory {
	HashMap<String, HashMap<String , ArrayList<Component>>> _db;
	public Factory(){
		
		//Initial DB
		_db = new HashMap<String, HashMap<String, ArrayList<Component>>>();
		//automobile --> components  map
		HashMap<String, ArrayList<Component>> acomps = new HashMap<String, ArrayList<Component>>();
		//automobile --> components  map
		HashMap<String, ArrayList<Component>> acomps1 = new HashMap<String, ArrayList<Component>>();
		
		//Fill the DB
		
		//Create Users
		User u1 = new User("user1", "pass1");
		//Create Automobiles
		Automobile a1 = new Automobile("au1");
		//Create Components
		AC ac = new AC("ac1");
		Radio rd = new Radio("rd1");
		Door dr = new Door("dr1");
		//add to components list
		ArrayList<Component> lcomps = new ArrayList<Component>();
		lcomps.add(ac);
		lcomps.add(rd);
		
		//Fill auto --> list components map
		acomps.put(a1.get_id(),lcomps);
		
	
		//Fill user --> auto,list<comps> map
		_db.put(u1.get_uname(),acomps );
		
		
		//[TODO:] Add auto and components to this factory
		
		//second user
		User u2 = new User("user2","pass2");
		//Create Automobile
		Automobile a2 = new Automobile("au2");
		//Create Components
		AC ac1 = new AC("ac2");
		Radio rd1 = new Radio("rd2");
		Door dr1 = new Door("dr2");
		//add to components list
		ArrayList<Component> lcomps1 = new ArrayList<Component>();
		lcomps1.add(ac1);
		lcomps1.add(rd1);
		lcomps1.add(dr1);
		//Fill auto --> list components map
		acomps1.put(a2.get_id(),lcomps1);
		//Fill user --> auto,list<comps> map
		_db.put(u2.get_uname(),acomps1 );
		
	}
	
	public HashMap<String, ArrayList<Component>> loadUserDB(String uname){
		
		return _db.get(uname) ;
		
	}
	


}