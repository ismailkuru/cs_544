package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.QueryResultMessage;

public class QueryResultMessageTest {

	@Test
	public void test() {
		QueryResultMessage qrm = new QueryResultMessage("This is a result");
		assertNotNull(qrm);
		System.out.println(qrm.toString());

	}

}
