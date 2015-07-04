package com.ericsson.msc.failuremanagement.accesscontrol.data;

/**
 * A Data Access Object interface for the User entity. Defines common
 * DAO methods.
 */
public interface UserDAO {

	/**
	 * Add a User 
	 */
	public void addUser(User user);
	
	/**
	 * Retrieve the User 
	 */
	public User getUser(String username);
}
