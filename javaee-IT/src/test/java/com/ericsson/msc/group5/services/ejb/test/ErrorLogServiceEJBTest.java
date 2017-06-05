package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.failureslog.validation.business.ErrorLogWriterBean;
import com.ericsson.msc.failuremanagement.network.error.data.ErrorLog;
import com.ericsson.msc.failuremanagement.network.error.data.dao.jpa.ErrorLogJPA;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ErrorLogServiceEJBTest {

    @EJB
    private ErrorLogWriterBean service;
    @Inject
    private ErrorLogJPA errorLogDAO;

    @Test
    public void writeToErrorLogTest() {

        HSSFWorkbook workbook = null;

        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(this.getClass().getResourceAsStream("/TestingDataset.xls"));
            workbook = new HSSFWorkbook(fileSystem);
        } catch (IOException e) {
        }
        HSSFSheet worksheet = (HSSFSheet) workbook.getSheetAt(0);
        HSSFRow row = (HSSFRow) worksheet.getRow(1);


        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());

        ErrorLog errorlog = new ErrorLog(dateNow, "error", "data");

        service.writeToErrorLog(row, "error");
        assertEquals("error", errorlog.getErrorDescription());
    }

}
