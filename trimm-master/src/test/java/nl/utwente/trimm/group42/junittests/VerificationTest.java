package nl.utwente.trimm.group42.junittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.UserDao;
import nl.utwente.trimm.group42.dao.VerificationDao;
import nl.utwente.trimm.group42.models.SendEmail;

public class VerificationTest {
	public static final String RANDOM_EMAIL="str@str.com";//it should not be into the database
	public static final String EXIST_EMAIL="somemail@mail.com";//existing email in the database

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerification() {
		assertTrue(UserDao.validEmail(EXIST_EMAIL));
		assertFalse(UserDao.validEmail(RANDOM_EMAIL));
		SendEmail send=new SendEmail();
		String token =send.generateSafeToken();
		VerificationDao.AddEmailnToken(RANDOM_EMAIL, token);
		assertTrue(VerificationDao.checkToken(token, RANDOM_EMAIL));
		String wrongtoken =send.generateSafeToken();
		assertFalse(VerificationDao.checkToken(wrongtoken, RANDOM_EMAIL));
		assertFalse(VerificationDao.checkToken(token, EXIST_EMAIL));
		
	}

}
