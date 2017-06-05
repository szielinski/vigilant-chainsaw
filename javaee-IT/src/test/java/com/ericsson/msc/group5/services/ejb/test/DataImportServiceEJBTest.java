package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.failureslog.dataimport.business.DataImporterBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.IOException;

@RunWith(Arquillian.class)
public class DataImportServiceEJBTest {

    @EJB
    private DataImporterBean dataImportService;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testDataImportService() {

        HSSFWorkbook workbook = null;
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(this.getClass().getResourceAsStream("/TestingDataset.xls"));
            workbook = new HSSFWorkbook(fileSystem);
        } catch (IOException e) {
            // File will be there unless removed from resources.
        }
        dataImportService.importSpreadsheet(workbook);
    }
}
