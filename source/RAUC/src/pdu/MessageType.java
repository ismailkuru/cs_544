package pdu;

public enum MessageType {
	INVALID_MESSAGE (-20),
	OP_SHUTDOWN 	(-1),
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
	public static MessageType getMessageTypeFromString(String mstype)throws Exception{
		
		if(mstype == "OP_SHUTDOWN") {
			return OP_SHUTDOWN;
		}else if(mstype == "OP_INFO"){
			return OP_INFO;
		}else if(mstype == "OP_QUERY"){
			return  OP_QUERY;
		}else if(mstype == "OP_ERROR"){
			return OP_ERROR;
		}else if(mstype == "OP_TMP_ERROR"){
			return OP_TMP_ERROR;			
		}else if(mstype == "OP_COMMAND_RECEIVED"){
			return OP_COMMAND_RECEIVED;
		}else if(mstype == "OP_FAILURE"){
			return OP_FAILURE;
		}else if(mstype == "OP_AUTH"){
			return OP_AUTH;
		}else if(mstype == "OP_COMMAND"){
			return OP_COMMAND;
		}else if(mstype == "OP_SUCCESS_VER"){
			return OP_SUCCESS_VER;
		}else throw new Exception("Invalid message type: " + INVALID_MESSAGE);
		
	}

}
