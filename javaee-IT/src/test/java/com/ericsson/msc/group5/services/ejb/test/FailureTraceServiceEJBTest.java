package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.network.data.business.FailureTraceDataBean;
import data.FailureTraceEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class FailureTraceServiceEJBTest {

    @EJB
    private FailureTraceDataBean service;

    @Test
    public void addFailureTracesTest() {

        FailureTraceEntity failureTraceOne = new FailureTraceEntity();
        failureTraceOne.setFailureTraceId(10L);
        FailureTraceEntity failureTraceTwo = new FailureTraceEntity();
        failureTraceTwo.setFailureTraceId(20L);

        FailureTraceEntity[] failureTraceArray = {failureTraceOne, failureTraceTwo};

        Collection<FailureTraceEntity> failureTraces = new ArrayList<>();
        for (FailureTraceEntity f : failureTraceArray) {
            failureTraces.add(f);
        }

        service.addFailureTraces(failureTraces);

        Collection<FailureTraceEntity> retrievedFailureTraces = service.getAllFailureTraces();

        for (FailureTraceEntity f : retrievedFailureTraces) {
            assertEquals(false, failureTraces.contains(f));
        }
    }

}
