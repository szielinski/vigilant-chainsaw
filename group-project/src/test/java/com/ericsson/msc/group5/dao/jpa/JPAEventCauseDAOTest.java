package com.ericsson.msc.group5.dao.jpa;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.EventCauseDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureTraceDAO;

@RunWith(Arquillian.class)
public class JPAEventCauseDAOTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private FailureTraceDAO failureTraceDAO;

	@Inject
	private UserTransaction utx;
	
	@Inject
	private EventCauseDAO eventCauseDAO;
	
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
	public void insertEventCauseTest(){
		EventCauseEntityCK ck = new EventCauseEntityCK(123, 546);
		EventCauseEntity ec = new EventCauseEntity(ck, "desc");
		eventCauseDAO.insertEventCause(ec);
		assertNotNull(eventCauseDAO.getAllEventCauses());
		assertTrue(eventCauseDAO.getAllEventCauses().size() == 1);
	}
	
	@Test
	public void batchInsertEventCauseTest(){
		EventCauseEntityCK ck = new EventCauseEntityCK(123, 546);
		EventCauseEntity ec = new EventCauseEntity(ck, "desc");
		EventCauseEntityCK ckk = new EventCauseEntityCK(654, 321);;
		EventCauseEntity ecc = new EventCauseEntity(ckk, " desc");;
		
		ArrayList<EventCauseEntity> list = new ArrayList<EventCauseEntity>();
		list.add(ec);
		list.add(ecc);
		
		eventCauseDAO.batchInsertEventCause(list);
		assertNotNull(eventCauseDAO.getAllEventCauses());
		assertTrue(eventCauseDAO.getAllEventCauses().size() == 2);
	}
	
	@Test
	public void getEventCauseTest(){
		EventCauseEntityCK ck = new EventCauseEntityCK(123, 546);
		EventCauseEntity ec = new EventCauseEntity(ck, "desc");
		EventCauseEntityCK ckk = new EventCauseEntityCK(654, 321);;
		EventCauseEntity ecc = new EventCauseEntity(ckk, " not");;
		
		ArrayList<EventCauseEntity> list = new ArrayList<EventCauseEntity>();
		list.add(ec);
		list.add(ecc);
		
		eventCauseDAO.batchInsertEventCause(list);
		assertTrue(eventCauseDAO.getEventCause(123, 546).getDescription()=="desc");
		
	}
	
	@Test
	public void getAllEventCausesTest(){
		EventCauseEntityCK ck = new EventCauseEntityCK(123, 546);
		EventCauseEntity ec = new EventCauseEntity(ck, "desc");
		EventCauseEntityCK ckk = new EventCauseEntityCK(654, 321);;
		EventCauseEntity ecc = new EventCauseEntity(ckk, " not");;
		
		ArrayList<EventCauseEntity> list = new ArrayList<EventCauseEntity>();
		list.add(ec);
		list.add(ecc);
		
		eventCauseDAO.batchInsertEventCause(list);
		assertNotNull(eventCauseDAO.getAllEventCauses());
		assertTrue(eventCauseDAO.getAllEventCauses().size() == 2);
	}
	
	
	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from com.ericsson.msc.group5.entities.FailureTrace").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
	
}
