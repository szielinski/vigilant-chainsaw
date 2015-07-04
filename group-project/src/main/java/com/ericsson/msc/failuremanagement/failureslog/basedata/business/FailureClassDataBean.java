package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureClassDAO;

@Stateless
@Local
public class FailureClassDataBean implements FailureClassData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private FailureClassDAO dao;

	@Override
	public Collection <FailureClassEntity> getFailureClasses() {
		return dao.getAllFailureClasses();
	}

	@Override
	public void addFailureClasses(Collection <FailureClassEntity> failureClasses) {
		dao.batchInsertFailureClasses(failureClasses);
	}

	@Override
	public void addFailureClass(FailureClassEntity failureClass) {
		dao.insertFailureClass(failureClass);
	}
}
