package com.ericsson.msc.failuremanagement.accesscontrol.business;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class AdminAccountCreatorEJB {

	@EJB
	UserService userService;

	@PostConstruct
	public void init() {
		userService.addUser("administrator", "admin", "administrator");
	}
}
