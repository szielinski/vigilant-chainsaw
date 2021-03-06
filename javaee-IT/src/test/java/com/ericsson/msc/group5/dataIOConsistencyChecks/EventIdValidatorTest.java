package com.ericsson.msc.group5.dataIOConsistencyChecks;

import static org.junit.Assert.assertEquals;

import java.io.File;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.data.importer.business.ValidatorBean;

@RunWith(Arquillian.class)
public class EventIdValidatorTest {

	@Deployment(testable = true)
	public static Archive <?> createDeployment() {
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		File [] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();

		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, "com.ericsson")
				.addAsLibraries(libraries)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	private boolean result1 = true;
	private boolean result2 = false;
	private Integer eventId1 = 4097;
	private Integer eventId2 = 4098;
	private Integer eventId3 = 4125;
	private Integer eventId4 = 4106;
	private Integer eventId5 = 5000;
	private Integer eventId6 = null;

	@Inject
	ValidatorBean service;
	
	@Test
	public void validateEventId() {
		assertEquals(result1, service.validateEventId(eventId1));
		assertEquals(result1, service.validateEventId(eventId2));
		assertEquals(result1, service.validateEventId(eventId3));
		assertEquals(result1, service.validateEventId(eventId4));
		assertEquals(result2, service.validateEventId(eventId5));
		assertEquals(result2, service.validateEventId(eventId6));
	}
}
