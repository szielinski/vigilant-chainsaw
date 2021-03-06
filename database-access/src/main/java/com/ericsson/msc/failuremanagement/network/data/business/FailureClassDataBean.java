package com.ericsson.msc.failuremanagement.network.data.business;

import com.ericsson.msc.failuremanagement.network.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.network.data.dao.FailureClassJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@Local
public class FailureClassDataBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private FailureClassJPA dao;

    public Collection<FailureClassEntity> getFailureClasses() {
        return dao.getAllFailureClasses();
    }

    public void addFailureClasses(Collection<FailureClassEntity> failureClasses) {
        dao.batchInsertFailureClasses(failureClasses);
    }

    public void addFailureClass(FailureClassEntity failureClass) {
        dao.insertFailureClass(failureClass);
    }
}
