package com.ericsson.msc.failuremanagement.failureslog.basedata.service;

import org.json.simple.JSONObject;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Stateless
@Local
public class LogDetailsRetriever {

    public JSONObject retrieveLogDetailsAsJson() {
        Path logFilePath = getLogFilePath();
        String latestImportDetails = null;
        String[] importDetailsSplit = null;
        ArrayList<String> importDetailsSplitToArrayList = null;
        ArrayList<String> logFileContent = new ArrayList<String>();
        if (Files.exists(logFilePath)) {
            try {
                for (String s : Files.readAllLines(logFilePath, StandardCharsets.UTF_8)) {
                    logFileContent.add(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (logFileContent.size() != 0) {
            latestImportDetails = logFileContent.get(logFileContent.size() - 1);
            importDetailsSplit = latestImportDetails.split(",");
            importDetailsSplitToArrayList = new ArrayList<String>();
            for (String s : importDetailsSplit) {
                importDetailsSplitToArrayList.add(s);
            }
        }
        JSONObject logAsJson = new JSONObject();
        if (importDetailsSplitToArrayList != null) {
            logAsJson = createJsonObjectFromArrayListOfDetails(importDetailsSplitToArrayList);
        }
        return logAsJson;
    }

    public Path getLogFilePath() {
        Path logFilePath = null;
        String operatingSystem = (System.getProperty("os.name").toLowerCase());
        if (operatingSystem.indexOf("win") >= 0) {
            logFilePath = getLogFilePathForWindowsSystem();
        }
        if (operatingSystem.indexOf("nix") >= 0 || operatingSystem.indexOf("nux") >= 0 || operatingSystem.indexOf("aix") > 0) {
            logFilePath = getLogFilePathForUnixSystem();
        }

        return logFilePath;
    }

    private Path getLogFilePathForUnixSystem() {
        Path logFilePath = null;
        String thisClassRootPathString = LogDetailsRetriever.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Path thisClassRootPath = Paths.get(thisClassRootPathString);
        Path JBossDeploymentsPath = thisClassRootPath.getParent().getParent().getParent();
        return logFilePath = Paths.get(JBossDeploymentsPath.toString() + "/log.txt");
    }

    private Path getLogFilePathForWindowsSystem() {
        Path logFilePath = null;
        String thisClassRootPathString = LogDetailsRetriever.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        thisClassRootPathString = thisClassRootPathString.substring(1);
        Path thisClassRootPath = Paths.get(thisClassRootPathString);
        Path JBossDeploymentsPath = thisClassRootPath.getParent().getParent().getParent();
        return logFilePath = Paths.get(JBossDeploymentsPath.toString() + "\\log.txt");
    }

    @SuppressWarnings("unchecked")
    public JSONObject createJsonObjectFromArrayListOfDetails(ArrayList<String> listOfDetailsForLastLogEntry) {
        JSONObject logAsJson = new JSONObject();
        ArrayList<String> detailsSplitAsArrayList = new ArrayList<String>();
        for (String s : listOfDetailsForLastLogEntry) {
            String[] detailSplit = s.split("=");
            for (String str : detailSplit) {
                detailsSplitAsArrayList.add(str);
            }
        }
        logAsJson.put("Timestamp", detailsSplitAsArrayList.get(detailsSplitAsArrayList.indexOf("Timestamp") + 1));
        logAsJson.put("TimeTaken", detailsSplitAsArrayList.get(detailsSplitAsArrayList.indexOf("Time Taken") + 1));
        logAsJson.put("ValidRecords", detailsSplitAsArrayList.get(detailsSplitAsArrayList.indexOf("Valid Records") + 1));
        logAsJson.put("InvalidRecords", detailsSplitAsArrayList.get(detailsSplitAsArrayList.indexOf("Invalid Records") + 1));
        logAsJson.put("ImportType", detailsSplitAsArrayList.get(detailsSplitAsArrayList.indexOf("Import Type") + 1));
        return logAsJson;
    }
}
