package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.AuthenAckMessage;

public class AuthenAckMessageTest {

	@Test
	public void test() {
		String ver= null;
		AuthenAckMessage am = new AuthenAckMessage(ver);
		assertNotNull(am);
		System.out.println(am.toString());
		
	}

}
