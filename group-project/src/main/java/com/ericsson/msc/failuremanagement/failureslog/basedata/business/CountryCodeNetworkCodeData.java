package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;

/**
 * CountryCodeNetworkCode service EJB interface.
 */
@Local
public interface CountryCodeNetworkCodeData {

	public Collection <CountryCodeNetworkCodeEntity> getCountryCodeNetworkCodes();

	public void addCountryCodeNetworkCodes(Collection <CountryCodeNetworkCodeEntity> countryNetworkCodes);

	public void addCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode);
}
