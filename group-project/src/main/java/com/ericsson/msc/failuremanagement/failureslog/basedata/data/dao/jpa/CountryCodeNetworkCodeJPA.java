package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntittyCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryCodeNetworkCodeDAO;

public class CountryCodeNetworkCodeJPA implements CountryCodeNetworkCodeDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <CountryCodeNetworkCodeEntity> getAllCountryCodeNetworkCodes() {
		return em.createNamedQuery("findAllCountryCodeNetworkCodes").getResultList();
	}

	@Override
	public CountryCodeNetworkCodeEntity getCountryCodeNetworkCode(int networkCode, int countryCode) {
		CountryEntity country = new CountryEntity();
		country.setCountryCode(countryCode);
		return em.find(CountryCodeNetworkCodeEntity.class, new CountryCodeNetworkCodeEntittyCK(country, networkCode));
	}

	@Override
	public void insertCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode) {
		CountryEntity country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
		if (em.find(CountryEntity.class, country.getCountryCode()) == null)
			em.persist(country);
		em.persist(countryCodeNetworkCode);
	}

	@Override
	public void batchInsertCountryCodeNetworkCode(Collection <CountryCodeNetworkCodeEntity> countryCodeNetworkCodeList) {
		for (CountryCodeNetworkCodeEntity countryCodeNetworkCode : countryCodeNetworkCodeList) {
			CountryEntity country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
			if (em.find(CountryEntity.class, country.getCountryCode()) == null)
				em.persist(country);
			em.persist(countryCodeNetworkCode);
		}
	}
}
