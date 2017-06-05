package com.ericsson.msc.failuremanagement.network.data.dao;

import com.ericsson.msc.failuremanagement.network.data.CountryEntity;

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
