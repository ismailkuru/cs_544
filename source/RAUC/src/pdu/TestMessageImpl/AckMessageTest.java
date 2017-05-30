package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;

import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageType;
import pdu.MessageImpl.AckMessage;

public class AckMessageTest {

	@Test
	public void test() throws Exception {
		String ver= null;
		AckMessage am = new AckMessage(null);
		assertNotNull(am);
		System.out.println(am.toString());
		
		JSONObject jObject = new JSONObject(am.toString());
	    JSONObject header = jObject.getJSONObject("_header");
	    String messageType = header.getString("_first");
		
	//	Message m = MessageFactory.createMessage(am.toString());
	    MessageType mt = MessageType.getMessageTypeFromString(messageType);
	       
	       Gson gson = new Gson();
	    Message m = gson.fromJson(am.toString(), AckMessage.class);
		System.out.println(m.toString());
		
		
		
		
		
	}

}
