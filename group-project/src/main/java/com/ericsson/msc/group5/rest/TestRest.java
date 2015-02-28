package com.ericsson.msc.group5.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.ericsson.msc.group5.entities.FailureClass;
import com.ericsson.msc.group5.services.FailureClassService;

@Path("/test")
public class TestRest {

	@EJB 
	private FailureClassService failureClassEJB;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <FailureClass> getFailureClass() {
		List<FailureClass> ls = new ArrayList<FailureClass>();
		ls.add(new FailureClass(1, "hello1"));
		ls.add(new FailureClass(2, "hello2"));
		ls.add(new FailureClass(3, "hello3"));
		ls.add(new FailureClass(4, "hello4"));
		ls.add(new FailureClass(5, "hello5"));
		failureClassEJB.addFailureClasses(ls);
		
		return failureClassEJB.getFailureClasses();
//		return em.createQuery("select ft from FailureClass ft ").getResultList();
	}
}
