package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCause;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.EventCauseDAO;

@Stateless
@Local
public class EventCauseDataBean implements EventCauseData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private EventCauseDAO dao;

	@Override
	public Collection <EventCause> getCauseCode() {
		return dao.getAllEventCauses();
	}

	@Override
	public void addEventCauses(Collection <EventCause> eventCauses) {
		dao.batchInsertEventCause(eventCauses);
	}

	@Override
	public void addEventCause(EventCause eventCause) {
		dao.insertEventCause(eventCause);
	}

}
