package pdu;

import com.google.gson.Gson;
import org.json.JSONObject;

import pdu.MessageImpl.AckMessage;
import pdu.MessageImpl.PermanentErrorMessage;
import pdu.MessageImpl.QueryResultMessage;
import pdu.MessageImpl.RequestReceivedMessage;
import pdu.MessageImpl.TemporaryErrorMessage;
import pdu.MessageImpl.TerminationMessage;
import pdu.MessageImpl.UserAuthenMessage;
import pdu.MessageImpl.UtilityControlReqMessage;
import pdu.MessageImpl.UtilityStateQueryMessage;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: MessageFactory.java -- Deprecated
* **************************************************
* Definition: This file includes implementation of factory methods which
*  generates Message objects.
* *******************************************************
* Requirements:
* - SERVICE : Messages are core components of the protocol service. It also
* enhances the reliability to keep the state transitions in accordance with
* message types.
* 
* ==============================================================================
*/  
public class MessageFactory{
	
	private static JSONObject jObject = null;
	private MessageFactory(){}

	public static Message createMessage(String jString) throws Exception{
		Message m = null;
		jObject = new JSONObject(jString);
		JSONObject header = jObject.getJSONObject("_header");
		String messageType = header.getString("_first");

		MessageType mt = MessageType.valueOf(messageType);

		Gson gson = new Gson();

		switch (mt) {
			case OP_AUTH:
				m = gson.fromJson(jString, UserAuthenMessage.class);
				return m;
			case OP_COMMAND:
				m = gson.fromJson(jString, UtilityControlReqMessage.class);
				return m;
			case OP_COMMAND_RECEIVED:
				m = gson.fromJson(jString, RequestReceivedMessage.class);
				return m;
			case OP_ERROR:
				m = gson.fromJson(jString, PermanentErrorMessage.class);
				return m;
			case OP_INFO:
				m = gson.fromJson(jString, QueryResultMessage.class);
				return m;
			case OP_QUERY:
				m = gson.fromJson(jString, UtilityStateQueryMessage.class);
				return m;
			case OP_SHUTDOWN:
				m = gson.fromJson(jString, TerminationMessage.class);
				return m;
			case OP_SUCCESS:
				m = gson.fromJson(jString, AckMessage.class);
				return m;
			default:
				m = gson.fromJson(jString, TemporaryErrorMessage.class);
				return m;
		}
		//{"_header":{"_first":"OP_QUERY","_second":"1"},"_content":[{"_first":"32","_second":"1"}]}
	}
}
