package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureTraceEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;

@Local
@Stateless
public class FailureTraceJPA {

    @PersistenceContext
    private EntityManager em;

    public Collection<EventCauseEntity> getEventCauseForImsi(String imsi) {
        return em.createNamedQuery("getEventCauseCombinations").setParameter("givenImsi", imsi).getResultList();
    }

    public Collection<String> getImsiOfFailureWithinTimePeriod(Date startTime, Date endTime) {
        return em.createNamedQuery("getImsiOfFailureByTimePeriod").setParameter("startTime", startTime).setParameter("endTime", endTime)
                .getResultList();
    }

    public Collection<String> getCountAndTotalDurationForGivenImsiWithinTimePeriod(Date startTime, Date endTime, String Imsi) {
        return em.createNamedQuery("getCountAndTotalDurationForGivenImsiWithinTimePeriod").setParameter("startTime", startTime).setParameter("endTime", endTime)
                .setParameter("Imsi", Imsi).getResultList();
    }

    public Collection<String> getCountOfFailuresForModelWithinTimePeriod(String model, Date startTime, Date endTime) {
        return em.createNamedQuery("getCountOfFailuresWithinTimePeriodForGivenModel").setParameter("model", model).setParameter("startTime", startTime)
                .setParameter("endTime", endTime).getResultList();
    }

    public Collection<String> getEventCauseCombinationsForModel(String model) {
        return em.createNamedQuery("getEventCauseCombinationsForModel").setParameter("model", model).getResultList();
    }

    public Collection<String> getTop10MarketOperatorCellIdCombinations(Date startTime, Date endTime) {
        return em.createNamedQuery("top10MarketOperatorCellIdCombinations").setParameter("startTime", startTime).setParameter("endTime", endTime)
                .setMaxResults(10).getResultList();
    }

    public Collection<String> getImsiOfFailureTraceByFailureClass(Integer failureClass) {
        return em.createNamedQuery("getImsiOfFailureTraceByFailureClass").setParameter("givenFailureClass", failureClass).getResultList();
    }

    public Long getTotalNumberOfEntries() {
        return (Long) em.createNamedQuery("getTotalNumberOfEntries").getSingleResult();
    }

    public Collection<FailureTraceEntity> getAllFailureTraces() {
        return em.createNamedQuery("getAllFailureTraces").getResultList();
    }

    public void insertFailureTrace(FailureTraceEntity failureTrace) {
        em.persist(failureTrace);
    }

    public void batchInsertFailureTrace(Collection<FailureTraceEntity> failureTraceList) {
        for (FailureTraceEntity failureTrace : failureTraceList) {
            em.persist(failureTrace);
        }
    }

    // For a given IMSI, count the number of failures it has had during a given
    // time period.
    public Collection<String> getCountOfFailuresForGivenImsiWithinTimePeriod(String Imsi, Date startTime, Date endTime) {
        return em.createNamedQuery("getCountOfFailuresForGivenImsiWithinTimePeriod").setParameter("Imsi", Imsi).setParameter("startTime", startTime)
                .setParameter("endTime", endTime).getResultList();
    }

    public Collection<String> getAllIMSIs() {
        return em.createNamedQuery("getAllIMSIs").getResultList();
    }

    public Collection<String> getAllModels() {
        return em.createNamedQuery("getAllModels").getResultList();
    }

    public Collection<Integer> getCauseCodesForImsi(String imsi) {
        return em.createNamedQuery("getCauseCodesForGivenImsi").setParameter("givenImsi", imsi).getResultList();
    }

    // Show the Top 10 IMSIs that had call failures during a time period
    public Collection<String> topTenIMSIsWithFailures(Date startTime, Date endTime) {
        return em.createNamedQuery("topTenIMSIsWithFailures").setParameter("startTime", startTime).setParameter("endTime", endTime).setMaxResults(10).getResultList();
    }

    public Collection<FailureClassEntity> getAllFailureClasses() {
        return em.createNamedQuery("getAllFailureClasses").getResultList();
    }
}
