package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.network.data.business.FailureClassDataBean;
import data.FailureClassEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class FailureClassServiceEJBTest {

    @EJB
    FailureClassDataBean failureClassServiceEJB;

    @Test
    public void addFailureClassesTest() {
        FailureClassEntity[] failureClassArray = {new FailureClassEntity(1, "first"), new FailureClassEntity(2, "second"), new FailureClassEntity(3, "third")};

        Collection<FailureClassEntity> failureClasses = new ArrayList<>();
        for (FailureClassEntity f : failureClassArray) {
            failureClasses.add(f);
        }

        failureClassServiceEJB.addFailureClasses(failureClasses);

        Collection<FailureClassEntity> retrievedFailureClasses = failureClassServiceEJB.getFailureClasses();

        for (FailureClassEntity f : retrievedFailureClasses) {
            assertTrue("An object failed to be retrieved", failureClasses.contains(f));
            assertTrue(f.equals(f));
            assertTrue(f.hashCode() == f.hashCode());
        }
    }
}
