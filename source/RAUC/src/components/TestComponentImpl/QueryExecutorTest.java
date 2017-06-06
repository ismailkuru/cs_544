package components.TestComponentImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import pdu.MessageImpl.UtilityControlReqMessage;
import pdu.MessageImpl.UtilityStateQueryMessage;
import users.User;

import com.google.gson.Gson;

import components.Automobile;
import components.Command;
import components.Component;
import components.ComponentType;
import components.QueryExecutor;
import components.ComponentImpl.AC;


public class QueryExecutorTest {
	@Test
	public void test() throws Exception {
		HashMap<String, HashMap<String , ArrayList<Component>>> _db;
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
		AC ac = new AC("0", ComponentType.AC);
		
		
		//Create Automobiles
		Automobile a2 = new Automobile("2");
		//Create Components
		AC ac2 = new AC("0", ComponentType.AC);
		//add to components list
		ArrayList<Component> lcomps = new ArrayList<Component>();
		lcomps.add(ac);
			
		
		
		//add to components list
		ArrayList<Component> lcomps2 = new ArrayList<Component>();
		lcomps2.add(ac);
		
		//Fill auto --> list components map
		acomps.put(a1.get_id(),lcomps);
		
		//Fill auto --> list components map
		acomps.put(a2.get_id(),lcomps2);
			
		//Fill user --> auto,list<comps> map
		_db.put(u1.get_uname(),acomps );
	
	
	
	
		UtilityStateQueryMessage umcr = new UtilityStateQueryMessage("2");
		
		String qres = QueryExecutor.executeQuery(umcr,acomps);
		
		System.out.println(qres);
	}


}
