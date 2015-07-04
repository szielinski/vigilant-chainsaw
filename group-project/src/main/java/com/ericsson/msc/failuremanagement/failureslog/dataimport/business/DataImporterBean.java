package com.ericsson.msc.failuremanagement.failureslog.dataimport.business;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.CountryCodeNetworkCodeEntittyCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureTraceEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryCodeNetworkCodeDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.CountryDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.EventCauseDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureClassDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.FailureTraceDAO;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.UserEquipmentDAO;
import com.ericsson.msc.failuremanagement.failureslog.validation.business.ErrorLogWriter;
import com.ericsson.msc.failuremanagement.failureslog.validation.business.ValidatorBean;

@Stateless
@Local
@Path("/import")
public class DataImporterBean implements DataImporter {

	// static Logger log =
	// Logger.getLogger(DataImportServiceEJB.class.getName());

	@Inject
	private CountryCodeNetworkCodeDAO countryCodeNetworkCodeDAO;
	@Inject
	private CountryDAO countryDAO;
	@Inject
	private EventCauseDAO eventCauseDAO;
	@Inject
	private FailureClassDAO failureClassDAO;
	@Inject
	private FailureTraceDAO failureTraceDAO;
	@Inject
	private UserEquipmentDAO userEquipmentDAO;
	@EJB
	private ErrorLogWriter errorLogWriterService;
	@Inject
	private ValidatorBean validatorService;

	private HashMap <Integer, EventCauseEntity> eventCauseHashMap = new HashMap <>();
	private HashMap <Integer, FailureClassEntity> failureClassHashMap = new HashMap <>();
	private HashMap <Integer, UserEquipmentEntity> userEquipmentHashMap = new HashMap <>();
	private HashMap <Integer, CountryCodeNetworkCodeEntity> countryCodeNetworkCodeHashMap = new HashMap <>();
	private HashMap <Integer, CountryEntity> countryHashMap = new HashMap <>();
	public static long duration = 0;
	private int validRowsAdded = 0;
	private int invalidRowsRejected = 0;
	private java.nio.file.Path logFilePath;

	private enum ExcelDataSheet {
		BASE_DATA_TABLE(0), EVENT_CAUSE_TABLE(1), FAILURE_CLASS_TABLE(2), UE_TABLE(3), MCC_MNC_TABLE(4);

		private final int pageNumber;

		ExcelDataSheet(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		int getPageNumber() {
			return pageNumber;
		}
	}

	public void importSpreadsheet(HSSFWorkbook excelWorkbook) {
		validRowsAdded = 0;
		invalidRowsRejected = 0;
		errorLogWriterService.startNewFile();
		Collection <EventCauseEntity> existingEventCauseCollection = eventCauseDAO.getAllEventCauses();
		for (EventCauseEntity evCa : existingEventCauseCollection) {
			eventCauseHashMap.put(evCa.getCauseCodeEventIdCK().hashCode(), evCa);
		}
		Collection <FailureClassEntity> existingFailureClassCollection = failureClassDAO.getAllFailureClasses();
		for (FailureClassEntity fail : existingFailureClassCollection) {
			failureClassHashMap.put(fail.getFailureClass(), fail);
		}
		Collection <UserEquipmentEntity> existingUserEquipmentCollection = userEquipmentDAO.getAllUserEquipment();
		for (UserEquipmentEntity ue : existingUserEquipmentCollection) {
			userEquipmentHashMap.put(ue.getTypeAllocationCode(), ue);
		}
		Collection <CountryCodeNetworkCodeEntity> existingCountryCodeNetworkCodeCollection = countryCodeNetworkCodeDAO.getAllCountryCodeNetworkCodes();
		for (CountryCodeNetworkCodeEntity cn : existingCountryCodeNetworkCodeCollection) {
			countryCodeNetworkCodeHashMap.put(cn.getCountryCodeNetworkCode().hashCode(), cn);
		}
		Collection <CountryEntity> existingCountryCollection = countryDAO.getAllCountries();
		for (CountryEntity c : existingCountryCollection) {
			countryHashMap.put(c.getCountryCode(), c);
		}
		long start = System.currentTimeMillis();

		readExcelDocument(excelWorkbook);

		duration = System.currentTimeMillis() - start;
		writeToLogFile();
		System.out.printf("The import took %d milliseconds.\n", duration);
	}

	private void readExcelDocument(Workbook excelWorkbook) {
		readUserEquipmentDataSheet(excelWorkbook);
		readFailureClassDataSheet(excelWorkbook);
		readEventCauseDataSheet(excelWorkbook);
		readOperatorDataSheet(excelWorkbook);
		readBaseDataSheet(excelWorkbook);
	}

	private void readBaseDataSheet(Workbook excelWorkbook) {
		HSSFSheet baseDataWorksheet = (HSSFSheet) excelWorkbook.getSheetAt(ExcelDataSheet.BASE_DATA_TABLE.getPageNumber());
		Collection <FailureTraceEntity> failureTraceCollectionToFlush = new ArrayList <FailureTraceEntity>();
		int numRows = baseDataWorksheet.getLastRowNum();
		Long initialPKValue = failureTraceDAO.getTotalNumberOfEntries() + 1;
		for (int i = 1; i <= numRows; i++) {
			HSSFRow row = (HSSFRow) baseDataWorksheet.getRow(i);
			if ( !validatorService.validateFailureTraceRow(row)) {
				errorLogWriterService.writeToErrorLog(row, validatorService.getErrorDescriptionString());
				invalidRowsRejected++;
				continue;
			}
			Date dateTime = row.getCell(0).getDateCellValue();
			int eventId = (int) row.getCell(1).getNumericCellValue();
			int failureClass = (int) row.getCell(2).getNumericCellValue();
			int ueType = (int) row.getCell(3).getNumericCellValue();
			int countryCode = (int) row.getCell(4).getNumericCellValue();
			int networkCode = (int) row.getCell(5).getNumericCellValue();
			int cellId = (int) row.getCell(6).getNumericCellValue();
			int duration = (int) row.getCell(7).getNumericCellValue();
			int causeCode = (int) row.getCell(8).getNumericCellValue();
			String neVersion = row.getCell(9).getStringCellValue();
			String imsi = Long.toString((long) row.getCell(10).getNumericCellValue());
			String hier3 = Long.toString((long) row.getCell(11).getNumericCellValue());
			String hier32 = Long.toString((long) row.getCell(12).getNumericCellValue());
			String hier321 = Long.toString((long) row.getCell(13).getNumericCellValue());

			EventCauseEntity existingEventCause = null;
			if (eventCauseHashMap.containsKey(new EventCauseEntityCK(causeCode, eventId).hashCode())) {
				existingEventCause = eventCauseHashMap.get(new EventCauseEntityCK(causeCode, eventId).hashCode());
			}
			CountryCodeNetworkCodeEntity existingCountryCodeNetworkCode = null;
			CountryEntity existingCountry = null;
			if (countryHashMap.containsKey(countryCode)) {
				existingCountry = countryHashMap.get(countryCode);
				if (countryCodeNetworkCodeHashMap.containsKey(new CountryCodeNetworkCodeEntittyCK(existingCountry, networkCode).hashCode())) {
					existingCountryCodeNetworkCode = countryCodeNetworkCodeHashMap.get(new CountryCodeNetworkCodeEntittyCK(existingCountry, networkCode).hashCode());
				}
			}
			FailureClassEntity existingFailureClass = null;
			if (failureClassHashMap.containsKey(failureClass)) {
				existingFailureClass = failureClassHashMap.get(failureClass);
			}
			UserEquipmentEntity existingUserEquipment = null;
			if (userEquipmentHashMap.containsKey(ueType)) {
				existingUserEquipment = userEquipmentHashMap.get(ueType);
			}

			FailureTraceEntity createdFailureTrace = new FailureTraceEntity();
			createdFailureTrace.setFailureTraceId(initialPKValue);
			createdFailureTrace.setDateTime(dateTime);
			createdFailureTrace.setCountryCodeNetworkCode(existingCountryCodeNetworkCode);
			createdFailureTrace.setDuration(duration);
			createdFailureTrace.setCellId(cellId);
			createdFailureTrace.setEventCause(existingEventCause);
			createdFailureTrace.setFailureClass(existingFailureClass);
			createdFailureTrace.setHier3Id(hier3);
			createdFailureTrace.setHier32Id(hier32);
			createdFailureTrace.setHier321Id(hier321);
			createdFailureTrace.setIMSI(imsi);
			createdFailureTrace.setNeVersion(neVersion);
			createdFailureTrace.setUserEquipment(existingUserEquipment);

			failureTraceCollectionToFlush.add(createdFailureTrace);
			validRowsAdded++;
			initialPKValue++;
		}
		failureTraceDAO.batchInsertFailureTrace(failureTraceCollectionToFlush);
	}

	private void readEventCauseDataSheet(Workbook excelWorkbook) {
		HSSFSheet eventCauseWorksheet = (HSSFSheet) excelWorkbook.getSheetAt(ExcelDataSheet.EVENT_CAUSE_TABLE.getPageNumber());
		int numRows = eventCauseWorksheet.getLastRowNum();
		HSSFRow row;
		EventCauseEntityCK managedEventCauseCK;
		EventCauseEntity managedEventCause;
		Collection <EventCauseEntity> eventCauseCollection = new ArrayList <EventCauseEntity>();
		for (int i = 1; i <= numRows; i++) {
			row = (HSSFRow) eventCauseWorksheet.getRow(i);
			int causeCode = (int) row.getCell(0).getNumericCellValue();
			int eventId = (int) row.getCell(1).getNumericCellValue();
			String description = row.getCell(2).getStringCellValue();
			managedEventCauseCK = new EventCauseEntityCK(causeCode, eventId);
			managedEventCause = new EventCauseEntity((managedEventCauseCK), description);

			if (eventCauseHashMap.get(managedEventCauseCK.hashCode()) != null) {
				continue;
			}
			eventCauseCollection.add(managedEventCause);
			eventCauseHashMap.put((Integer) managedEventCauseCK.hashCode(), managedEventCause);
		}
		eventCauseDAO.batchInsertEventCause(eventCauseCollection);
	}

	private void readFailureClassDataSheet(Workbook excelWorkbook) {
		HSSFSheet failureClassWorksheet = (HSSFSheet) excelWorkbook.getSheetAt(ExcelDataSheet.FAILURE_CLASS_TABLE.getPageNumber());
		int numRows = failureClassWorksheet.getLastRowNum();
		HSSFRow row;
		FailureClassEntity managedFailureClass;
		Collection <FailureClassEntity> failureClassCollection = new ArrayList <FailureClassEntity>();
		for (int i = 1; i <= numRows; i++) {
			row = failureClassWorksheet.getRow(i);
			int failureClassId = (int) row.getCell(0).getNumericCellValue();
			String description = row.getCell(1).getStringCellValue();

			if (failureClassHashMap.get((Integer) failureClassId) != null) {
				continue;
			}

			managedFailureClass = new FailureClassEntity(failureClassId, description);

			failureClassCollection.add(managedFailureClass);
			failureClassHashMap.put((Integer) failureClassId, managedFailureClass);
		}
		failureClassDAO.batchInsertFailureClasses(failureClassCollection);
	}

	private void readUserEquipmentDataSheet(Workbook excelWorkbook) {
		HSSFSheet userEquipmentWorksheet = (HSSFSheet) excelWorkbook.getSheetAt(ExcelDataSheet.UE_TABLE.getPageNumber());

		int numRows = userEquipmentWorksheet.getLastRowNum();
		HSSFRow row;
		UserEquipmentEntity managedUserEquipment;
		Collection <UserEquipmentEntity> userEquipmentCollection = new ArrayList <UserEquipmentEntity>();
		for (int i = 1; i <= numRows; i++) {
			row = (HSSFRow) userEquipmentWorksheet.getRow(i);

			int typeAllocationCode = (int) row.getCell(0).getNumericCellValue();
			String marketName = "";
			String manufacturer = row.getCell(2).getStringCellValue();
			String accessCapability = row.getCell(3).getStringCellValue();
			String model = "";
			String vendor = row.getCell(5).getStringCellValue();
			String ueType = row.getCell(6).getStringCellValue();
			String os = row.getCell(7).getStringCellValue();
			String inputMode = row.getCell(8).getStringCellValue();
			try {
				marketName = row.getCell(1).getStringCellValue();
				model = row.getCell(4).getStringCellValue();
			}
			catch (IllegalStateException e) {
				HSSFCell marketNameCell = row.getCell(1);
				marketNameCell.setCellType(Cell.CELL_TYPE_STRING);
				HSSFCell modelNameCell = row.getCell(4);
				modelNameCell.setCellType(Cell.CELL_TYPE_STRING);
				marketName = marketNameCell.getStringCellValue();
				model = modelNameCell.getStringCellValue();
			}

			if (userEquipmentHashMap.get((Integer) typeAllocationCode) != null) {
				continue;
			}

			managedUserEquipment = new UserEquipmentEntity(typeAllocationCode, marketName, manufacturer, accessCapability, model, vendor, ueType, os, inputMode);
			userEquipmentCollection.add(managedUserEquipment);
			userEquipmentHashMap.put((Integer) typeAllocationCode, managedUserEquipment);
		}
		userEquipmentDAO.batchInsertUserEquipment(userEquipmentCollection);
	}

	private void readOperatorDataSheet(Workbook excelWorkbook) {
		HSSFSheet operatorWorksheet = (HSSFSheet) excelWorkbook.getSheetAt(ExcelDataSheet.MCC_MNC_TABLE.getPageNumber());
		// NEW IMPLEMENTATION
		int numRows = operatorWorksheet.getLastRowNum();
		HSSFRow row;
		CountryEntity managedCountry;
		CountryCodeNetworkCodeEntittyCK managedCountryCodeNetworkCodeCK;
		CountryCodeNetworkCodeEntity managedCountryCodeNetworkCode;
		Collection <CountryCodeNetworkCodeEntity> countryCodeNetworkCodeCollection = new ArrayList <CountryCodeNetworkCodeEntity>();
		for (int i = 1; i <= numRows; i++) {
			row = (HSSFRow) operatorWorksheet.getRow(i);
			int countryCode = (int) row.getCell(0).getNumericCellValue();
			int networkCode = (int) row.getCell(1).getNumericCellValue();
			String country = row.getCell(2).getStringCellValue();
			String operator = row.getCell(3).getStringCellValue();

			if (countryHashMap.get((Integer) countryCode) != null) {
				managedCountry = countryHashMap.get((Integer) countryCode);
			}
			else {
				managedCountry = new CountryEntity(countryCode, country);
				countryHashMap.put(countryCode, managedCountry);
			}

			managedCountryCodeNetworkCodeCK = new CountryCodeNetworkCodeEntittyCK(managedCountry, networkCode);

			if (countryCodeNetworkCodeHashMap.get(managedCountryCodeNetworkCodeCK.hashCode()) != null) {
				continue;
			}

			managedCountryCodeNetworkCode = new CountryCodeNetworkCodeEntity(managedCountryCodeNetworkCodeCK, operator);

			countryCodeNetworkCodeCollection.add(managedCountryCodeNetworkCode);

			countryCodeNetworkCodeHashMap.put((Integer) managedCountryCodeNetworkCodeCK.hashCode(), managedCountryCodeNetworkCode);
		}
		countryCodeNetworkCodeDAO.batchInsertCountryCodeNetworkCode(countryCodeNetworkCodeCollection);
	}

	public String getTimestamp() {
		return errorLogWriterService.getTimestamp();
	}

	public String getAddedCount() {
		return Integer.toString(validRowsAdded);
	}

	public String getRejectedCount() {
		return Integer.toString(invalidRowsRejected);
	}

	public void writeToLogFile() {
		java.nio.file.Path logFilePath = createLogFileIfNotExists();
		String importResultLogEntry = createImportResultLogEntry();
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath.toString(), true)))) {
			out.print(importResultLogEntry);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public java.nio.file.Path createLogFileIfNotExists() {
		String operatingSystem = (System.getProperty("os.name").toLowerCase());
		if (operatingSystem.indexOf("win") >= 0) {
			logFilePath = createLogFileForWindowsSystem();
		}
		if (operatingSystem.indexOf("nix") >= 0 || operatingSystem.indexOf("nux") >= 0 || operatingSystem.indexOf("aix") > 0) {
			logFilePath = createLogFileForUnixSystem();
		}
		return logFilePath;
	}

	private java.nio.file.Path createLogFileForWindowsSystem() {
		String dataImportServiceEJBPathString = DataImporterBean.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		dataImportServiceEJBPathString = dataImportServiceEJBPathString.substring(1);
		java.nio.file.Path pathOfDataImportServiceClass = Paths.get(dataImportServiceEJBPathString);
		java.nio.file.Path JBossDeploymentsPath = pathOfDataImportServiceClass.getParent().getParent().getParent();
		logFilePath = Paths.get(JBossDeploymentsPath.toString() + "\\log.txt");
		if (Files.notExists(logFilePath)) {
			try {
				Files.createFile(logFilePath);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logFilePath;
	}

	private java.nio.file.Path createLogFileForUnixSystem() {
		String dataImportServiceEJBPathString = DataImporterBean.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		java.nio.file.Path pathOfDataImportServiceClass = Paths.get(dataImportServiceEJBPathString);
		java.nio.file.Path JBossDeploymentsPath = pathOfDataImportServiceClass.getParent().getParent().getParent();
		logFilePath = Paths.get(JBossDeploymentsPath.toString() + "/log.txt");
		if (Files.notExists(logFilePath)) {
			try {
				Files.createFile(logFilePath);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logFilePath;
	}

	public String createImportResultLogEntry() {

		String result = "";
		result = result + "Import Details=" + ",";
		result = result + "Timestamp=" + getTimestamp() + ",";
		result = result + "Time Taken=" + duration + ",";
		result = result + "Valid Records=" + validRowsAdded + ",";
		result = result + "Invalid Records=" + invalidRowsRejected + ",";
		return result;
	}

	public void finaliseErrorLogEntry(String importType) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath.toString(), true)))) {
			out.print("Import Type=" + importType + System.getProperty("line.separator"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
