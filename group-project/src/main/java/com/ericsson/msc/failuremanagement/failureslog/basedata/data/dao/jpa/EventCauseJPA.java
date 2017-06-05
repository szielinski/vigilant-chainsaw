package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class EventCauseJPA {

    @PersistenceContext
    private EntityManager em;

    public Collection<EventCauseEntity> getAllEventCauses() {
        return em.createNamedQuery("findAllEventCauses").getResultList();
    }

    public EventCauseEntity getEventCause(int causeCode, int eventId) {
        return em.find(EventCauseEntity.class, new EventCauseEntityCK(causeCode, eventId));
    }

    public void insertEventCause(EventCauseEntity eventCause) {
        em.persist(eventCause);
    }

    public void batchInsertEventCause(Collection<EventCauseEntity> eventCauseList) {
        for (EventCauseEntity eventCause : eventCauseList)
            em.persist(eventCause);
    }
}
