package specs;

public enum DFAState {
	CLOSED						("Closed"),
	S_AWAITS_AUTHEN_REQUEST		("Server awaits authentication request"),
	SC_INIT				 	("Client awaits authentication response / Server awaits connection request"),
	S_AWAITS_COMM_REQUEST		("Server awaits command request"),
	S_AWAITS_QUERY_REQUEST		("Server awaits query request"),
	C_AWAITS_RESPONSE			("Client awaits response");

	private String desc;
	
	private DFAState(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;

	}
}