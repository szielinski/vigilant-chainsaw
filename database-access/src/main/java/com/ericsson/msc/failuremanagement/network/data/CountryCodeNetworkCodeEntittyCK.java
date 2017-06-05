package com.ericsson.msc.failuremanagement.network.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Composite Key that helps to map/uniquely identify an operator using a combination of Country/Network.
 */
@Embeddable
public class CountryCodeNetworkCodeEntittyCK implements Serializable {

	private static final long serialVersionUID = -4973782694581889595L;

	@Column(name = "network_code")
	private Integer networkCode;
	@ManyToOne
	@JoinColumn(name = "country_code")
	private CountryEntity country;

	/**
	 * No-args constructor used by the JPA.
	 */
	public CountryCodeNetworkCodeEntittyCK() {
	}

	/**
	 * Create a composite key made up of a Country object and a networkCode. Together they can be used to uniquely identify an operator.
	 * 
	 * @param country
	 *            The first part of the composite key - a country with a unique country id.
	 * @param networkCode
	 *            The second part of the composite key - a non-unique network code id. This key is non-unique by itself, but forms a unique composite key when
	 *            combined with the country code.
	 */
	public CountryCodeNetworkCodeEntittyCK(CountryEntity country, Integer networkCode) {
		this.country = country;
		this.networkCode = networkCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((networkCode == null) ? 0 : networkCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryCodeNetworkCodeEntittyCK other = (CountryCodeNetworkCodeEntittyCK) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		}
		else if ( !country.equals(other.country))
			return false;
		if (networkCode == null) {
			if (other.networkCode != null)
				return false;
		}
		else if ( !networkCode.equals(other.networkCode))
			return false;
		return true;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public Integer getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(Integer networkCode) {
		this.networkCode = networkCode;
	}
}
