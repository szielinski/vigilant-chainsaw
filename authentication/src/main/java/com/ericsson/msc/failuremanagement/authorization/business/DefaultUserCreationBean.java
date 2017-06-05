package com.ericsson.msc.failuremanagement.authorization.business;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class DefaultUserCreationBean {
    @EJB
    UserDataBean userService;

    @PostConstruct
    public void init() {
        userService.addUser("administrator", "admin", "administrator");
    }
}
