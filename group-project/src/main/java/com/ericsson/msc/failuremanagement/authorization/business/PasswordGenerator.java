package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;

@Local
public interface PasswordGenerator {

	public String generate(String password);
}
