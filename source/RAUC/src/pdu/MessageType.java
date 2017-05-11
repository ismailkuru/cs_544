package pdu;

public enum MessageType {
	OP_SHUTDOWN 	(-1),
	OP_CONNECTION	(0),
	OP_INFO 		(16),
	OP_QUERY 		(6),
	OP_ERROR 		(255),
	OP_TMP_ERROR 	(254),
	OP_COMMAND_RECEIVED	(15),
	OP_FAILURE		(17),	
	OP_AUTH			(3),
	OP_COMMAND 		(5),
	OP_SUCCESS 		(4),
	OP_SUCCESS_VER (12);


	
	private final Integer opcode;
	
	private MessageType(Integer opcode) {
		this.opcode = opcode;
	}

	public Integer getOpcode() {
		return opcode;
	}
	
}
