package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureClassDAO;

public class FailureClassJPA implements FailureClassDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <FailureClassEntity> getAllFailureClasses() {
		return em.createNamedQuery("findAllFailureClasses").getResultList();
	}

	@Override
	public FailureClassEntity getFailureClass(int failureClassId) {
		return em.find(FailureClassEntity.class, failureClassId);
	}

	@Override
	public void insertFailureClass(FailureClassEntity failureClass) {
		em.persist(failureClass);
	}

	@Override
	public void batchInsertFailureClasses(Collection <FailureClassEntity> failureClassList) {
		for(FailureClassEntity failureClass : failureClassList)
			em.persist(failureClass);
	}
}
