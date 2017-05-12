package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.UtilityStateQueryMessage;

public class UtilityStateQueryMessageTest {

	@Test
	public void test() {
	
		String[] params  = new String[1];
		params[0] = "1"; // car id
		UtilityStateQueryMessage usm = new UtilityStateQueryMessage(params);
		assertNotNull(usm);
		System.out.println(usm.toString());
		
	}

}
