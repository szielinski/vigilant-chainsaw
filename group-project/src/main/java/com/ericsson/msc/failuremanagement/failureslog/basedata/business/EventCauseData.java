package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;

/**
 * EventCause service EJB interface.
 */
@Local
public interface EventCauseData {

	public Collection <EventCauseEntity> getCauseCode();

	public void addEventCauses(Collection <EventCauseEntity> eventCauses);

	public void addEventCause(EventCauseEntity testEventCause);
}
