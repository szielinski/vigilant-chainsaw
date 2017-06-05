package com.ericsson.msc.failuremanagement.authorization.data.dao.jpa;

import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserJPA {

    @PersistenceContext
    private EntityManager em;

    public void addUser(UserEntity user) {
        em.persist(user);
    }

    public UserEntity getUser(String username) {
        List<UserEntity> resultList = em.createNamedQuery("findUserByUsername").setParameter("username", username).getResultList();
        if (resultList.size() == 0)
            return null;
        return resultList.get(0);
    }
}
