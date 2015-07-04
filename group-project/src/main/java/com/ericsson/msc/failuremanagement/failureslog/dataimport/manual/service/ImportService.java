package com.ericsson.msc.failuremanagement.failureslog.dataimport.manual.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.ericsson.msc.failuremanagement.failureslog.basedata.business.FailureTraceData;
import com.ericsson.msc.failuremanagement.failureslog.dataimport.business.DataImporter;
import com.ericsson.msc.failuremanagement.failureslog.dataimport.business.DataImporterBean;

@Path("/restImportService")
public class ImportService {

	@EJB
	private FailureTraceData failureTraceEJB;

	@Inject
	private DataImporter dataImport;

	@POST
	@Path("/import")
	@Consumes("multipart/form-data")
	public Response importUploadedFile(@MultipartForm FileUploadForm form) {
		String resultString = "";

		java.net.URI location = null;
		try {
			location = new java.net.URI("../pages/failed_import.html?");
		}
		catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		try {
			HSSFWorkbook wb = new HSSFWorkbook(new ByteArrayInputStream(form.getFileData()));
			dataImport.importSpreadsheet(wb);
			dataImport.finaliseErrorLogEntry("Manual");
			String timestamp = dataImport.getTimestamp();
			String validRecordCount = dataImport.getAddedCount();
			String invalidRecordCount = dataImport.getRejectedCount();
			resultString = "time_taken=" + DataImporterBean.duration + "ms&timestamp=" + timestamp + "&added=" + validRecordCount + "&rejected="
					+ invalidRecordCount;
			System.out.println(resultString);
		}
		catch (IOException e) {
			resultString = "Import was unsuccessful";
			return Response.temporaryRedirect(location).build();
		}

		try {
			location = new java.net.URI("../pages/import_results.html?" + URLEncoder.encode(resultString, "UTF-8"));
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Response.temporaryRedirect(location).build();
	}
}
