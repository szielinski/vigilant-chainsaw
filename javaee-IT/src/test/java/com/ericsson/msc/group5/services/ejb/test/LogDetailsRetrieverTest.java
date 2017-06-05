package com.ericsson.msc.group5.services.ejb.test;

import dataimport.business.LogDetailsRetriever;
import org.jboss.arquillian.junit.Arquillian;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class LogDetailsRetrieverTest {

    @EJB
    private LogDetailsRetriever logger;

    @Test
    public void testLogger() {
        ArrayList<String> logString = new ArrayList<String>();
        logString.add("Import Details=");
        logString.add("Timestamp=2015/04/14 12:04:52");
        logString.add("Time Taken=6088");
        logString.add("Valid Records=29997");
        logString.add("Invalid Records=1");
        JSONObject json = logger.createJsonObjectFromArrayListOfDetails(logString);
        assertEquals(false, json.isEmpty());
    }
}
