package pdu.ChunkImpl;

public enum MessageType {
	OP_CONNECTION				((byte)0),
	OP_INFO 		((byte)1),
	OP_QUERY 	((byte)2),
	OP_ERROR 	((byte)3),
	OP_FAILURE		((byte)4),
	OP_AUTH	((byte)5),
	OP_INIT ((byte)6),
	OP_COMMAND 	((byte)7),
	OP_SUCCESS 	((byte)8),
	OP_SHUTDOWN 	((byte)9);

	
	private final byte opcode;
	
	private MessageType(byte opcode) {
		this.opcode = opcode;
	}

	public byte getOpcode() {
		return opcode;
	}
	
}
