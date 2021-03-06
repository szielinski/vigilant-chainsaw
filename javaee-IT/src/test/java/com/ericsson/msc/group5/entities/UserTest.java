package com.ericsson.msc.group5.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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

import com.ericsson.msc.failuremanagement.user.data.data.UserEntity;

@RunWith(Arquillian.class)
public class UserTest {

	@Deployment
	public static Archive <?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(UserEntity.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	private static String INITIAL_USERNAME = "username";

	private static String INITIAL_PASSWORD = "password";
	private static String UPDATED_PASSWORD = "other";

	private static String INITIAL_ROLE = "customer";
	private static String UPDATED_ROLE = "administrator";

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		insertData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();

		UserEntity user = new UserEntity(INITIAL_USERNAME, INITIAL_PASSWORD, INITIAL_ROLE);
		em.persist(user);
		
		utx.commit();
		em.clear();
	}

	@Test
	public void basicCRUDTest() throws Exception {
		UserEntity loadedUser = em.find(UserEntity.class, INITIAL_USERNAME);
		assertEquals("Failed to insert", INITIAL_USERNAME, loadedUser.getUsername());
		assertEquals("Failed to update", INITIAL_PASSWORD, loadedUser.getPassword());
		assertEquals("Failed to update", INITIAL_ROLE, loadedUser.getRole());

		loadedUser.setPassword(UPDATED_PASSWORD);
		loadedUser.setRole(UPDATED_ROLE);
		em.merge(loadedUser);
		
		UserEntity updatedUser = em.find(UserEntity.class, INITIAL_USERNAME);
//		assertTrue("Failed to match", loadedUser.hashCode() == updatedUser.hashCode());
		assertTrue("Failed to match", loadedUser.equals(updatedUser));
		assertEquals("Failed to update", UPDATED_PASSWORD, updatedUser.getPassword());
		assertEquals("Failed to update", UPDATED_ROLE, updatedUser.getRole());

		em.remove(updatedUser);
		UserEntity shouldBeNull = em.find(UserEntity.class, INITIAL_USERNAME);
		assertNull("Failed to delete", shouldBeNull);
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from group5.entities.UserEquipment").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
