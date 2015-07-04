package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao;

import java.util.Collection;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;

/**
 * A Data Access Object interface for the CountryCodeNetworkCode entity. Defines common DAO methods.
 */
public interface CountryCodeNetworkCodeDAO {

	/**
	 * Retrieve all CountryCodeNetworkCode objects present in the data store.
	 * 
	 * @return a Collection of CountryCodeNetworkCode objects; empty collection if no CountryCodeNetworkCode objects are present in the data store.
	 */
	public Collection <CountryCodeNetworkCodeEntity> getAllCountryCodeNetworkCodes();

	/**
	 * Retrieve the CountryCodeNetworkCode associated with the 2-part composite key passed in as a parameter.
	 * 
	 * @param networkCode
	 *            first part of a 2-part composite key that uniquely identifies the CountryCodeNetworkCode to be retrieved.
	 * @param countryCode
	 *            second part of a 2-part composite key that uniquely identifies the CountryCodeNetworkCode to be retrieved.
	 * @return CountryCodeNetworkCode with the provided networkCode+countryCode combination iff present in the data store; otherwise null.
	 */
	public CountryCodeNetworkCodeEntity getCountryCodeNetworkCode(int networkCode, int countryCode);

	/**
	 * Insert a new CountryCodeNetworkCode object into the data store.
	 * 
	 * @param countryCodeNetworkCode
	 *            A new CountryCodeNetworkCode object.
	 */
	public void insertCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode);

	/**
	 * Batch insert a collection of CountryCodeNetworkCode objects into the data store. Optimized for handling large volumes of data.
	 * 
	 * @param countryCodeNetworkCodeList
	 *            A collection of new CountryCodeNetworkCode objects.
	 */
	public void batchInsertCountryCodeNetworkCode(Collection <CountryCodeNetworkCodeEntity> countryCodeNetworkCodeList);
}
