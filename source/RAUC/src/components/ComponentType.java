package components;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: ComponentType.java
* **************************************************
* Definition: .
* *******************************************************
* Requirements:
* - STATEFUL : 
* - SERVICE : 
* ==============================================================================
*/ 
public enum ComponentType {

	AC		(0),
	RADIO   (1),
	DOOR	(2),
	INVALID_DEVICE (-1);


	
	private final Integer compCode;
	
	private ComponentType(Integer compCode) {
		this.compCode = compCode;
	}
	
	
	public static ComponentType typeFromString(String code) throws Exception {
		switch (Integer.parseInt(code)) {
		case 0: return AC;
		case 1: return RADIO;
		case 2: return DOOR;
		default: throw new Exception("Invalid component code: " + INVALID_DEVICE);
		}
	}

	public Integer getComponentCode() {
		return compCode;
	}
	
}
