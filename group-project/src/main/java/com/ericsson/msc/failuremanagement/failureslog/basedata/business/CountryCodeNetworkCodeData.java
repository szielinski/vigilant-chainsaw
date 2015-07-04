package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCode;

/**
 * CountryCodeNetworkCode service EJB interface.
 */
@Local
public interface CountryCodeNetworkCodeData {

	public Collection <CountryCodeNetworkCode> getCountryCodeNetworkCodes();

	public void addCountryCodeNetworkCodes(Collection <CountryCodeNetworkCode> countryNetworkCodes);

	public void addCountryCodeNetworkCode(CountryCodeNetworkCode countryCodeNetworkCode);
}
