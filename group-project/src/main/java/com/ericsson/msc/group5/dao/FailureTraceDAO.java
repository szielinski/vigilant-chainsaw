package com.ericsson.msc.group5.dao;

import java.util.Collection;
import java.util.Date;
import com.ericsson.msc.group5.entities.EventCause;
import com.ericsson.msc.group5.entities.FailureTrace;

/**
 * A Data Access Object interface for the FailureTrace entity. Defines common DAO methods.
 */
public interface FailureTraceDAO {

	public Collection <String> getImsiOfFailureWithinTimePeriod(Date startTime, Date endTime);

	public Collection <String> getGivenImsiOfFailureWithinTimePeriod(Date startTime, Date endTime, String Imsi);

	public Long getTotalNumberOfEntries();

	public Collection <EventCause> getEventCauseForImsi(String imsi);

	/**
	 * Retrieve all FailureTrace objects present in the data store.
	 * 
	 * @return a Collection of FailureTrace objects; empty collection if no FailureTrace objects are present in the data store.
	 */
	public Collection <FailureTrace> getAllFailureTraces();

	/**
	 * Insert a new FailureTrace object into the data store.
	 * 
	 * @param failureTrace
	 *            A new FailureTrace object.
	 */
	public void insertFailureTrace(FailureTrace failureTrace);

	/**
	 * Batch insert a Collection of FailureTrace objects into the data store. Optimized for handling large volumes of data.
	 * 
	 * @param failureTraceList
	 *            A collection of new FailureTrace objects.
	 */
	public void batchInsertFailureTrace(Collection <FailureTrace> failureTraceList);
}
