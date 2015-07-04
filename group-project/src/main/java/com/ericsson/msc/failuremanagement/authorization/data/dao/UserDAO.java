package com.ericsson.msc.failuremanagement.authorization.data.dao;

import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;

/**
 * A Data Access Object interface for the User entity. Defines common
 * DAO methods.
 */
public interface UserDAO {

	/**
	 * Add a User 
	 */
	public void addUser(UserEntity user);
	
	/**
	 * Retrieve the User 
	 */
	public UserEntity getUser(String username);
}
