package com.ericsson.msc.group5.dao.jpa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntittyCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryCodeNetworkCodeDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryDAO;

@RunWith(Arquillian.class)
public class JPACountryCodeNetworkCodeDAOTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private CountryCodeNetworkCodeDAO countryDAO;

	@Inject
	private UserTransaction utx;
	
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
	public void testInsertion(){	
		CountryEntity country = new CountryEntity(1, "country");
		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(country, 1);
		CountryCodeNetworkCodeEntity ccncck = new CountryCodeNetworkCodeEntity(ck, "one");
		countryDAO.insertCountryCodeNetworkCode(ccncck);
		assertNotNull(countryDAO.getAllCountryCodeNetworkCodes());
		assertTrue(countryDAO.getAllCountryCodeNetworkCodes().size() == 1);
		
	}
	
	@Test
	public void testGetAllCountryCodeNetworkCodes(){
		CountryEntity country = new CountryEntity(1, "country");
		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(country, 1);
		CountryCodeNetworkCodeEntity ccncck = new CountryCodeNetworkCodeEntity(ck, "one");

		CountryEntity countryTwo = new CountryEntity(2, "countryTwo");
		CountryCodeNetworkCodeEntittyCK ckTwo = new CountryCodeNetworkCodeEntittyCK(countryTwo, 2);
		CountryCodeNetworkCodeEntity ccncckTwo = new CountryCodeNetworkCodeEntity(ckTwo, "two");

		countryDAO.insertCountryCodeNetworkCode(ccncck);
		countryDAO.insertCountryCodeNetworkCode(ccncckTwo);
		
		assertTrue(countryDAO.getAllCountryCodeNetworkCodes().contains(ccncck));
		assertTrue(countryDAO.getAllCountryCodeNetworkCodes().contains(ccncckTwo));
	}
	
	@Test 
	public void testGetCountryCodeNetworkCode(){
		CountryEntity country = new CountryEntity(1, "country");
		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(country, 1);
		CountryCodeNetworkCodeEntity ccncck = new CountryCodeNetworkCodeEntity(ck, "one");
		countryDAO.insertCountryCodeNetworkCode(ccncck);
		assertNotNull(countryDAO.getCountryCodeNetworkCode(1, 1));
		assertTrue(countryDAO.getAllCountryCodeNetworkCodes().size() == 1);
	}
	
	@Test
	public void testBatchInsertCountryCodeNetworkCode(){
		CountryEntity country = new CountryEntity(1, "country");
		CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(country, 1);
		CountryCodeNetworkCodeEntity ccncck = new CountryCodeNetworkCodeEntity(ck, "one");

		CountryEntity countryTwo = new CountryEntity(2, "countryTwo");
		CountryCodeNetworkCodeEntittyCK ckTwo = new CountryCodeNetworkCodeEntittyCK(countryTwo, 2);
		CountryCodeNetworkCodeEntity ccncckTwo = new CountryCodeNetworkCodeEntity(ckTwo, "two");

		CountryEntity countryThree = new CountryEntity(3, "countryThree");
		CountryCodeNetworkCodeEntittyCK ckThree = new CountryCodeNetworkCodeEntittyCK(countryThree, 3);
		CountryCodeNetworkCodeEntity ccncckThree = new CountryCodeNetworkCodeEntity(ckThree, "Three");

		Collection <CountryCodeNetworkCodeEntity> ccncArray = new ArrayList<>();
		ccncArray.add(ccncck);
		ccncArray.add(ccncckTwo);
		ccncArray.add(ccncckThree);

		countryDAO.batchInsertCountryCodeNetworkCode(ccncArray);
		assertNotNull(countryDAO.getAllCountryCodeNetworkCodes());
		assertTrue(countryDAO.getAllCountryCodeNetworkCodes().size() == 3);
	}
	
	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from com.ericsson.msc.group5.entities.CountryCodeNetworkCode").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
	
}
