package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryDAO;

@Local
@Stateless
public class CountryJPA implements CountryDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <CountryEntity> getAllCountries() {
		return em.createNamedQuery("findAllCountries").getResultList();
	}

	@Override
	public void insertCountry(CountryEntity country) {
		em.persist(country);
	}

}
