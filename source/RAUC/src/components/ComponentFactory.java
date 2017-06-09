package components;

import java.util.*;

/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ComponentFactory.java
* **************************************************
* Definition: 
* *******************************************************
* Requirements:
* - STATEFUL : 
* - SERVICE : 
* ==============================================================================
*/ 

public class ComponentFactory {
	
	static String atrName;
	static ArrayList<String> valsLst;
		
	public ComponentFactory() {
		
	}
	public static ArrayList<Attribute> getDefault(ComponentType type) {
		switch (type) {
		case AC: return defaultAC();
		case RADIO: return defaultRadio();
		case DOOR: return defaultDoor();
		default: return null;
		}
	}
	private static ArrayList<Attribute> defaultDoor() {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		for (int i = 0; i < 1; i++) {
			switch (i) {
			case 0:
				atrName = "lock";
				String[] lckVals ={"closed", "open"};
				ArrayList<String> valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(lckVals));
				attrs.add(new Attribute(atrName, valsLst));
				break;
				
			default: break;
		}
	}
		return attrs;
	}	
	
	private static ArrayList<Attribute> defaultAC() {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		for (int i = 0; i < 2; i++) {
			switch (i) {
			case 0:
				String atrName = "power";
				String[] acPwrVals = {"on", "off"};
				ArrayList<String> valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(acPwrVals));
				attrs.add(new Attribute(atrName, valsLst));
				break;
				
			case 1:
				atrName = "temp";
				String[] tmpVals = {"cld", "mid", "hot"};
				valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(tmpVals));
				attrs.add(new Attribute(atrName, valsLst));
				break;
				
			default: break;
		}
	}
		
		return attrs;
	}
	
	private static ArrayList<Attribute> defaultRadio() {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				String[] pwrVals = {"on", "off"};
				valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(pwrVals));
				attrs.add(new Attribute("power", valsLst));
				break;
				
			case 1:
				String[] vols = {"mute", "quiet", "mid", "loud"};
				valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(vols));
				attrs.add(new Attribute("volume", valsLst));
				break;
			
			case 2:
				String[] presets = {"102.1", "101.1", "104.5", "98.1"};
				valsLst = new ArrayList<String>();
				valsLst.addAll(Arrays.asList(presets));
				attrs.add(new Attribute("presets", valsLst));
			default: break;
		}
	}
		
		return attrs;
	}
}
