package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;
/**
 * UserAuthentication service EJB interface.
 */
@Local
public interface UserAuthenticator {
	public String authenticateUser(String username, String password);
}
