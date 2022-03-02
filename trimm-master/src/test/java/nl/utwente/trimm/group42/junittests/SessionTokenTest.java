package nl.utwente.trimm.group42.junittests;


import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;

public class SessionTokenTest {

	static String loginReply;
	static String username = "CvdB";
	String password = "firstrunner18";
	int run = 1;

	@Before
	public void setUp() {
		byte[] salt = AuthDao.getSalt(username);
		byte[] hashedpass = EncryptionPassStronger.HashPassStr(password, salt);
		loginReply = AuthDao.login(username, hashedpass);
		System.err.println(loginReply);
	}

	@Test
	public void testAssignSessionTokken() {
		assertTrue(loginReply.startsWith("success;"));
		assertTrue(loginReply.length() > 13);
	}

	@Test
	public void testgetUser() {
		assertEquals(SessionTokenDao.getUser(loginReply.substring(8)), username);
	}

	@Test
	public void testExpire() {
		SessionTokenDao.EXPIERY = 1;
		byte[] salt = AuthDao.getSalt(username);
		byte[] hashedpass = EncryptionPassStronger.HashPassStr(password, salt);
		loginReply = AuthDao.login(username, hashedpass);
		Date currentTime = new Date();
		Date expireTime = SessionTokenDao.addSecondsToDate(currentTime, SessionTokenDao.EXPIERY * 2);
		while (new Date().before(expireTime)) {
			// wait
		}
		assertEquals(SessionTokenDao.getUser(loginReply.substring(8)), null);
	}

	@Test
	public void testAddSecondsToDate() {
		Date time = new Date();
		int difference = 6;
		Date time2 = SessionTokenDao.addSecondsToDate(time, 6);
		assertEquals(time2.getTime() - time.getTime(), difference * 1000);
	}

}
