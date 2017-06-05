package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.authorization.business.PasswordGeneratorBean;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class PasswordGeneratorServiceEJBTest {

    @EJB
    private PasswordGeneratorBean service;

    @Test
    public void testGenerate() {
        String password = "password";

        assertEquals(service.generate(password), "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=");
    }

}
