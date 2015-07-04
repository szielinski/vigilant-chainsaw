package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCause;

/**
 * EventCause service EJB interface.
 */
@Local
public interface EventCauseData {

	public Collection <EventCause> getCauseCode();

	public void addEventCauses(Collection <EventCause> eventCauses);

	public void addEventCause(EventCause testEventCause);
}
