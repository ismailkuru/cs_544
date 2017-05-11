package specs;

public enum DFAState {
	CLOSED				("Closed"),
	S_AWAITS_INIT 		("Server awaits init"),
	S_AWAITS_AUTHEN 	("Server awaits authentication request"),
	C_AWAITS_CONFIRM 	("Client awaits confirmation"),
	S_AWAITS_ACTION		("Server awaits command/query"),
	C_AWAITS_RESPONSE	("Client awaits response");

	private String desc;
	
	private DFAState(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;

	}
}