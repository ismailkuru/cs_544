package pdu;

import com.google.gson.Gson;
import org.json.JSONObject;

import pdu.MessageImpl.AuthenAckMessage;
import pdu.MessageImpl.PermanentErrorMessage;
import pdu.MessageImpl.QueryResultMessage;
import pdu.MessageImpl.RequestReceivedMessage;
import pdu.MessageImpl.TemporaryErrorMessage;
import pdu.MessageImpl.TerminationMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import pdu.MessageImpl.UtilityStateQueryMessage;

public class MessageFactory{
	
	private static JSONObject jObject = null;
	private MessageFactory(){}
	
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
	    	   m = gson.fromJson(jString, AuthenAckMessage.class);
	   			return m;
	    	   
	       }else if(mt == MessageType.OP_SUCCESS_VER){
	    	   m = gson.fromJson(jString, AuthenAckMessage.class);
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
