package com.ericsson.msc.failuremanagement.authorization.business;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;
import com.ericsson.msc.failuremanagement.authorization.data.dao.UserDAO;

@Stateless
@Local
public class UserDataBean implements UserData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserDAO dao;
	@Inject
	private PasswordGenerator passwordGeneratorService;

	@Override
	public boolean addUser(String username, String password, String userRole) {
		if (dao.getUser(username) != null){
			return false;
		}
		dao.addUser(new UserEntity(username, passwordGeneratorService.generate(password), userRole));
		return true;
	}

	@Override
	public UserEntity getUser(String username) {
		return dao.getUser(username);
	}
}
