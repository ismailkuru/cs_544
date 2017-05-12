package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.RequestReceivedMessage;

public class RequestReceivedMessageTest {

	@Test
	public void test() {
	
		Boolean b = false;
		RequestReceivedMessage rrm = new RequestReceivedMessage(b);
		assertNotNull(rrm);
		System.out.println(rrm.toString());
		
	}

}
