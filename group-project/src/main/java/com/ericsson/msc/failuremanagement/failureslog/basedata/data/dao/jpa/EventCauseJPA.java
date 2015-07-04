package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.EventCauseDAO;

public class EventCauseJPA implements EventCauseDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <EventCauseEntity> getAllEventCauses() {
		return em.createNamedQuery("findAllEventCauses").getResultList();
	}

	@Override
	public EventCauseEntity getEventCause(int causeCode, int eventId) {
		return em.find(EventCauseEntity.class, new EventCauseEntityCK(causeCode, eventId));
	}

	@Override
	public void insertEventCause(EventCauseEntity eventCause) {
		em.persist(eventCause);
	}

	@Override
	public void batchInsertEventCause(Collection <EventCauseEntity> eventCauseList) {
		for(EventCauseEntity eventCause : eventCauseList)
			em.persist(eventCause);
	}
}
