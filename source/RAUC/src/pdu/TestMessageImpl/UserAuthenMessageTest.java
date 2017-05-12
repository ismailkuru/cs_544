package pdu.TestMessageImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import pdu.MessageImpl.UserAuthenMessage;

public class UserAuthenMessageTest {

	@Test
	public void test() {

		UserAuthenMessage um = new UserAuthenMessage("ismail", "1234");
		assertNotNull(um);
		System.out.println(um.toString());
	}

}
