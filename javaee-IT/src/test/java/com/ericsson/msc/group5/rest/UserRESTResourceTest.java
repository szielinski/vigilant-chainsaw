package com.ericsson.msc.group5.rest;

import static com.jayway.restassured.RestAssured.given;
import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.config.RestAssuredConfig;

@RunWith(Arquillian.class)
public class UserRESTResourceTest {

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test.war")

				.addPackages(true, "group5.dao", "group5.dao.jpa", "group5.entities",
						"group5.rest", "group5.services", "group5.services.ejb",
						"group5.servlet", "group5.utils").addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.setWebXML(new File("src/main/webapp/WEB-INF/web.xml")).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		File [] libs;

		libs = Maven.resolver().resolve("com.jayway.restassured:rest-assured:2.4.0").withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.apache.poi:poi:3.11").withTransitivity().as(File.class);
		archive.addAsLibraries(libs);

		return archive;
	}

    @Before
    public void setUp() throws Exception{
        RestAssured.config = RestAssuredConfig.config().logConfig(new LogConfig(System.out, true));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "group-project/rest";
        RestAssured.port = 8080;
    }

    @Test
    public void basicAuthentication() throws Exception {
        given().auth().basic("administrator", "admin").expect().statusCode(200).when().get("/get/userrole");
    }
}
