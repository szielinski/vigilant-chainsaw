package com.ericsson.msc.failuremanagement.failureslog.validation.business;

import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface DateUtility {

	String formatDateAsString(Date dateCellValue);

	Timestamp formatDateStringAsTimestamp(String inputDateString);

}
