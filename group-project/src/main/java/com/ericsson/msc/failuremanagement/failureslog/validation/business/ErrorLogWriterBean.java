package com.ericsson.msc.failuremanagement.failureslog.validation.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.ericsson.msc.failuremanagement.failureslog.validation.data.ErrorLog;
import com.ericsson.msc.failuremanagement.failureslog.validation.data.dao.ErrorLogDAO;

@Stateless
@Local
public class ErrorLogWriterBean implements ErrorLogWriter {

	@Inject
	private ErrorLogDAO errorLogDAO;
	@Inject
	private DateUtility dateUtilityService;

	private static Date dateObj = new Date();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final static String [] headings = {"Date / Time: ", " Event Id: ", " Failure Class: ", " UE Type: ", " Market: ", " Operator: ", " Cell Id: ",
			" Duration: ", " Cause Code: ", " NE Version: ", " IMSI: ", " HIER3_ID: ", " HIER32_ID: ", " HIER321_ID: "};

	public void writeToErrorLog(HSSFRow rowOfBaseData, String errorDescription) {
		StringBuilder buffer = new StringBuilder(400);
		String date = dateUtilityService.formatDateAsString(rowOfBaseData.getCell(0).getDateCellValue());
		buffer.append(headings[0]);
		buffer.append(date);

		for (int i = 1; i < headings.length; i++) {
			buffer.append(headings[i]);
			HSSFCell cell = rowOfBaseData.getCell(i);
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				buffer.append((int) cell.getNumericCellValue());
			}
			else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				buffer.append(cell.getStringCellValue());
			}
		}
		errorLogDAO.insertErrorLog(new ErrorLog(dateFormat.format(dateObj), errorDescription, buffer.toString()));
	}

	public void startNewFile() {
		dateObj = new Date();
	}

	public String getTimestamp() {
		return dateFormat.format(dateObj);
	}
}
