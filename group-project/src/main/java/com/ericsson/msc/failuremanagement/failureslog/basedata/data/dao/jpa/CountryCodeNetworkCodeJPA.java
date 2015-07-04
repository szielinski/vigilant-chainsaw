package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.Country;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCode;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryCodeNetworkCodeDAO;

public class CountryCodeNetworkCodeJPA implements CountryCodeNetworkCodeDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <CountryCodeNetworkCode> getAllCountryCodeNetworkCodes() {
		return em.createNamedQuery("findAllCountryCodeNetworkCodes").getResultList();
	}

	@Override
	public CountryCodeNetworkCode getCountryCodeNetworkCode(int networkCode, int countryCode) {
		Country country = new Country();
		country.setCountryCode(countryCode);
		return em.find(CountryCodeNetworkCode.class, new CountryCodeNetworkCodeCK(country, networkCode));
	}

	@Override
	public void insertCountryCodeNetworkCode(CountryCodeNetworkCode countryCodeNetworkCode) {
		Country country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
		if (em.find(Country.class, country.getCountryCode()) == null)
			em.persist(country);
		em.persist(countryCodeNetworkCode);
	}

	@Override
	public void batchInsertCountryCodeNetworkCode(Collection <CountryCodeNetworkCode> countryCodeNetworkCodeList) {
		for (CountryCodeNetworkCode countryCodeNetworkCode : countryCodeNetworkCodeList) {
			Country country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
			if (em.find(Country.class, country.getCountryCode()) == null)
				em.persist(country);
			em.persist(countryCodeNetworkCode);
		}
	}
}
