package com.ericsson.msc.group5.services.ejb.test;

import authorization.business.UserDataBean;
import com.ericsson.msc.failuremanagement.user.data.data.UserEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserServiceEJBTests {

    @EJB
    private UserDataBean service;

    @Test
    public void testUserIsAdded() {
        service.addUser("SAM", "password", "administrator");
        UserEntity newUser = service.getUser("SAM");
        assertEquals(newUser.getUsername(), "SAM");
    }

    @Test
    public void testExistingUser() {
        service.addUser("test", "pass", "administrator");
        assertEquals(service.addUser("test", "pass", "administrator"), false);
    }

}
