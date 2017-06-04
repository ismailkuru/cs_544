package pdu;

public enum MessageType {
	OP_AUTH			(3),
	OP_SUCCESS 		(4),
	OP_COMMAND 		(5),
	OP_COMMAND_RECEIVED	(15),
	OP_QUERY 		(6),
	OP_INFO 		(16),
	OP_SHUTDOWN 	(9),
	OP_ERROR 		(255),
	OP_TMP_ERROR 	(254),
	INVALID_MESSAGE (-20);
	
	private final Integer opcode;
	
	MessageType(Integer opcode) {
		this.opcode = opcode;
	}

	public Integer getOpcode() {
		return opcode;
	}
	public static MessageType getMType(Integer op) throws IllegalArgumentException{
		switch (op) {
			case 3:
				return OP_AUTH;
			case 4:
				return OP_SUCCESS;
			case 5:
				return OP_COMMAND;
			case 15:
				return OP_COMMAND_RECEIVED;
			case 6:
				return OP_QUERY;
			case 16:
				return OP_INFO;
			case 9:
				return OP_SHUTDOWN;
			case 255:
				return OP_ERROR;
			case 254:
				return OP_TMP_ERROR;
			default:
				throw new IllegalArgumentException("Invalid message type: " + INVALID_MESSAGE);
		}
	}
}
