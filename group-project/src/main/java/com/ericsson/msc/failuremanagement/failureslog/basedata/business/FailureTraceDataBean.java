package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureTraceEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureTraceDAO;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.ErrorLog;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.dao.ErrorLogDAO;

@Stateless
@Local
public class FailureTraceDataBean implements FailureTraceData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private FailureTraceDAO dao;

	@Inject
	private ErrorLogDAO errorLogDAO;

	@Override
	public Collection <EventCauseEntity> getEventCauseCombinations(String imsi) {
		return dao.getEventCauseForImsi(imsi);
	}

	@Override
	public Collection <String> getImsiOfFailureTraceByFailureClass(Integer failureClass) {
		return dao.getImsiOfFailureTraceByFailureClass(failureClass);
	}

	@Override
	public Collection <String> getImsiOfFailureByTimePeriod(Date startTime, Date endTime) {
		return dao.getImsiOfFailureWithinTimePeriod(startTime, endTime);
	}

	@Override
	public Collection <String> getGivenImsiOfFailureWithinTimePeriod(Date startTime, Date endTime, String Imsi) {
		return dao.getCountAndTotalDurationForGivenImsiWithinTimePeriod(startTime, endTime, Imsi);
	}

	@Override
	public Collection <String> getCountFailsForModelWithinTimePeriod(String model, Date startTime, Date endTime) {
		return dao.getCountOfFailuresForModelWithinTimePeriod(model, startTime, endTime);
	}

	@Override
	public Collection <String> getEventCauseCombinationsForModel(String model) {
		return dao.getEventCauseCombinationsForModel(model);
	}

	public Collection <String> getTop10MarketOperatorCellIdCombinations(Date startTime, Date endTime) {
		return dao.getTop10MarketOperatorCellIdCombinations(startTime, endTime);
	}

	@Override
	public Collection <FailureTraceEntity> getAllFailureTraces() {
		return dao.getAllFailureTraces();
	}

	@Override
	public Long getTotalNumberOfEntries() {
		return dao.getTotalNumberOfEntries();
	}

	@Override
	public void addFailureTraces(Collection <FailureTraceEntity> failureTraces) {
		dao.batchInsertFailureTrace(failureTraces);
	}

	// For a given IMSI, count the number of failures it has had during a given
	// time period.
	@Override
	public Collection <String> givenImsiAndTimePeriodReturnNumberOfFailures(String Imsi, Date startTime, Date endTime) {
		return dao.getCountOfFailuresForGivenImsiWithinTimePeriod(Imsi, startTime, endTime);
	}

	@Override
	public Collection <String> getAllIMSIs() {
		return dao.getAllIMSIs();
	}

	@Override
	public Collection <String> getAllModels() {
		return dao.getAllModels();
	}

	@Override
	public Collection <Integer> getCauseCodesForImsi(String imsi) {
		return dao.getCauseCodesForImsi(imsi);
	}

	// Show the Top 10 IMSIs that had call failures during a time period
	@Override
	public Collection <String> topTenIMSIsWithFailures(Date startTime,
			Date endTime) {
		return dao.topTenIMSIsWithFailures(startTime, endTime);
	}

	@Override
	public Collection <FailureClassEntity> getAllFailureClasses() {
		return dao.getAllFailureClasses();
	}

	@Override
	public Collection <ErrorLog> getErrorLogByImportDate(String importDate) {
		return errorLogDAO.getErrorLogByImportDate(importDate);
	}

	@Override
	public void addFailureTrace(FailureTraceEntity failureTrace) {
		dao.insertFailureTrace(failureTrace);

	}
}
