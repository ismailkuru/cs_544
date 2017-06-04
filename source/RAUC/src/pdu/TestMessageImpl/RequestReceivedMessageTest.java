package pdu.TestMessageImpl;

import org.junit.Test;
import pdu.MessageImpl.RequestReceivedMessage;

import static org.junit.Assert.assertNotNull;

public class RequestReceivedMessageTest {

	@Test
	public void test() {
	
		Boolean b = false;
		RequestReceivedMessage rrm = new RequestReceivedMessage();
		assertNotNull(rrm);
		System.out.println(rrm.toString());
		
	}

}
