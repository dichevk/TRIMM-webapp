package nl.utwente.trimm.group42.junittests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.extras.EncryptionPass;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;

public class AuthFuncTest {

	private static final String CORRECT_NAME = "CvdB";
	private static final String WRONG_NAME = "JOHN";
	private  byte[] salt_CvdB;
	private static final byte[] GENERATED_SALT=EncryptionPassStronger.generateSalt();
	private static final byte[] SALT_INCORRECT=AuthDao.getSalt(WRONG_NAME);
	private  byte[] correct_pass;
	private static final String PLAIN_PASS = "firstrunner18";
	private  byte[] incorrect_pass;
	private static final String INCORRECT_PASS2="e11d96446ac971d1b88071d1281d078e3e6386a21aef3d0674368c528dd2445cb833341f9b8bc4febc011bc0ea5628ead57ef98b51a595c1838d35ae237042bd";
	private static final String CORRECT_PASS2="db6c4ce678348be04db5fd8386945efd1bafd2ba4579f8413c5a8c7aad215bd96aae69585c592dfd98a02d54934cac4957af3f8cd257021b5910ababa3d16632";
	private static final String INCORRECT_PLAINPASS = "something";
	private static String token;
	private static String newName;
	 private static Random rand = new Random(); 
	@Before
	public void setUp() {
		salt_CvdB=AuthDao.getSalt("CvdB");
		correct_pass=EncryptionPassStronger.HashPassStr("firstrunner18", salt_CvdB);
		incorrect_pass=EncryptionPassStronger.HashPassStr("kfkdkfkdk", salt_CvdB);
	}
	@Test
	public void testDao() {
		String[] split=AuthDao.login(CORRECT_NAME, correct_pass).split(";");
		String answer=split[0];//success or fail
		token=split[1];
		assertEquals("success", answer);
		split=AuthDao.login(CORRECT_NAME, incorrect_pass).split(";");
		answer=split[0];
		//token=split[1];
		assertEquals("fail",answer);
		split=AuthDao.login(WRONG_NAME, correct_pass).split(";");
		answer=split[0];
		//token=split[1];
		assertEquals("fail",answer);
		split=AuthDao.register(CORRECT_NAME, correct_pass,salt_CvdB).split(";");
		answer=split[0];
		//token=split[1];
		assertEquals("fail",answer);
		token=SessionTokenDao.gettokenByusername(CORRECT_NAME);
		SessionTokenDao.deleteToken(token);
		newName=WRONG_NAME+rand.nextInt(1000);
		assertEquals("success",AuthDao.register(newName, incorrect_pass,GENERATED_SALT));
		//assertEquals("fail", AuthDao.register(WRONG_NAME, INCORRECT_PASS));
	}
	@Test
	public void testEncryption() {
		assertEquals(CORRECT_PASS2,EncryptionPass.hashWithSalt(PLAIN_PASS));
		assertFalse(INCORRECT_PASS2.equals(EncryptionPass.hashWithSalt(PLAIN_PASS)));
		assertFalse(CORRECT_PASS2.equals(EncryptionPass.hashWithSalt(INCORRECT_PLAINPASS)));
	}

}
