package com.ericsson.msc.group5.rest;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.ericsson.msc.group5.entities.FailureTrace;
import com.ericsson.msc.group5.services.DataImportService;
import com.ericsson.msc.group5.services.FailureTraceService;

@Path("/query")
public class CreatedQueries {

	@EJB
	private FailureTraceService failureTraceEJB;

	@Inject
	private DataImportService dataImport;

	// dataImport.importSpreadsheet("C:\\Users\\Harry\\Documents\\data.xls");
	// return failureTraceEJB.findImsiOfFailureByTimePeriod(
	// "11/01/2013  17:15:00", "11/01/2013  17:39:00");
	// return failureTraceEJB.getEventCauseCombinations("344930000000011");
	// dataImport.importSpreadsheet("C:\\Users\\Harry\\Documents\\data.xls");

	@POST
	@Path("/eventCause")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventCauseCombinations(String imsi) {

		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println("Time take %d milliseconds.\n" + total);
		return Response.ok().status(200)
				.entity(failureTraceEJB.getEventCauseCombinations(imsi))
				.build();
	}

	@GET
	@Path("getAllFailureTraces")
	public Collection <FailureTrace> getAllFailureTraces() {
		return failureTraceEJB.getAllFailureTraces();
	}
}