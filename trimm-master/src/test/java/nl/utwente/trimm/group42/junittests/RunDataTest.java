package nl.utwente.trimm.group42.junittests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.RunnerDataDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;
import nl.utwente.trimm.group42.models.Run;

public class RunDataTest {
	private static final String USERNAME="CvdB";
	private static final int RUN_1=1;
	private static final int RUN_2=2;
	private static final int RUN_3=3;
	private static final int RUN_8=8;
	private static String token;
	private static Run run1;
	private static Run run8;
	private static int runs;
	

	@BeforeClass
	public static void setUp() throws Exception {
		//RunnerDataDao.stepData(USERNAME);
		token=SessionTokenDao.gettokenByusername(USERNAME);
		SessionTokenDao.deleteToken(token);
		byte[] salt = AuthDao.getSalt(USERNAME);
		System.out.println("pass");
		byte[] hashedpass = EncryptionPassStronger.HashPassStr("firstrunner18", salt);
		String answer = AuthDao.login(USERNAME, hashedpass);
		String[] split= answer.split(";");
		token=split[1];
		run1=RunnerDataDao.getRun(token, RUN_1);
		run8=RunnerDataDao.getRun(token, RUN_8);
		runs=RunnerDataDao.getCount(token);
		
	}

	@Test
	public void testRunData() {
		assertEquals(8,runs);
		assertEquals(562,run8.getTotalSteps());
		assertEquals(3116,run1.getTotalSteps());
		assertEquals(86,run1.getAveragePace());
		assertEquals(36,run1.getDuration());
		
	}
	@Test
	public void testStepData() {
		assertEquals(0,run1.getStepData().get(9).getSurface());
		assertEquals(53.8457,run1.getStepData().get(9).getAxtibacc_right(),0.05);
		assertEquals(103.1805097862983,run1.getStepData().get(9).getTibimpact_right(),0.0005);
		assertEquals(32.9632,run1.getStepData().get(9).getAxsacacc_right(),0.005);
		assertEquals(35.210465633325555,run1.getStepData().get(9).getSacimpact_right(),0.00005);
		assertEquals(-56.9147,run1.getStepData().get(9).getBrakingforce_right(),0.005);
		assertEquals(-1.34722,run1.getStepData().get(9).getPushoffpower_right(),0.005);
		assertEquals("2018-11-21 21:00:26",run1.getStepData().get(9).getIc_right());
		assertEquals("2018-11-21 21:00:27",run1.getStepData().get(9).getTo_right());
		assertEquals(37.20101301361588,run1.getStepData().get(9).getVll_left(),0.00005);
		

	}
	@Test
	public void testBodyPack() {
		assertEquals(7.88,run1.getMeta().getDistance(),0.05);
		assertEquals("00:36:08",run1.getMeta().getTime());
		assertEquals("2x zelfde ronde. Pittig tempo",run1.getMeta().getDescription());
		assertEquals("21/11/2018",run1.getMeta().getDate());	
	}
	@AfterClass
    public static void cleanDatabase() {
		SessionTokenDao.deleteToken(token);
    }    

}
