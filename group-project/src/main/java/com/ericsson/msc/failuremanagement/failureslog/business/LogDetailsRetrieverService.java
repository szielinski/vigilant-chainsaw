package com.ericsson.msc.failuremanagement.failureslog.business;

import java.util.ArrayList;
import javax.ejb.Local;
import org.json.simple.JSONObject;

@Local
public interface LogDetailsRetrieverService {

	public JSONObject retrieveLogDetailsAsJson();

	JSONObject createJsonObjectFromArrayListOfDetails(ArrayList <String> listOfDetailsForLastLogEntry);
}
