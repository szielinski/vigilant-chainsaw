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
public class UserAuthenticatorBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserJPA userDAO;
    @Inject
    private PasswordGeneratorBean passwordGeneratorService;

    public String authenticateUser(String username, String password) {
        UserEntity user = userDAO.getUser(username);
        System.out.println("password is " + password);
        System.out.println("password is " + passwordGeneratorService.generate(password));
        if (user != null && user.getPassword().equals(passwordGeneratorService.generate(password)))
            return user.getRole();
        return null;
    }
}
