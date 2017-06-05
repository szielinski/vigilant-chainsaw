package com.ericsson.msc.group5.services.ejb.test;

import authorization.business.UserDataBean;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class AdminAccountCreatorEJBTest {

    @EJB
    private UserDataBean service;

    @Test
    public void initTest() {
        assertEquals("administrator", service.getUser("administrator").getUsername());
    }
}
