package com.ericsson.msc.failuremanagement.failureslog.validation.business;

import com.ericsson.msc.failuremanagement.data.util.DateUtilityBean;
import com.ericsson.msc.failuremanagement.network.error.ErrorLog;
import com.ericsson.msc.failuremanagement.network.error.dao.ErrorLogJPA;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
@Local
public class ErrorLogWriterBean {

    @Inject
    private ErrorLogJPA errorLogDAO;
    @Inject
    private DateUtilityBean dateUtilityService;

    private static Date dateObj = new Date();
    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private final static String[] headings = {"Date / Time: ", " Event Id: ", " Failure Class: ", " UE Type: ", " Market: ", " Operator: ", " Cell Id: ",
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
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
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
