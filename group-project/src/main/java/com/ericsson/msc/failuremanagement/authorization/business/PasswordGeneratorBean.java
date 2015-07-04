package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;
import javax.ejb.Stateless;
import org.jboss.security.auth.spi.Util;

/**
 * Password Generator containing password generating methods.
 */

@Local
@Stateless
public class PasswordGeneratorBean implements PasswordGenerator {

	@Override
	public String generate(String password) {
		return Util.createPasswordHash("SHA-256", "BASE64", null, null,
				password);
	}
}