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
public class NEVersionValidatorTest {

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
	
	private boolean expectedResult1 = true;
	private String neVersion1 = "222";
	
	private boolean expectedResult2 = false;
	private String neVersion2 = "10";
	private String neVersion3 = null;


	@Inject
	ValidatorBean service;

	@Test
	public void validateNEVersion() {
		assertEquals(expectedResult1, service.validateNEVersion(neVersion1));
		assertEquals(expectedResult2, service.validateNEVersion(neVersion2));
		assertEquals(expectedResult2, service.validateNEVersion(neVersion3));
	}

}
