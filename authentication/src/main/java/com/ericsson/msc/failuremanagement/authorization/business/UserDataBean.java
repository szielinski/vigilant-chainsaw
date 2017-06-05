package com.ericsson.msc.failuremanagement.authorization.business;

import com.ericsson.msc.failuremanagement.user.data.UserEntity;
import com.ericsson.msc.failuremanagement.user.data.dao.UserJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local
public class UserDataBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserJPA dao;
    @Inject
    private PasswordGeneratorBean passwordGeneratorService;

    public boolean addUser(String username, String password, String userRole) {
        if (dao.getUser(username) != null) {
            return false;
        }
        dao.addUser(new UserEntity(username, passwordGeneratorService.generate(password), userRole));
        return true;
    }

    public UserEntity getUser(String username) {
        return dao.getUser(username);
    }
}
