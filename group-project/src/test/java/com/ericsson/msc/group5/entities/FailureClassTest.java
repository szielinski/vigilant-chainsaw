package com.ericsson.msc.group5.entities;

import com.ericsson.msc.failuremanagement.failureslog.basedata.business.FailureClassDataBean;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.EventCauseEntityCK;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional
public class FailureClassTest {

    @PersistenceContext
    private EntityManager em;

    private static String INITIAL_DESCRIPTION = "HIGH PRIORITY ACCESS";

    @Inject
    private FailureClassDataBean failureClassService;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void basicCRUDTest() throws Exception {
        int id = 0;

        FailureClassEntity createdFC = new FailureClassEntity(id, INITIAL_DESCRIPTION);
        failureClassService.addFailureClass(createdFC);

        ArrayList<FailureClassEntity> failureClasses = new ArrayList<>();
        Collection<?> failureClassesCollection = failureClassService.getFailureClasses();

        for (Object e : failureClassesCollection) {
            failureClasses.add((FailureClassEntity) e);
        }
        ;
        FailureClassEntity loadedFC = null;
        for (FailureClassEntity f : failureClasses) {
            if (f.getFailureClass().equals(id)) {
                loadedFC = f;
                break;
            }
        }
        assertEquals("Failed to insert", INITIAL_DESCRIPTION, loadedFC.getDescription());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testEquality() {
        FailureClassEntity failureClass = new FailureClassEntity(1, "description");
        FailureClassEntity otherFailureClass = new FailureClassEntity(2, "other description");

        assertTrue(failureClass.equals(new FailureClassEntity(1, "other failure class")));
        assertFalse(failureClass.equals(null));
        assertFalse(failureClass.equals(new Integer(0)));
        assertFalse(failureClass.equals(new EventCauseEntityCK(0, 1)));
        assertFalse((new FailureClassEntity()).equals(failureClass));
        assertFalse(failureClass.equals(otherFailureClass));
        assertTrue(failureClass.hashCode() == (new FailureClassEntity(1, "other failure class").hashCode()));
    }
}
