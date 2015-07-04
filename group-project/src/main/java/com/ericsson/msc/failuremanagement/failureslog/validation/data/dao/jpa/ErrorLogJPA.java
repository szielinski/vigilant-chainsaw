package com.ericsson.msc.failuremanagement.failureslog.validation.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.validation.data.ErrorLog;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.dao.ErrorLogDAO;

public class ErrorLogJPA implements ErrorLogDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <ErrorLog> getAllErrorLogs() {
		return em.createNamedQuery("findAllErrorLogs").getResultList();
	}

	@Override
	public void insertErrorLog(ErrorLog errorLog) {
		em.persist(errorLog);
	}

	@Override
	public void batchInsertErrorLog(Collection <ErrorLog> errorLogList) {
		for(ErrorLog errorLog : errorLogList)
			em.persist(errorLog);
	}

	@Override
	public void deleteErrorLogs() {
		em.createNamedQuery("deleteAllErrorLogs").getResultList();
	}

	@Override
	public Collection <ErrorLog> getErrorLogByImportDate(String importDate) {
		return em.createNamedQuery("getErrorsLogsByGenerationDate").setParameter("generationDate", importDate).getResultList();
	}
}
