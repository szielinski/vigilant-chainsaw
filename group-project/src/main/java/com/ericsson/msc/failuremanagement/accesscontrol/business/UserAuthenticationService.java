package com.ericsson.msc.failuremanagement.accesscontrol.business;

import javax.ejb.Local;
/**
 * UserAuthentication service EJB interface.
 */
@Local
public interface UserAuthenticationService {
	public String authenticateUser(String username, String password);
}
