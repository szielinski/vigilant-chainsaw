package com.ericsson.msc.failuremanagement.failureslog.dataimport.business;

import javax.ejb.Local;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * DataImport service EJB interface.
 */
@Local
public interface DataImporter {

	public void importSpreadsheet(HSSFWorkbook excelWorkbook);

	public String getTimestamp();

	public String getAddedCount();

	public String getRejectedCount();

	public void finaliseErrorLogEntry(String importType);
}
