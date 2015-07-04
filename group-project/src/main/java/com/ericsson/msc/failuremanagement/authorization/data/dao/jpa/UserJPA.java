package com.ericsson.msc.failuremanagement.authorization.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;
import com.ericsson.msc.failuremanagement.authorization.data.dao.UserDAO;

public class UserJPA implements UserDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void addUser(UserEntity user) {
		em.persist(user);
	}

	@Override
	public UserEntity getUser(String username) {
		List<UserEntity> resultList = em.createNamedQuery("findUserByUsername").setParameter("username", username).getResultList();
		if(resultList.size() == 0)
			return null;
		return resultList.get(0);
	}
}
