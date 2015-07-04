package com.ericsson.msc.failuremanagement.failureslog.dataimport.auto.business;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface AutoImporter {

	void importSpreadsheet(HSSFWorkbook excelWorkbook);
}
