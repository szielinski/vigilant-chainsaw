package com.ericsson.msc.failuremanagement.validation.business;

import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface DateUtilityService {

	String formatDateAsString(Date dateCellValue);

	Timestamp formatDateStringAsTimestamp(String inputDateString);

}
