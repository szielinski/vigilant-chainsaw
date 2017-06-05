package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class CountryJPA {
    @PersistenceContext
    private EntityManager em;

    public Collection<CountryEntity> getAllCountries() {
        return em.createNamedQuery("findAllCountries").getResultList();
    }

    public void insertCountry(CountryEntity country) {
        em.persist(country);
    }
}
