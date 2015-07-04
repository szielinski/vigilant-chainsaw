package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCode;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa.CountryCodeNetworkCodeJPA;

@Stateless
@Local
public class CountryCodeNetworkCodeDataBean implements CountryCodeNetworkCodeData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private CountryCodeNetworkCodeJPA dao;

	@Override
	public Collection <CountryCodeNetworkCode> getCountryCodeNetworkCodes() {
		return dao.getAllCountryCodeNetworkCodes();
	}

	@Override
	public void addCountryCodeNetworkCodes(Collection <CountryCodeNetworkCode> countryNetworkCodes) {
		dao.batchInsertCountryCodeNetworkCode(countryNetworkCodes);

	}

	@Override
	public void addCountryCodeNetworkCode(CountryCodeNetworkCode countryCodeNetworkCode) {
		dao.insertCountryCodeNetworkCode(countryCodeNetworkCode);

	}

}
