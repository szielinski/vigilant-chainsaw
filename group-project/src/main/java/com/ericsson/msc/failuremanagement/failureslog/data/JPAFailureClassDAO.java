package com.ericsson.msc.failuremanagement.failureslog.data;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPAFailureClassDAO implements FailureClassDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <FailureClass> getAllFailureClasses() {
		return em.createNamedQuery("findAllFailureClasses").getResultList();
	}

	@Override
	public FailureClass getFailureClass(int failureClassId) {
		return em.find(FailureClass.class, failureClassId);
	}

	@Override
	public void insertFailureClass(FailureClass failureClass) {
		em.persist(failureClass);
	}

	@Override
	public void batchInsertFailureClasses(Collection <FailureClass> failureClassList) {
		for(FailureClass failureClass : failureClassList)
			em.persist(failureClass);
	}
}
