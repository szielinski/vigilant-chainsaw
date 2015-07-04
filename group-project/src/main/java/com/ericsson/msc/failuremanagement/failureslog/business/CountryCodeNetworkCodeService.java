package com.ericsson.msc.failuremanagement.failureslog.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.data.CountryCodeNetworkCode;

/**
 * CountryCodeNetworkCode service EJB interface.
 */
@Local
public interface CountryCodeNetworkCodeService {

	public Collection <CountryCodeNetworkCode> getCountryCodeNetworkCodes();

	public void addCountryCodeNetworkCodes(Collection <CountryCodeNetworkCode> countryNetworkCodes);

	public void addCountryCodeNetworkCode(CountryCodeNetworkCode countryCodeNetworkCode);
}
