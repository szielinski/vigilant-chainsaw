package com.ericsson.msc.failuremanagement.failureslog.validation.business;

import javax.ejb.Local;
import org.apache.poi.hssf.usermodel.HSSFRow;
/**
 * Error Log Writer service EJB interface.
 */
@Local
public interface ErrorLogWriter {

	public void writeToErrorLog(HSSFRow rowOfBaseData, String errorDescription);
	
	public String getTimestamp();
	
	public void startNewFile();
}
