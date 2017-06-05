package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.authorization.business.UserAuthenticatorBean;
import com.ericsson.msc.failuremanagement.authorization.business.UserDataBean;
import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserAuthenicationServiceEJBTest {

    @EJB
    private UserAuthenticatorBean service;

    @EJB
    UserDataBean userService;

    @Test
    public void authenticateUserTest() {
        userService.addUser("John", "nothing", "administrator");
        UserEntity user = userService.getUser("John");
        assertEquals(service.authenticateUser("John", "nothing"), "administrator");
    }

    @Test
    public void authenticalUserNullTest() {
        assertEquals(service.authenticateUser("Should", "fail"), null);
    }

}
