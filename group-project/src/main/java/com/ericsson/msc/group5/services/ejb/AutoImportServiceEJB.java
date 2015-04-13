package com.ericsson.msc.group5.services.ejb;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.ericsson.msc.group5.services.DataImportService;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class AutoImportServiceEJB {

	private WatchService watcher;
	private WatchKey key;
	private String autoImportPathString;
	private Path pathOfAutoImportServiceClass;
	private Path JBossDeploymentsPath;
	private Path autoImportFolderPath;
	private String OperatingSystem;
	@Resource
	private TimerService timerService;
	@EJB
	private DataImportService dataImportService;

	@PostConstruct
	public void onStart() throws InterruptedException {
		setOperatingSystem(System.getProperty("os.name").toLowerCase());
		if (OperatingSystem.indexOf("win") >= 0) {
			createDirectoryForWindowsSystem();
		}
		if (OperatingSystem.indexOf("nix") >= 0 || OperatingSystem.indexOf("nux") >= 0 || OperatingSystem.indexOf("aix") > 0) {
			createDirectoryForUnixSystem();
		}
		try {
			watcher = FileSystems.getDefault().newWatchService();
			autoImportFolderPath.register(watcher, ENTRY_CREATE);
			System.out.println("Watcher added for " + autoImportFolderPath.getFileName());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		timerService.createIntervalTimer(new Date(), new Date().getTime(), new TimerConfig());
	}

	@Lock(LockType.READ)
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void run() throws InterruptedException {
		try {
			key = watcher.take();
		}
		catch (InterruptedException ex) {
			System.err.println(ex);
		}
		for (WatchEvent <?> event : key.pollEvents()) {
			WatchEvent.Kind <?> kind = event.kind();
			@SuppressWarnings("unchecked")
			WatchEvent <Path> ev = (WatchEvent <Path>) event;
			Path fileName = ev.context();
			String extension = FilenameUtils.getExtension(fileName.toString());
			if (kind == ENTRY_CREATE) {
				System.out.println("File Created.");
				if (extension.equals("xls")) {
					File sourceFile = new File(autoImportFolderPath.toString() + fileName);
					while ( !sourceFile.renameTo(sourceFile)) {
						// Cannot read from file, windows still working on it.
						Thread.sleep(10);
					}
					try {
						HSSFWorkbook excelWorkbook = new HSSFWorkbook(new FileInputStream(sourceFile));
						dataImportService.importSpreadsheet(excelWorkbook);
						System.out.println("Data from file " + fileName + " added successfully");
					}
					catch (IOException e) {
						e.printStackTrace();
						System.out.println(fileName + " has incorrect layout for upload.");
					}
				}
				else {
					System.out.println("File " + fileName + " is not a valid file type for upload.");
				}
			}
		}
		boolean valid = key.reset();
		if ( !valid) {
			System.out.println("key reset error");
		}
	}

	private void createDirectoryForWindowsSystem() {
		autoImportPathString = AutoImportServiceEJB.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// autoImportPathString = autoImportPathString.replace('/',
		// File.separatorChar);
		autoImportPathString = autoImportPathString.substring(1);
		pathOfAutoImportServiceClass = Paths.get(autoImportPathString);
		JBossDeploymentsPath = pathOfAutoImportServiceClass.getParent().getParent().getParent();
		autoImportFolderPath = Paths.get(JBossDeploymentsPath.toString() + "\\autoImportFolder");
		if (Files.notExists(autoImportFolderPath)) {
			File autoImportDirectory = new File(autoImportFolderPath.toString());
			autoImportDirectory.mkdirs();
		}
	}

	private void createDirectoryForUnixSystem() {

	}

	@Timeout
	public void onTimeout(Timer timer) {
		System.out.println("Bean timed out");
	}

	@PreDestroy
	public void onDestroy() {
		try {
			watcher.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOperatingSystem() {
		return OperatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		OperatingSystem = operatingSystem;
	}
}
