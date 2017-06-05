package com.ericsson.msc.failuremanagement.authorization.business;

import org.jboss.security.auth.spi.Util;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Password Generator containing password generating methods.
 */
@Local
@Stateless
public class PasswordGeneratorBean {

    public String generate(String password) {
        return Util.createPasswordHash("SHA-256", "BASE64", null, null,
                password);
    }
}
