package nl.utwente.trimm.group42.junittests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.RunnerDataDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.dao.UserDao;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;
import nl.utwente.trimm.group42.models.User;

public class AccountTest {
	private static final String USERNAME="CvdB";
	private static final String FIRSTNAME="Christian";
	private static final String LASTNAME="van den Berge";
	private static final double KM_RAN= 79.95;
	private static final int STEPS_TOTAL=35223;
	private static final int HEIGHT=195;
	private static final int WEIGHT=90;
	private static final String EMAIL="somemail@mail.com";
	private static User user;
	private static User userA=new User(USERNAME,FIRSTNAME,LASTNAME,EMAIL,WEIGHT,HEIGHT);
	private static final String CHANGED_FIRSTNAME="somename";
	private static final String CHANGED_LASTNAME="somelastname";
	private static final int CHANGED_HEIGHT=140;
	private static final int CHANGED_WEIGHT=60;
	private static String token;
	
	@Before
	public void setUp() {
		token=SessionTokenDao.gettokenByusername(USERNAME);
		SessionTokenDao.deleteToken(token);
		byte[] salt = AuthDao.getSalt(USERNAME);
		System.out.println("pass");
		byte[] hashedpass = EncryptionPassStronger.HashPassStr("firstrunner18", salt);
		String answer = AuthDao.login(USERNAME, hashedpass);
		String[] split= answer.split(";");
		token=split[1];
		UserDao.updateUser(userA);
		user = UserDao.getAcc(token);
		UserDao.addstepsnKM(user, token);
		
	}

	@Test
	public void testAccget() {
		assertEquals(USERNAME,user.getUsername());
		assertEquals(KM_RAN,user.getRankm(),0.2);
		assertEquals(STEPS_TOTAL,user.getsteps());
		assertEquals(HEIGHT,user.getheight());
		assertEquals(WEIGHT,user.getweight());
		assertEquals(EMAIL,user.getEmail());
		assertEquals(FIRSTNAME,user.getfirstname());
		assertEquals(LASTNAME,user.getlastname());
		
	}
	@Test
	public void testUpdateAcc() {
		user.setfirstname(CHANGED_FIRSTNAME);
		user.setlastname(CHANGED_LASTNAME);
		user.setweght(CHANGED_WEIGHT);
		user.setheight(CHANGED_HEIGHT);
		UserDao.updateUser(user);
		user=UserDao.getAcc(token);
		UserDao.addstepsnKM(user, token);
		assertEquals(USERNAME,user.getUsername());
		assertEquals(KM_RAN,user.getRankm(),0.2);
		assertEquals(STEPS_TOTAL,user.getsteps());
		assertEquals(CHANGED_HEIGHT,user.getheight());
		assertEquals(CHANGED_WEIGHT,user.getweight());
		assertEquals(EMAIL,user.getEmail());//the email cannot be changed in the database security issue otherwise
		
	}
	@Test
	public void testOther() {
		assertEquals(STEPS_TOTAL,UserDao.totalstepsUser(USERNAME));
	}
	@AfterClass
    public static void cleanDatabase() {
		SessionTokenDao.deleteToken(token);
    }    

}
