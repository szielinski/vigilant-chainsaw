package com.ericsson.msc.group5.rest;

import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.ericsson.msc.group5.entities.FailureTrace;
import com.ericsson.msc.group5.services.DataImportService;
import com.ericsson.msc.group5.services.FailureTraceService;

@Path("/testingQueries")
public class TestRest {

	@Inject
	private FailureTraceService failureTraceEJB;

	@Inject
	private DataImportService dataImport;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection <FailureTrace> getEventCauses() {
		dataImport.importSpreadsheet("C:\\Users\\Harry\\Documents\\data.xls");
		return failureTraceEJB.getEventCauseCombinations("344930000000011");

	}
	// @GET
	// @Produces(MediaType.APPLICATION_JSON)
	// public Collection <FailureClass> getFailureClass() {
	// dataImport.importSpreadsheet("C:\\Users\\Harry\\Documents\\data.xls");
	//
	// // List<FailureClass> ls = new ArrayList<FailureClass>();
	// // ls.add(new FailureClass(6, "hello1"));
	// // ls.add(new FailureClass(7, "hello2"));
	// // ls.add(new FailureClass(8, "hello3"));
	// // ls.add(new FailureClass(9, "hello4"));
	// // ls.add(new FailureClass(10, "hello5"));
	// // failureClassEJB.addFailureClasses(ls);
	//
	// return failureClassEJB.getFailureClasses();
	// }

}