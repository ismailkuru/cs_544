package specs;

public enum DFAState {
	CLOSED						("Closed"),
	S_AWAITS_CONN_REQUEST		("Server awaits connection"),
	S_AWAITS_AUTHEN_REQUEST 	("Server awaits authentication request"),
	C_AWAITS_AUTHEN_RESPONSE 	("Client awaits authentication response"),
	S_AWAITS_COMM_REQUEST		("Server awaits command request"),
	C_AWAITS_COMM_RESPONSE		("Client awaits command response"),
	
	S_AWAITS_QUERY_REQUEST		("Server awaits query request"),
	C_AWAITS_QUERY_RESPONSE		("Client awaits query response");

	private String desc;
	
	private DFAState(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;

	}
}