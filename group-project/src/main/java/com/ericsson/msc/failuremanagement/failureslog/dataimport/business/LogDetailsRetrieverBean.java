package com.ericsson.msc.failuremanagement.failureslog.dataimport.business;

import java.util.ArrayList;
import javax.ejb.Local;
import org.json.simple.JSONObject;

@Local
public interface LogDetailsRetrieverBean {

	public JSONObject retrieveLogDetailsAsJson();

	JSONObject createJsonObjectFromArrayListOfDetails(ArrayList <String> listOfDetailsForLastLogEntry);
}
