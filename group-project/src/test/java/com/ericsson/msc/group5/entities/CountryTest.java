package com.ericsson.msc.group5.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CountryTest {

	@Deployment
	public static Archive <?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Country.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

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
		Country createdC = new Country();
		createdC.setCountry(INITIAL_COUNTRY);
		em.persist(createdC);
		int newId = createdC.getCountryCode();

		Country loadedC = em.find(Country.class, newId);
		assertEquals("Failed to insert", INITIAL_COUNTRY, loadedC.getCountry());

		loadedC.setCountry(UPDATED_COUNTRY);
		Country updatedC = em.find(Country.class, newId);

		assertEquals("Failed to update", UPDATED_COUNTRY, updatedC.getCountry());

		em.remove(updatedC);
		Country shouldBeNull = em.find(Country.class, newId);
		assertNull("Failed to delete", shouldBeNull);
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from com.ericsson.msc.group5.entities.Country").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
