package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.authorization.data.User;
import com.ericsson.msc.failuremanagement.authorization.data.dao.UserDAO;

@Stateless
@Local
public class UserAuthenticatorBean implements UserAuthenticator {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserDAO userDAO;
	@Inject
	private PasswordGenerator passwordGeneratorService;

	@Override
	public String authenticateUser(String username, String password) {
		User user = userDAO.getUser(username);
		System.out.println("password is " + password);
		System.out.println("password is " + passwordGeneratorService.generate(password));
		if (user != null && user.getPassword().equals(passwordGeneratorService.generate(password)))
			return user.getRole();
		return null;
	}
}
