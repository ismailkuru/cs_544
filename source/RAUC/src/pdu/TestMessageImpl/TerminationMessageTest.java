package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.TerminationMessage;

public class TerminationMessageTest {

	@Test
	public void test() {

		TerminationMessage tm = new TerminationMessage();
		assertNotNull(tm);
		System.out.println(tm.toString());
		
	}

}
