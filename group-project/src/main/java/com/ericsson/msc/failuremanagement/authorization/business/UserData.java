package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.authorization.data.User;

/**
 * User service EJB interface.
 */
@Local
public interface UserData {

	public boolean addUser(String username, String password, String userRole);

	User getUser(String username);
}
