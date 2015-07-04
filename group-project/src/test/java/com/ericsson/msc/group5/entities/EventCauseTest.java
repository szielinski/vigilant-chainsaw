package com.ericsson.msc.group5.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.basedata.business.EventCauseData;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;

@RunWith(Arquillian.class)
@Transactional
public class EventCauseTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private EventCauseData eventCauseService;

	private static String INITIAL_DESCRIPTION = "RRC CONN SETUP-UE BEARERS REJECTED DUE TO ARP ADM REJ AND LICENSES MISSING";
	private static String UPDATED_DESCRIPTION = "UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED ENB UE S1AP ID";


	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void basicCRUDTest() throws Exception {
		EventCauseEntityCK pk = new EventCauseEntityCK();
		pk.setCauseCode(1);
		pk.setEventId(1);
		EventCauseEntity createdEC = new EventCauseEntity(pk, INITIAL_DESCRIPTION);
		eventCauseService.addEventCause(createdEC);

		ArrayList <EventCauseEntity> eventCauses = new ArrayList <>();
		Collection <?> eventCausesCollection = eventCauseService.getCauseCode();

		for (Object e : eventCausesCollection) {
			eventCauses.add((EventCauseEntity) e);
		};
		EventCauseEntity loadedEC = null;
		for (EventCauseEntity f : eventCauses) {
			if (f.getCauseCodeEventIdCK().equals(pk)) {
				loadedEC = f;
				break;
			}
		}

		assertEquals("Failed to insert", INITIAL_DESCRIPTION, loadedEC.getDescription());

		loadedEC.setDescription(UPDATED_DESCRIPTION);
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void compositeKeyTest() {
		int oldCauseCode = 21;
		int oldEventId = 45;
		Integer newCauseCode = 5000;
		Integer newEventId = 241;
		EventCauseEntityCK ck = new EventCauseEntityCK(oldCauseCode, oldEventId);
		ck.setCauseCode(newCauseCode);
		ck.setEventId(newEventId);
		assertEquals("failed to set cause code", newCauseCode, ck.getCauseCode());
		assertEquals("failed to set event id", newEventId, ck.getEventId());
		EventCauseEntityCK ckCopy = new EventCauseEntityCK(newCauseCode, newEventId);
		assertEquals("the two objects should be equal since they have the same state", ck, ckCopy);
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testEquality(){
		EventCauseEntityCK pk = new EventCauseEntityCK(1, 1);
		EventCauseEntityCK other = new EventCauseEntityCK(1, 1);
		
		assertTrue(pk.equals(other));
		assertFalse(pk.equals(null));
		assertFalse(pk.equals(new Integer(0)));
		assertFalse(pk.equals(new EventCauseEntityCK(0, 1)));
		assertFalse(pk.equals(new EventCauseEntityCK(1, 0)));
		assertTrue(pk.hashCode() == (other.hashCode()));
		
		EventCauseEntity eventCause = new EventCauseEntity(pk, "description");
		EventCauseEntity otherEventCause = new EventCauseEntity(other, "description");
		
		assertTrue(eventCause.equals(otherEventCause));
		assertFalse(eventCause.equals(null));
		assertFalse(eventCause.equals(new Integer(0)));
		assertFalse(eventCause.equals(new EventCauseEntity(new EventCauseEntityCK(0, 1), "description")));
		assertFalse(eventCause.equals(new EventCauseEntity(new EventCauseEntityCK(1, 0), "description")));
		assertTrue(eventCause.hashCode() == (otherEventCause.hashCode()));
	}
}
