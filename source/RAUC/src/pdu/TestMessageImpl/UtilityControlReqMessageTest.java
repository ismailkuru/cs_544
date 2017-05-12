package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.UtilityControlReqMessage;

public class UtilityControlReqMessageTest {

	@Test
	public void test() {
		UtilityControlReqMessage umcr = new UtilityControlReqMessage("1", "2", "3", "4");
		assertNotNull(umcr);
		System.out.println(umcr.toString());
		
	}

}
