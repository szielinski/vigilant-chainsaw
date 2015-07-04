package com.ericsson.msc.group5.services.ejb.test;

import static org.junit.Assert.assertEquals;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.authorization.business.UserAuthenticator;
import com.ericsson.msc.failuremanagement.authorization.business.UserData;
import com.ericsson.msc.failuremanagement.authorization.data.UserEntity;

@RunWith(Arquillian.class)
public class UserAuthenicationServiceEJBTest {

	@EJB
	private UserAuthenticator service;

	@EJB
	UserData userService;

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
