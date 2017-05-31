package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import pdu.Message;
import pdu.MessageFactory;
import pdu.MessageImpl.UserAuthenMessage;

public class UserAuthenMessageTest {

	@Test
	public void test() throws Exception {

		UserAuthenMessage um = new UserAuthenMessage("ismail", "1234");
		List<byte[]> bl = um.serialize();
		byte[][] bb = um.crunchToBytes(bl);
		UserAuthenMessage m = null;
		
		
			 m  = (UserAuthenMessage) MessageFactory.createMessage(bb);
			 assertNotNull(m);
		
		
		assertNotNull(um);
		System.out.println(m.toString());
	}

}
