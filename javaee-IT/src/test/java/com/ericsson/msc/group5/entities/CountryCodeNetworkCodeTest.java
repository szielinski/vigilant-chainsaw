package com.ericsson.msc.group5.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import data.CountryEntity;
import data.CountryCodeNetworkCodeEntity;
import data.CountryCodeNetworkCodeEntittyCK;

@RunWith(Arquillian.class)
public class CountryCodeNetworkCodeTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	private static String INITIAL_OPERATOR = "Oklahoma Western Telephone Company US";
	private static String UPDATED_OPERATOR = "Clearnet CA";

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void basicCRUDTest() throws Exception {
		CountryEntity c = new CountryEntity(0, "country");
		em.persist(c);
		CountryCodeNetworkCodeEntittyCK pk = new CountryCodeNetworkCodeEntittyCK(c, 1);
		CountryCodeNetworkCodeEntity createdCountryCodeNetworkCode = new CountryCodeNetworkCodeEntity(pk, INITIAL_OPERATOR);
		em.persist(createdCountryCodeNetworkCode);

		CountryCodeNetworkCodeEntity loadedCountryCodeNetworkCode = em.find(CountryCodeNetworkCodeEntity.class, pk);
		assertEquals("Failed to insert", INITIAL_OPERATOR, loadedCountryCodeNetworkCode.getOperator());

		loadedCountryCodeNetworkCode.setOperator(UPDATED_OPERATOR);
		CountryCodeNetworkCodeEntity updatedCountryCodeNetworkCode = em.find(CountryCodeNetworkCodeEntity.class, pk);

		assertEquals("Failed to update", UPDATED_OPERATOR, updatedCountryCodeNetworkCode.getOperator());

		em.remove(updatedCountryCodeNetworkCode);
		CountryCodeNetworkCodeEntity shouldBeNull = em.find(CountryCodeNetworkCodeEntity.class, pk);
		assertNull("Failed to delete", shouldBeNull);
	}

	@Test
	public void compositeKeyTest() {
		int oldCode = 21;
		int newCode = 5000;
		CountryEntity newCountry = new CountryEntity(newCode, "new country");

		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(new CountryEntity(oldCode, "country"), oldCode);
		ck.setCountry(newCountry);
		ck.setNetworkCode(newCode);
		assertEquals("failed to set network code", newCode, (int) ck.getNetworkCode());
		assertEquals("failed to set country", newCountry, ck.getCountry());
	}

	@Test
	public void testGeneratedMethods() {
		CountryEntity countryOne = new CountryEntity(0, "Test country");

		CountryCodeNetworkCodeEntittyCK countryCodeNetworkCodeCKOne = new CountryCodeNetworkCodeEntittyCK();
		countryCodeNetworkCodeCKOne.setCountry(countryOne);
		countryCodeNetworkCodeCKOne.setNetworkCode(0);

		CountryCodeNetworkCodeEntittyCK countryCodeNetworkCodeCKTwo = new CountryCodeNetworkCodeEntittyCK();
		countryCodeNetworkCodeCKTwo.setCountry(countryOne);
		countryCodeNetworkCodeCKTwo.setNetworkCode(1);

		CountryCodeNetworkCodeEntity countryCodeNetworkCodeOne = new CountryCodeNetworkCodeEntity();
		countryCodeNetworkCodeOne.setCountryCodeNetworkCode(countryCodeNetworkCodeCKOne);
		countryCodeNetworkCodeOne.setOperator(INITIAL_OPERATOR);

		CountryCodeNetworkCodeEntity countryCodeNetworkCodeTwo = new CountryCodeNetworkCodeEntity();
		countryCodeNetworkCodeTwo.setCountryCodeNetworkCode(countryCodeNetworkCodeCKTwo);
		countryCodeNetworkCodeTwo.setOperator(UPDATED_OPERATOR);

		// Check same objects equal same
		assertTrue(countryCodeNetworkCodeCKOne.equals(countryCodeNetworkCodeCKOne));
		assertFalse(countryCodeNetworkCodeCKOne.equals(countryCodeNetworkCodeCKTwo));
		// Check hash code works correctly
		assertFalse(countryCodeNetworkCodeCKOne.hashCode() == countryCodeNetworkCodeCKTwo.hashCode());

		// Check same objects equal same
		assertTrue(countryCodeNetworkCodeOne.equals(countryCodeNetworkCodeOne));
		assertFalse(countryCodeNetworkCodeOne.equals(countryCodeNetworkCodeTwo));
		// Check hash code works correctly
		assertFalse((countryCodeNetworkCodeOne.hashCode() == countryCodeNetworkCodeTwo.hashCode()));

		// Check .equals for null and different object type
		countryCodeNetworkCodeTwo = null;
		assertFalse(countryCodeNetworkCodeOne.equals(countryCodeNetworkCodeTwo));
		assertFalse(countryCodeNetworkCodeOne.equals(new String()));
		// Check .equals on empty required field
		countryCodeNetworkCodeTwo = new CountryCodeNetworkCodeEntity();
		assertFalse(countryCodeNetworkCodeOne.equals(countryCodeNetworkCodeTwo));
	}
	
	@Test
	public void testEquality(){
		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(new CountryEntity(1, "country"), 1);
		CountryCodeNetworkCodeEntittyCK otherCK = new CountryCodeNetworkCodeEntittyCK(new CountryEntity(1, "other country"), 1);
		
		CountryCodeNetworkCodeEntittyCK wrongCK1 = new CountryCodeNetworkCodeEntittyCK(new CountryEntity(2, "country"), 1);
		CountryCodeNetworkCodeEntittyCK wrongCK2 = new CountryCodeNetworkCodeEntittyCK(new CountryEntity(1, "other country"), 2);
		
		assertTrue(ck.equals(otherCK));
		assertFalse(ck.equals(null));
		assertFalse(ck.equals(new Integer(0)));
		assertFalse(ck.equals(new CountryCodeNetworkCodeEntittyCK(new CountryEntity(2, "country"), 1)));
		assertFalse(ck.equals(new CountryCodeNetworkCodeEntittyCK(new CountryEntity(1, "country"), 2)));
		assertTrue(ck.hashCode() == (otherCK.hashCode()));
		
		CountryCodeNetworkCodeEntity cc = new CountryCodeNetworkCodeEntity(ck, "description");
		CountryCodeNetworkCodeEntity otherCC = new CountryCodeNetworkCodeEntity(otherCK, "other description");
		
		assertTrue(cc.equals(otherCC));
		assertFalse(cc.equals(null));
		assertFalse(cc.equals(new Integer(0)));
		assertFalse(cc.equals(new CountryCodeNetworkCodeEntity(wrongCK1, "description")));
		assertFalse(cc.equals(new CountryCodeNetworkCodeEntity(wrongCK2, "description")));
		assertTrue(cc.hashCode() == (otherCC.hashCode()));
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from group5.entities.CountryCodeNetworkCode").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
