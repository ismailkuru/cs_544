package components;

import java.util.*;

public final class ComponentFactory {
		
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
				String pwr = "lock";
				String[] pwrVals = {"closed", "open"};
				attrs.add(new Attribute(pwr, Arrays.asList(pwrVals)));
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
				String pwr = "power";
				String[] pwrVals = {"on", "off"};
				attrs.add(new Attribute(pwr, Arrays.asList(pwrVals)));
				break;
				
			case 1:
				String name = "temp";
				String[] vals = {"hot", "cold", "middle"};
				attrs.add(new Attribute(name, Arrays.asList(vals)));
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
				attrs.add(new Attribute("power", Arrays.asList(pwrVals)));
				break;
				
			case 1:
				String[] vols = {"mute", "quiet", "mid", "loud"};
				attrs.add(new Attribute("volume", Arrays.asList(vols)));
				break;
			
			case 2:
				String[] presets = {"102.1", "101.1", "104.5", "98.1"};
				attrs.add(new Attribute("presets", Arrays.asList(presets)));
			default: break;
		}
	}
		
		return attrs;
	}
}
