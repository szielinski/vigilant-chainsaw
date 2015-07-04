package com.ericsson.msc.failuremanagement.failureslog.dataimport.auto;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface AutoImportService {

	void importSpreadsheet(HSSFWorkbook excelWorkbook);
}
