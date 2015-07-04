package com.ericsson.msc.failuremanagement.accesscontrol.business;

import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.accesscontrol.data.User;

/**
 * User service EJB interface.
 */
@Local
public interface UserService {

	public boolean addUser(String username, String password, String userRole);

	User getUser(String username);
}
