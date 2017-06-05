package com.ericsson.msc.failuremanagement.network.error.dao;

import com.ericsson.msc.failuremanagement.network.error.ErrorLog;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class ErrorLogJPA {
    @PersistenceContext
    private EntityManager em;

    public Collection<ErrorLog> getAllErrorLogs() {
        return em.createNamedQuery("findAllErrorLogs").getResultList();
    }

    public void insertErrorLog(ErrorLog errorLog) {
        em.persist(errorLog);
    }

    public void batchInsertErrorLog(Collection<ErrorLog> errorLogList) {
        for (ErrorLog errorLog : errorLogList)
            em.persist(errorLog);
    }

    public void deleteErrorLogs() {
        em.createNamedQuery("deleteAllErrorLogs").getResultList();
    }

    public Collection<ErrorLog> getErrorLogByImportDate(String importDate) {
        return em.createNamedQuery("getErrorsLogsByGenerationDate").setParameter("generationDate", importDate).getResultList();
    }
}
