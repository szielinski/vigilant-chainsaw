package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.network.data.business.CountryCodeNetworkCodeDataBean;
import data.CountryCodeNetworkCodeEntittyCK;
import data.CountryCodeNetworkCodeEntity;
import data.CountryEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Transactional
public class CountryCodeNetworkCodeServiceEJBTest {

    @EJB
    private CountryCodeNetworkCodeDataBean service;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void addCountryCodeNetworkCodeTest() {
        CountryEntity country = new CountryEntity(1, "country");
        CountryCodeNetworkCodeEntittyCK ck = new CountryCodeNetworkCodeEntittyCK(country, 1);
        CountryCodeNetworkCodeEntity ccncck = new CountryCodeNetworkCodeEntity(ck, "one");

        CountryEntity countryTwo = new CountryEntity(2, "countryTwo");
        CountryCodeNetworkCodeEntittyCK ckTwo = new CountryCodeNetworkCodeEntittyCK(countryTwo, 2);
        CountryCodeNetworkCodeEntity ccncckTwo = new CountryCodeNetworkCodeEntity(ckTwo, "two");

        CountryEntity countryThree = new CountryEntity(3, "countryThree");
        CountryCodeNetworkCodeEntittyCK ckThree = new CountryCodeNetworkCodeEntittyCK(countryThree, 3);
        CountryCodeNetworkCodeEntity ccncckThree = new CountryCodeNetworkCodeEntity(ckThree, "Three");

        CountryCodeNetworkCodeEntity[] ccncArray = {ccncck, ccncckTwo, ccncckThree};

        Collection<CountryCodeNetworkCodeEntity> countryCodeNetworkCodesOriginal = new ArrayList<>();
        for (CountryCodeNetworkCodeEntity c : ccncArray) {
            countryCodeNetworkCodesOriginal.add(c);
        }

        service.addCountryCodeNetworkCodes(countryCodeNetworkCodesOriginal);

        Collection<CountryCodeNetworkCodeEntity> retrievedCountryNetworkCodes = service.getCountryCodeNetworkCodes();
        System.out.println("Size of collection: " + retrievedCountryNetworkCodes.size());
        for (CountryCodeNetworkCodeEntity c : retrievedCountryNetworkCodes) {
            assertTrue("An object failed to be retrieved", countryCodeNetworkCodesOriginal.contains(c));
        }
    }
}
