package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pdu.MessageImpl.UtilityStateQueryMessage;

public class UtilityStateQueryMessageTest {

	@Test
	public void test() {
	
		String[] params  = new String[1];
		params[0] = "1"; // car id
		UtilityStateQueryMessage usm = new UtilityStateQueryMessage(params);
		assertNotNull(usm);
		//System.out.println(usm.toString());
		
		
		//JsonParser jp = new JsonParser();
	//	JsonElement element = jp.parse(usm.toString());
		//UtilityStateQueryMessage um = gson.fromJson(usm.toString(), UtilityStateQueryMessage.class);
		
		Gson gson = new Gson();
		String json = gson.toJson(usm);
		System.out.println(json);
		
		UtilityStateQueryMessage as = gson.fromJson(json, UtilityStateQueryMessage.class);
		System.out.println(as.toString());
		//System.out.println(element.toString());
		
	}

}
