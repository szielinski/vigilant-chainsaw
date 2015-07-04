package com.ericsson.msc.failuremanagement.failureslog.data;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Local
@Stateless
public class JPACountryDAO implements CountryDAO {

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
