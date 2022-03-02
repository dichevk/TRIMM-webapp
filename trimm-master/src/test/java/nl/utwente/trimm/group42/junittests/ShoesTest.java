package nl.utwente.trimm.group42.junittests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.dao.ShoesDao;
import nl.utwente.trimm.group42.dao.UserDao;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;
import nl.utwente.trimm.group42.models.Shoes;

public class ShoesTest {
	
	private static final String SALOMON="Salomon";
	private static final String SENSE_RIDE="Sense Ride";
	private static final int HEEL=27;
	private static final int FOREFOOT=19;
	private static final int DROP=8;
	private static final int WEIGHT=275;
	Shoes shoes;
	@Before
	public void setUp() {
		shoes=ShoesDao.getShoeData(1);
	}
	@Test
	public void testShoe() {
		assertEquals(SENSE_RIDE,shoes.getName());
		assertEquals(SALOMON,shoes.getBrand());
		assertEquals(HEEL,shoes.getHeelmm());
		assertEquals(FOREFOOT,shoes.getForefootmm());
		assertEquals(DROP, shoes.getDropmm());
		assertEquals(WEIGHT, shoes.getWeightgr());
	}
	
   
}
