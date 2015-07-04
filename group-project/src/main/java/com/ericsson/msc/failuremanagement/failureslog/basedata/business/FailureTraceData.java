package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCause;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClass;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureTrace;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.ErrorLog;

/**
 * FailureTrace service EJB interface.
 */
@Local
public interface FailureTraceData {

	public void addFailureTrace(FailureTrace failureTrace);

	public void addFailureTraces(Collection <FailureTrace> failureTraces);

	public Collection <String> getImsiOfFailureByTimePeriod(Date startTime, Date endTime);

	public Collection <EventCause> getEventCauseCombinations(String imsi);

	public Collection <Integer> getCauseCodesForImsi(String imsi);

	public Collection <String> getGivenImsiOfFailureWithinTimePeriod(Date startTime, Date endTime, String Imsi);

	public Collection <String> getCountFailsForModelWithinTimePeriod(String model, Date startTime, Date endTime);

	public Collection <String> getImsiOfFailureTraceByFailureClass(Integer failureClass);

	public Collection <String> getEventCauseCombinationsForModel(String model);

	public Collection <FailureTrace> getAllFailureTraces();

	public Collection <String> getTop10MarketOperatorCellIdCombinations(Date dateOne, Date dateTwo);

	public Collection <String> givenImsiAndTimePeriodReturnNumberOfFailures(String Imsi, Date startTime, Date endTime);

	public Collection <String> topTenIMSIsWithFailures(Date startTime, Date endTime);

	public Long getTotalNumberOfEntries();

	public Collection <String> getAllIMSIs();

	public Collection <String> getAllModels();

	public Collection <FailureClass> getAllFailureClasses();

	public Collection <ErrorLog> getErrorLogByImportDate(String importDate);

}
