package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa.CountryCodeNetworkCodeJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@Local
public class CountryCodeNetworkCodeDataBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CountryCodeNetworkCodeJPA dao;

    public Collection<CountryCodeNetworkCodeEntity> getCountryCodeNetworkCodes() {
        return dao.getAllCountryCodeNetworkCodes();
    }

    public void addCountryCodeNetworkCodes(Collection<CountryCodeNetworkCodeEntity> countryNetworkCodes) {
        dao.batchInsertCountryCodeNetworkCode(countryNetworkCodes);

    }

    public void addCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode) {
        dao.insertCountryCodeNetworkCode(countryCodeNetworkCode);
    }
}
