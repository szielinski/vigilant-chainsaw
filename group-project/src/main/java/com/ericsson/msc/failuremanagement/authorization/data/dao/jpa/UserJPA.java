package com.ericsson.msc.failuremanagement.authorization.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.authorization.data.User;
import com.ericsson.msc.failuremanagement.authorization.data.dao.UserDAO;

public class UserJPA implements UserDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void addUser(User user) {
		em.persist(user);
	}

	@Override
	public User getUser(String username) {
		List<User> resultList = em.createNamedQuery("findUserByUsername").setParameter("username", username).getResultList();
		if(resultList.size() == 0)
			return null;
		return resultList.get(0);
	}
}
