package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class FailureClassJPA {

    @PersistenceContext
    private EntityManager em;

    public Collection<FailureClassEntity> getAllFailureClasses() {
        return em.createNamedQuery("findAllFailureClasses").getResultList();
    }

    public FailureClassEntity getFailureClass(int failureClassId) {
        return em.find(FailureClassEntity.class, failureClassId);
    }

    public void insertFailureClass(FailureClassEntity failureClass) {
        em.persist(failureClass);
    }

    public void batchInsertFailureClasses(Collection<FailureClassEntity> failureClassList) {
        for (FailureClassEntity failureClass : failureClassList)
            em.persist(failureClass);
    }
}
