package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureTraceEntity;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.ErrorLog;

/**
 * FailureTrace service EJB interface.
 */
@Local
public interface FailureTraceData {

	public void addFailureTrace(FailureTraceEntity failureTrace);

	public void addFailureTraces(Collection <FailureTraceEntity> failureTraces);

	public Collection <String> getImsiOfFailureByTimePeriod(Date startTime, Date endTime);

	public Collection <EventCauseEntity> getEventCauseCombinations(String imsi);

	public Collection <Integer> getCauseCodesForImsi(String imsi);

	public Collection <String> getGivenImsiOfFailureWithinTimePeriod(Date startTime, Date endTime, String Imsi);

	public Collection <String> getCountFailsForModelWithinTimePeriod(String model, Date startTime, Date endTime);

	public Collection <String> getImsiOfFailureTraceByFailureClass(Integer failureClass);

	public Collection <String> getEventCauseCombinationsForModel(String model);

	public Collection <FailureTraceEntity> getAllFailureTraces();

	public Collection <String> getTop10MarketOperatorCellIdCombinations(Date dateOne, Date dateTwo);

	public Collection <String> givenImsiAndTimePeriodReturnNumberOfFailures(String Imsi, Date startTime, Date endTime);

	public Collection <String> topTenIMSIsWithFailures(Date startTime, Date endTime);

	public Long getTotalNumberOfEntries();

	public Collection <String> getAllIMSIs();

	public Collection <String> getAllModels();

	public Collection <FailureClassEntity> getAllFailureClasses();

	public Collection <ErrorLog> getErrorLogByImportDate(String importDate);

}
