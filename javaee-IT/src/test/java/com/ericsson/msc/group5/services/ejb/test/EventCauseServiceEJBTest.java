package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.network.data.business.EventCauseDataBean;
import data.EventCauseEntity;
import data.EventCauseEntityCK;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class EventCauseServiceEJBTest {

    @EJB
    private EventCauseDataBean service;

    @Test
    public void addEventCause() {
        EventCauseEntityCK ckOne = new EventCauseEntityCK(1, 1);
        EventCauseEntityCK ckTwo = new EventCauseEntityCK(2, 2);
        EventCauseEntityCK ckThree = new EventCauseEntityCK(3, 3);
        EventCauseEntity[] eventCauseArray = {new EventCauseEntity(ckOne, "first"), new EventCauseEntity(ckTwo, "second"), new EventCauseEntity(ckThree, "third")};

        Collection<EventCauseEntity> eventCauses = new ArrayList<>();
        for (EventCauseEntity e : eventCauseArray) {
            eventCauses.add(e);
        }

        service.addEventCauses(eventCauses);

        Collection<EventCauseEntity> retrievedEventCauses = service.getCauseCode();

        for (EventCauseEntity e : retrievedEventCauses) {
            assertTrue("An object failed to be retrieved", eventCauses.contains(e));
            assertTrue(e.equals(e));
            assertTrue(e.hashCode() == e.hashCode());
        }

    }

}
