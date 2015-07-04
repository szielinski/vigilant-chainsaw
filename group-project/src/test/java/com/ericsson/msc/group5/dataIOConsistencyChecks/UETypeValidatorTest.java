package com.ericsson.msc.group5.dataIOConsistencyChecks;

import static org.junit.Assert.assertEquals;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.validation.business.ValidatorBean;

@RunWith(Arquillian.class)
public class UETypeValidatorTest {

	private boolean result1 = true;
	private boolean result2 = false;
	private Integer ueType1 = 100000;
	private Integer ueType2 = 100;
	private Integer ueType3 = null;

	@Inject
	ValidatorBean service;

	@Test
	public void validateUEType() {
		assertEquals(result1, service.validateUEType(ueType1));
		assertEquals(result2, service.validateUEType(ueType2));
		assertEquals(result2, service.validateUEType(ueType3));
	}

}