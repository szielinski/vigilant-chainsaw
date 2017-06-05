package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntittyCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class CountryCodeNetworkCodeJPA {

    @PersistenceContext
    private EntityManager em;

    public Collection<CountryCodeNetworkCodeEntity> getAllCountryCodeNetworkCodes() {
        return em.createNamedQuery("findAllCountryCodeNetworkCodes").getResultList();
    }

    public CountryCodeNetworkCodeEntity getCountryCodeNetworkCode(int networkCode, int countryCode) {
        CountryEntity country = new CountryEntity();
        country.setCountryCode(countryCode);
        return em.find(CountryCodeNetworkCodeEntity.class, new CountryCodeNetworkCodeEntittyCK(country, networkCode));
    }

    public void insertCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode) {
        CountryEntity country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
        if (em.find(CountryEntity.class, country.getCountryCode()) == null)
            em.persist(country);
        em.persist(countryCodeNetworkCode);
    }

    public void batchInsertCountryCodeNetworkCode(Collection<CountryCodeNetworkCodeEntity> countryCodeNetworkCodeList) {
        for (CountryCodeNetworkCodeEntity countryCodeNetworkCode : countryCodeNetworkCodeList) {
            CountryEntity country = countryCodeNetworkCode.getCountryCodeNetworkCode().getCountry();
            if (em.find(CountryEntity.class, country.getCountryCode()) == null)
                em.persist(country);
            em.persist(countryCodeNetworkCode);
        }
    }
}
