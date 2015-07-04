package com.ericsson.msc.failuremanagement.accesscontrol.business;

import javax.ejb.Local;

@Local
public interface PasswordGeneratorService {

	public String generate(String password);
}
