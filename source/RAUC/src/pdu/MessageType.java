package pdu;

public enum MessageType {
	INVALID_MESSAGE (-20),
	OP_SHUTDOWN 	(9),
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
	public static MessageType getMType(Integer op)throws Exception{
		if(op == -1){
			return OP_SHUTDOWN;
		}else if(op == 16){
			return OP_INFO;
		}else if(op == 6){
			return  OP_QUERY;
		}else if(op == 255){
			return OP_ERROR;
		}else if(op == 254){
			return OP_TMP_ERROR;			
		}else if(op == 15){
			return OP_COMMAND_RECEIVED;
		}else if(op == 17){
			return OP_FAILURE;
		}else if(op == 3){
			return OP_AUTH;
		}else if(op == 5){
			return OP_COMMAND;
		}else if(op == 12){
			return OP_SUCCESS_VER;
		}else if(op == 4){
			return OP_SUCCESS;		
		}else 
			throw new Exception("Invalid message type: " + INVALID_MESSAGE);
		
	}
	public static MessageType getMessageTypeFromString(String mstype)throws Exception{
		
		if(mstype.equals("OP_SHUTDOWN")){
			return OP_SHUTDOWN;
		}else if(mstype.equals("OP_INFO")){
			return OP_INFO;
		}else if(mstype.equals("OP_QUERY")){
			return  OP_QUERY;
		}else if(mstype.equals("OP_ERROR")){
			return OP_ERROR;
		}else if(mstype.equals("OP_TMP_ERROR")){
			return OP_TMP_ERROR;			
		}else if(mstype.equals("OP_COMMAND_RECEIVED")){
			return OP_COMMAND_RECEIVED;
		}else if(mstype.equals("OP_FAILURE")){
			return OP_FAILURE;
		}else if(mstype.equals("OP_AUTH")){
			return OP_AUTH;
		}else if(mstype.equals("OP_COMMAND")){
			return OP_COMMAND;
		}else if(mstype.equals("OP_SUCCESS_VER")){
			return OP_SUCCESS_VER;
		}else if(mstype.equals("OP_SUCCESS")){
			return OP_SUCCESS;		
		}else 
			throw new Exception("Invalid message type: " + INVALID_MESSAGE);
		
	}

}
