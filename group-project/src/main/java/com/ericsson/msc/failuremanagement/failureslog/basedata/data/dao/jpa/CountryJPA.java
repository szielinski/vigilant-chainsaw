package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.Country;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryDAO;

@Local
@Stateless
public class CountryJPA implements CountryDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <Country> getAllCountries() {
		return em.createNamedQuery("findAllCountries").getResultList();
	}

	@Override
	public void insertCountry(Country country) {
		em.persist(country);
	}

}
