package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.EventCauseDAO;

@Stateless
@Local
public class EventCauseDataBean implements EventCauseData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private EventCauseDAO dao;

	@Override
	public Collection <EventCauseEntity> getCauseCode() {
		return dao.getAllEventCauses();
	}

	@Override
	public void addEventCauses(Collection <EventCauseEntity> eventCauses) {
		dao.batchInsertEventCause(eventCauses);
	}

	@Override
	public void addEventCause(EventCauseEntity eventCause) {
		dao.insertEventCause(eventCause);
	}

}
