package com.ericsson.msc.failuremanagement.network.data.business;

import com.ericsson.msc.failuremanagement.network.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.network.data.dao.EventCauseJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@Local
public class EventCauseDataBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private EventCauseJPA dao;

    public Collection<EventCauseEntity> getCauseCode() {
        return dao.getAllEventCauses();
    }

    public void addEventCauses(Collection<EventCauseEntity> eventCauses) {
        dao.batchInsertEventCause(eventCauses);
    }

    public void addEventCause(EventCauseEntity eventCause) {
        dao.insertEventCause(eventCause);
    }
}
