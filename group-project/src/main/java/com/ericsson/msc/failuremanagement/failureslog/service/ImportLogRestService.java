package com.ericsson.msc.failuremanagement.failureslog.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ericsson.msc.failuremanagement.failureslog.business.LogDetailsRetrieverService;

@Path("/importLog")
public class ImportLogRestService {

	@Inject
	private LogDetailsRetrieverService logDetailsRetriever;

	@GET
	@Path("/getLastImportDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogDetails() {
		return Response.ok().status(200).entity(logDetailsRetriever.retrieveLogDetailsAsJson()).build();
	}
}
