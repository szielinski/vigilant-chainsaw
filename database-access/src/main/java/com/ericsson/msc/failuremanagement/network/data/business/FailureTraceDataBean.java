package com.ericsson.msc.failuremanagement.network.data.business;

import com.ericsson.msc.failuremanagement.network.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.network.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.network.data.FailureTraceEntity;
import com.ericsson.msc.failuremanagement.network.data.dao.FailureTraceJPA;
import com.ericsson.msc.failuremanagement.network.error.ErrorLog;
import com.ericsson.msc.failuremanagement.network.error.dao.ErrorLogJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;

@Stateless
@Local
public class FailureTraceDataBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private FailureTraceJPA dao;
    @Inject
    private ErrorLogJPA errorLogDAO;

    public Collection<EventCauseEntity> getEventCauseCombinations(String imsi) {
        return dao.getEventCauseForImsi(imsi);
    }

    public Collection<String> getImsiOfFailureTraceByFailureClass(Integer failureClass) {
        return dao.getImsiOfFailureTraceByFailureClass(failureClass);
    }

    public Collection<String> getImsiOfFailureByTimePeriod(Date startTime, Date endTime) {
        return dao.getImsiOfFailureWithinTimePeriod(startTime, endTime);
    }

    public Collection<String> getGivenImsiOfFailureWithinTimePeriod(Date startTime, Date endTime, String Imsi) {
        return dao.getCountAndTotalDurationForGivenImsiWithinTimePeriod(startTime, endTime, Imsi);
    }

    public Collection<String> getCountFailsForModelWithinTimePeriod(String model, Date startTime, Date endTime) {
        return dao.getCountOfFailuresForModelWithinTimePeriod(model, startTime, endTime);
    }

    public Collection<String> getEventCauseCombinationsForModel(String model) {
        return dao.getEventCauseCombinationsForModel(model);
    }

    public Collection<String> getTop10MarketOperatorCellIdCombinations(Date startTime, Date endTime) {
        return dao.getTop10MarketOperatorCellIdCombinations(startTime, endTime);
    }

    public Collection<FailureTraceEntity> getAllFailureTraces() {
        return dao.getAllFailureTraces();
    }

    public Long getTotalNumberOfEntries() {
        return dao.getTotalNumberOfEntries();
    }

    public void addFailureTraces(Collection<FailureTraceEntity> failureTraces) {
        dao.batchInsertFailureTrace(failureTraces);
    }

    // For a given IMSI, count the number of failures it has had during a given
    // time period.
    public Collection<String> givenImsiAndTimePeriodReturnNumberOfFailures(String Imsi, Date startTime, Date endTime) {
        return dao.getCountOfFailuresForGivenImsiWithinTimePeriod(Imsi, startTime, endTime);
    }

    public Collection<String> getAllIMSIs() {
        return dao.getAllIMSIs();
    }

    public Collection<String> getAllModels() {
        return dao.getAllModels();
    }

    public Collection<Integer> getCauseCodesForImsi(String imsi) {
        return dao.getCauseCodesForImsi(imsi);
    }

    // Show the Top 10 IMSIs that had call failures during a time period
    public Collection<String> topTenIMSIsWithFailures(Date startTime,
                                                      Date endTime) {
        return dao.topTenIMSIsWithFailures(startTime, endTime);
    }

    public Collection<FailureClassEntity> getAllFailureClasses() {
        return dao.getAllFailureClasses();
    }

    public Collection<ErrorLog> getErrorLogByImportDate(String importDate) {
        return errorLogDAO.getErrorLogByImportDate(importDate);
    }

    public void addFailureTrace(FailureTraceEntity failureTrace) {
        dao.insertFailureTrace(failureTrace);
    }
}
