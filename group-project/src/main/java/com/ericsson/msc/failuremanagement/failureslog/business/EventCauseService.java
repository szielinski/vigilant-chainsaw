package com.ericsson.msc.failuremanagement.failureslog.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.data.EventCause;

/**
 * EventCause service EJB interface.
 */
@Local
public interface EventCauseService {

	public Collection <EventCause> getCauseCode();

	public void addEventCauses(Collection <EventCause> eventCauses);

	public void addEventCause(EventCause testEventCause);
}
