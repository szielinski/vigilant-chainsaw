package com.ericsson.msc.failuremanagement.failureslog.data;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPAEventCauseDAO implements EventCauseDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <EventCause> getAllEventCauses() {
		return em.createNamedQuery("findAllEventCauses").getResultList();
	}

	@Override
	public EventCause getEventCause(int causeCode, int eventId) {
		return em.find(EventCause.class, new EventCauseCK(causeCode, eventId));
	}

	@Override
	public void insertEventCause(EventCause eventCause) {
		em.persist(eventCause);
	}

	@Override
	public void batchInsertEventCause(Collection <EventCause> eventCauseList) {
		for(EventCause eventCause : eventCauseList)
			em.persist(eventCause);
	}
}
