package pdu;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.json.JSONObject;

import pdu.ChunkImpl.ContentChunk;
import pdu.ChunkImpl.HeaderChunk;
import pdu.MessageImpl.AckMessage;
import pdu.MessageImpl.PermanentErrorMessage;
import pdu.MessageImpl.QueryResultMessage;
import pdu.MessageImpl.RequestReceivedMessage;
import pdu.MessageImpl.TemporaryErrorMessage;
import pdu.MessageImpl.TerminationMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import pdu.MessageImpl.UtilityStateQueryMessage;
import utils.DataTypeConverter;

public class MessageFactory{
	
	private static JSONObject jObject = null;
	private MessageFactory(){}
	
	public static Message createMessage(byte[][] byteA) throws Exception{
		
		List<byte[]> bytel = DataTypeConverter.toByteList(byteA);
		Message m = byteToMessage(bytel);
		return m;
	}

	private static Message byteToMessage(List<byte[]> byteL) throws Exception{
		Message m = null;

		byte[] hdr = byteL.get(0);		
		Byte mType = hdr[0];
		MessageType mt = MessageType.getMType(mType.intValue());

		//Chunk Count -- Typed String
		Byte chnkC = hdr[1];
		String cc = Integer.toString(chnkC.intValue());
		HeaderChunk hdrC = new HeaderChunk(mt, cc);
		ArrayList<ContentChunk> cntChunks = new ArrayList<ContentChunk>();
		
		if(byteL.size()>1){
	
			for(int i=1 ;i<byteL.size();i++){
				String cntnt=null;
				String size=null;
				ContentChunk cnt = null;
				
				if(i%2 == 0){
					byte[] bcntnt = byteL.get(i);
					cntnt = new String(bcntnt);
					//System.out.println("i is even and content is " + cntnt);
				}
				else{
					byte[] bsz = byteL.get(i);
					Byte bbsz = bsz[0];
					//System.out.println("size of content as int " + bbsz.intValue());
					int sz = bbsz.intValue() & 0xFF;
					size = Integer.toString(sz);
					//System.out.println("i is odd and size of content is " + size);
						
				}
				cnt = new ContentChunk(size, cntnt);
				cntChunks.add(cnt);
			}
			
		}
		
		 if(mt == MessageType.OP_AUTH)
	       {
	   			m = new UserAuthenMessage(hdrC,cntChunks);
	   			return m; 
	       }else if(mt == MessageType.OP_COMMAND){
	    		m = new UtilityControlReqMessage(hdrC,cntChunks);
	   			return m; 
	       }else if(mt == MessageType.OP_COMMAND_RECEIVED){
	    		m = new RequestReceivedMessage(hdrC,cntChunks);
	   			return m; 
	       }else if(mt == MessageType.OP_ERROR){
	    		m = new PermanentErrorMessage(hdrC,cntChunks);
	   			return m; 
	       }else if(mt == MessageType.OP_FAILURE){
	    	   //TODO // We should discuss this
	    		System.out.print("Are we going to add failure ?");
	   			return m; 
	   			
	       }else if(mt == MessageType.OP_INFO){
	    		m = new QueryResultMessage(hdrC,cntChunks);
	   			return m; 
	       }else if(mt == MessageType.OP_QUERY){
	    		m = new UtilityStateQueryMessage(hdrC,cntChunks);
	   			return m; 
	       	   
	       }else if(mt == MessageType.OP_SHUTDOWN){
	    		m = new TerminationMessage(hdrC,cntChunks);
	   			return m; 
	    	   
	       }else if(mt == MessageType.OP_SUCCESS){
	    		m = new AckMessage(hdrC,cntChunks);
	   			return m; 
	    	   
	       }else if(mt == MessageType.OP_SUCCESS_VER){
	    		m = new AckMessage(hdrC,cntChunks);
	   			return m; 
	       }
	       else
	       {
	    		m = new TemporaryErrorMessage(hdrC,cntChunks);
	   			return m; 
	       } 
	}
	
	
	//[TODO : We need to discuss some of them]
	public static Message createMessage(String jString) throws Exception{
		
			Message m = null;
			jObject = new JSONObject(jString);
	       JSONObject header = jObject.getJSONObject("_header");
	       String messageType = header.getString("_first");
	       
	       //Debug print 
	      // System.out.print("Type of the message is : "+messageType );
	       
	       MessageType mt = MessageType.getMessageTypeFromString(messageType);
	       
	       Gson gson = new Gson();
	       
	       if(mt == MessageType.OP_AUTH)
	       {
	    	   //Debug print
	    	  // System.out.println("\n Message type is authentication");
	   			m = gson.fromJson(jString, UserAuthenMessage.class);
	   			return m; 

	       }else if(mt == MessageType.OP_COMMAND){
	    	   m = gson.fromJson(jString, UtilityControlReqMessage.class);
	   			return m; 
	         	   
	       }else if(mt == MessageType.OP_COMMAND_RECEIVED){
	    	   m = gson.fromJson(jString, RequestReceivedMessage.class);
	   			return m; 

	       }else if(mt == MessageType.OP_ERROR){
	    	   	m = gson.fromJson(jString, PermanentErrorMessage.class);
	   			return m; 
	       }else if(mt == MessageType.OP_FAILURE){
	    	   
	    		System.out.print("Are we going to add failure ?");
	   			return m; 
	   			
	       }else if(mt == MessageType.OP_INFO){
	    	   m = gson.fromJson(jString, QueryResultMessage.class);
	   			return m;
	       }else if(mt == MessageType.OP_QUERY){
	    	   m = gson.fromJson(jString, UtilityStateQueryMessage.class);
	   			return m;
	       	   
	       }else if(mt == MessageType.OP_SHUTDOWN){
	    	   m = gson.fromJson(jString, TerminationMessage.class);
	   			return m;
	    	   
	       }else if(mt == MessageType.OP_SUCCESS){
	    	  // System.out.println("\n Message type is success ack");
	    	   m = gson.fromJson(jString, AckMessage.class);
	   			return m;
	    	   
	       }else if(mt == MessageType.OP_SUCCESS_VER){
	    	   m = gson.fromJson(jString, AckMessage.class);
	   			return m;
	       }
	       else
	       {
	    	   m = gson.fromJson(jString, TemporaryErrorMessage.class);
	   			return m;
	       }
		//{"_header":{"_first":"OP_QUERY","_second":"1"},"_content":[{"_first":"32","_second":"1"}]}

	}
	
}
