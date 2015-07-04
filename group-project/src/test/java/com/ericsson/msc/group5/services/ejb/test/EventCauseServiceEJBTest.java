package com.ericsson.msc.group5.services.ejb.test;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.basedata.business.EventCauseData;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;

@RunWith(Arquillian.class)
public class EventCauseServiceEJBTest {

	@EJB
	private EventCauseData service;

	@Test
	public void addEventCause() {
		EventCauseEntityCK ckOne = new EventCauseEntityCK(1, 1);
		EventCauseEntityCK ckTwo = new EventCauseEntityCK(2, 2);
		EventCauseEntityCK ckThree = new EventCauseEntityCK(3, 3);
		EventCauseEntity [] eventCauseArray = {new EventCauseEntity(ckOne, "first"), new EventCauseEntity(ckTwo, "second"), new EventCauseEntity(ckThree, "third")};

		Collection <EventCauseEntity> eventCauses = new ArrayList <>();
		for (EventCauseEntity e : eventCauseArray) {
			eventCauses.add(e);
		}

		service.addEventCauses(eventCauses);

		Collection <EventCauseEntity> retrievedEventCauses = service.getCauseCode();

		for (EventCauseEntity e : retrievedEventCauses) {
			assertTrue("An object failed to be retrieved", eventCauses.contains(e));
			assertTrue(e.equals(e));
			assertTrue(e.hashCode() == e.hashCode());
		}

	}

}
