package pdu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.MessageImpl.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Message{
	public HeaderChunk _header;
	public List<ContentChunk> _content;

	public Message() {

    }

	public Message(HeaderChunk h, List<ContentChunk>c){
	    this._content = c;
	    this._header = h;
	}

    public abstract MessageType getMessageType();

    public JsonElement toJson() {
        JsonParser jp = new JsonParser();
        return jp.parse(this.toString());
    }

    public List<ContentChunk> getContent() {
        return _content;
    }

    public HeaderChunk getHeader() {
        return _header;
    }

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            array.write(new byte[]{
                    (byte) getHeader().getChunkCount(),
                    (byte) getHeader().getMessageType().getOpcode().intValue()
            });
            for (ContentChunk c  : this.getContent()) {
                array.write(new byte[]{(byte) c.getSize()});
                array.write(c.getContent().getBytes());
            }
            return array.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Message fromStream(InputStream stream) throws IOException {
        int chunkCount = stream.read();
        int opCode = stream.read();
        MessageType type;
        try {
            type = MessageType.getMType(opCode);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid message type " + opCode, e);
        }
        HeaderChunk header = new HeaderChunk(type, Integer.toString(chunkCount));
        List<ContentChunk> chunks = new ArrayList<>(chunkCount);
        for(;chunkCount > 0; chunkCount--) {
            int chunkSize = stream.read();
            byte[] content = new byte[chunkSize];
            stream.read(content, 0, chunkSize);
            chunks.add(new ContentChunk(Integer.toString(chunkSize), new String(content)));
        }
        return fromType(type, header, chunks);
    }

    private static Message fromType(MessageType type, HeaderChunk header, List<ContentChunk> content) {
        switch (type) {
            case OP_AUTH:
                return new UserAuthenMessage(header, content);
            case OP_COMMAND:
                return new UtilityControlReqMessage(header, content);
            case OP_COMMAND_RECEIVED:
                return new RequestReceivedMessage(header, content);
            case OP_ERROR:
                return new PermanentErrorMessage(header, content);
            case OP_INFO:
                return new QueryResultMessage(header, content);
            case OP_QUERY:
                return new UtilityStateQueryMessage(header, content);
            case OP_SHUTDOWN:
                return new TerminationMessage(header, content);
            case OP_SUCCESS:
                return new AckMessage(header, content);
            default:
                return new TemporaryErrorMessage(header, content);
        }
    }

    public String toString(){
		/*
		String strHeader;
		String contHeader;

        if(versionExists()){
            strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
            contHeader = "Content=";
            for(int i = 0 ; i<this.getContent().size(); i ++){
                contHeader += "["+ this.getContent().get(i).getSize() + ":"+ this.getContent().get(i).getContent()+  "]";
            }
            return strHeader + "#" + contHeader;
        }
        else{
            strHeader = "Header=[" + this.getHeader().getMessageType() + ":" + this.getHeader().getChunkCount() + "]";
            return strHeader;
        }
        */
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
