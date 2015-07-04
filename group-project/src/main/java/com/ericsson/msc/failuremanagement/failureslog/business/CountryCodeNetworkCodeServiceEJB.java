package com.ericsson.msc.failuremanagement.failureslog.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.data.CountryCodeNetworkCode;
import com.ericsson.msc.failuremanagement.failureslog.data.JPACountryCodeNetworkCodeDAO;

@Stateless
@Local
public class CountryCodeNetworkCodeServiceEJB implements CountryCodeNetworkCodeService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private JPACountryCodeNetworkCodeDAO dao;

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
