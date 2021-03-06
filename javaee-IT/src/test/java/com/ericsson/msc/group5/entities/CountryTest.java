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

@RunWith(Arquillian.class)
public class CountryTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	private static String INITIAL_COUNTRY = "United States of America";
	private static String UPDATED_COUNTRY = "Guadeloupe-France";

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
		int id = 213;
		int newId = 2130;
		CountryEntity createdC = new CountryEntity(id, INITIAL_COUNTRY);
		em.persist(createdC);

		commitTransaction();
		em.clear();
		CountryEntity loadedC = em.find(CountryEntity.class, id);
		assertEquals("Failed to insert", INITIAL_COUNTRY, loadedC.getCountry());

		loadedC.setCountry(UPDATED_COUNTRY);
		loadedC.setCountryCode(newId);
		CountryEntity updatedC = em.find(CountryEntity.class, id);
		assertEquals("Failed to update", UPDATED_COUNTRY, updatedC.getCountry());
		assertEquals("Failed to update", newId, (int) updatedC.getCountryCode());

		em.remove(updatedC);
		CountryEntity shouldBeNull = em.find(CountryEntity.class, id);
		assertNull("Failed to delete", shouldBeNull);
	}

	@Test
	public void testGenerateMethods() {
		int oldCode = 21;
		int newCode = 5000;
		CountryEntity countryOne = new CountryEntity(oldCode, "old country");
		CountryEntity countryTwo = new CountryEntity(newCode, "new country");

		// Hashcode and .equals test
		assertTrue(countryOne.equals(countryOne));
		assertTrue(countryOne.hashCode() == countryOne.hashCode());
		assertTrue( !countryOne.equals(countryTwo));
		assertTrue( !(countryOne.hashCode() == countryTwo.hashCode()));

		// Getters and setters test
		countryOne.setCountry("new country");
		countryOne.setCountryCode(newCode);
		assertTrue(countryOne.getCountry().equals("new country"));
		assertTrue(countryOne.getCountryCode() == newCode);
		countryOne = null;
		assertFalse(countryTwo.equals((countryOne)));
		assertFalse(countryTwo.equals(new String()));
		countryOne = new CountryEntity();
		countryOne.setCountry("new country");
		assertFalse(countryOne.equals(countryTwo));
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from group5.entities.Country").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
