package com.ericsson.msc.group5.entities;

import com.ericsson.msc.failuremanagement.network.data.business.*;
import data.*;
import data.dao.jpa.FailureTraceJPA;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@Transactional
public class FailureTraceTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Inject
    private FailureTraceDataBean failureTraceService;
    @Inject
    private FailureTraceJPA failureTraceDAO;
    @Inject
    private UserEquipmentDataBean userEquipmentService;
    @Inject
    private FailureClassDataBean failureClassService;
    @Inject
    private EventCauseDataBean eventCauseService;
    // @Inject
    // private CountryService countryService;
    @Inject
    private CountryCodeNetworkCodeDataBean countryCodeNetworkCodeService;

    private final static Integer INITIAL_DURATION = 1000;
    private final static Integer UPDATED_DURATION = 10000;

    private final static Integer INITIAL_CELL_ID = 10;
    private final static Integer UPDATED_CELL_ID = 20;

    private final static Date INITIAL_DATE = new Date(100100101);
    private final static Date UPDATED_DATE = new Date(1001001011532L);

    private final static String INITIAL_HIER321 = "hier321";
    private final static String UPDATED_HIER321 = "updated hier321";

    private final static String INITIAL_HIER32 = "hier32";
    private final static String UPDATED_HIER32 = "updated hier32";

    private final static String INITIAL_HIER3 = "hier3";
    private final static String UPDATED_HIER3 = "updated hier3";

    private final static String INITIAL_IMSI = "test imsi";
    private final static String UPDATED_IMSI = "updated test imsi";

    private final static String INITIAL_NE_VERSION = "12A";
    private final static String UPDATED_NE_VERSION = "26B";

    private FailureClassEntity testFailureClass;
    private EventCauseEntity testEventCause;
    private UserEquipmentEntity testUserEquipment;
    private CountryEntity testCountry;
    private CountryCodeNetworkCodeEntity testCountryCodeNetworkCode;

    private Long id = 0L;

    // @Before
    // public void preparePersistenceTest() throws Exception {
    // clearData();
    // insertData();
    // startTransaction();
    // }
    //
    // @After
    // public void commitTransaction() throws Exception {
    // utx.commit();
    // }

    public void insertData() {
        // utx.begin();
        // em.joinTransaction();

        FailureTraceEntity testFailureTrace = new FailureTraceEntity();
        testFailureTrace.setFailureTraceId(0L);

        testFailureTrace.setCellId(INITIAL_CELL_ID);
        testFailureTrace.setDateTime(INITIAL_DATE);
        testFailureTrace.setDuration(INITIAL_DURATION);
        testFailureTrace.setHier321Id(INITIAL_HIER321);
        testFailureTrace.setHier32Id(INITIAL_HIER32);
        testFailureTrace.setHier3Id(INITIAL_HIER3);
        testFailureTrace.setIMSI(INITIAL_IMSI);
        testFailureTrace.setNeVersion(INITIAL_NE_VERSION);

        testUserEquipment = new UserEquipmentEntity(0, "marketing name", "manufacturer", "access capability", "model", "vendor", "user equipment type",
                "operating system", "input mode");
        userEquipmentService.addUserEquipment(testUserEquipment);
        // em.persist(testUserEquipment);

        testFailureTrace.setUserEquipment(testUserEquipment);

        testFailureClass = new FailureClassEntity(0, "description");
        testFailureTrace.setFailureClass(testFailureClass);
        failureClassService.addFailureClass(testFailureClass);
        // em.persist(testFailureClass);
        testEventCause = new EventCauseEntity(new EventCauseEntityCK(0, 0), "desc");
        eventCauseService.addEventCause(testEventCause);
        // em.persist(testEventCause);
        testFailureTrace.setEventCause(testEventCause);

        testCountry = new CountryEntity();
        testCountry.setCountry("country");
        testCountry.setCountryCode(231);
        // countryService.insertCountry(testCountry);
        // em.persist(testCountry);
        testCountryCodeNetworkCode = new CountryCodeNetworkCodeEntity(new CountryCodeNetworkCodeEntittyCK(testCountry, 0), "operator");
        testFailureTrace.setCountryCodeNetworkCode(testCountryCodeNetworkCode);
        countryCodeNetworkCodeService.addCountryCodeNetworkCode(testCountryCodeNetworkCode);
        // em.persist(testCountryCodeNetworkCode);
        testFailureTrace.setDuration(INITIAL_DURATION);
        failureTraceService.addFailureTrace(testFailureTrace);
        // em.persist(testFailureTrace);
        id = testFailureTrace.getFailureTraceId();

        // utx.commit();
        // em.clear();
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void basicCRUDTest() throws Exception {
        insertData();
        ArrayList<FailureTraceEntity> failureTraces = new ArrayList<>();
        Collection<?> failureTraceCollection = failureTraceService.getAllFailureTraces();
        System.out.println("SIZE OF COLL : " + failureTraceCollection.size());
        for (Object f : failureTraceCollection) {
            failureTraces.add((FailureTraceEntity) f);
        }
        ;
        FailureTraceEntity loadedFT = null;
        for (FailureTraceEntity f : failureTraces) {
            System.out.println("ID COMPARISON: " + f.getIMSI() + " = " + id);
            if (f.getIMSI().equals(INITIAL_IMSI)) {
                loadedFT = f;
                break;
            }
        }
        assertEquals("Failed to fetch", loadedFT.getCountryCodeNetworkCode(), testCountryCodeNetworkCode);
        assertEquals("Failed to fetch", loadedFT.getEventCause(), testEventCause);
        assertEquals("Failed to fetch", loadedFT.getFailureClass(), testFailureClass);
        assertEquals("Failed to fetch", loadedFT.getUserEquipment(), testUserEquipment);
        assertEquals("Failed to insert", INITIAL_DURATION, loadedFT.getDuration());
        assertEquals("Failed to insert", INITIAL_CELL_ID, loadedFT.getCellId());
        assertEquals("Failed to insert", INITIAL_DATE, loadedFT.getDateTime());
        assertEquals("Failed to insert", INITIAL_HIER321, loadedFT.getHier321Id());
        assertEquals("Failed to insert", INITIAL_HIER32, loadedFT.getHier32Id());
        assertEquals("Failed to insert", INITIAL_HIER3, loadedFT.getHier3Id());
        assertEquals("Failed to insert", INITIAL_IMSI, loadedFT.getIMSI());
        assertEquals("Failed to insert", INITIAL_NE_VERSION, loadedFT.getNeVersion());

        FailureTraceEntity updatedFT = new FailureTraceEntity();
        updatedFT.setFailureTraceId(1L);
        updatedFT.setCellId(UPDATED_CELL_ID);
        updatedFT.setDateTime(UPDATED_DATE);
        updatedFT.setDuration(UPDATED_DURATION);
        updatedFT.setHier321Id(UPDATED_HIER321);
        updatedFT.setHier32Id(UPDATED_HIER32);
        updatedFT.setHier3Id(UPDATED_HIER3);
        updatedFT.setIMSI(UPDATED_IMSI);
        updatedFT.setNeVersion(UPDATED_NE_VERSION);

        failureTraceService.addFailureTrace(updatedFT);

        assertEquals("Failed to insert", UPDATED_DURATION, updatedFT.getDuration());
        assertEquals("Failed to insert", UPDATED_CELL_ID, updatedFT.getCellId());
        assertEquals("Failed to insert", UPDATED_DATE, updatedFT.getDateTime());
        assertEquals("Failed to insert", UPDATED_HIER321, updatedFT.getHier321Id());
        assertEquals("Failed to insert", UPDATED_HIER32, updatedFT.getHier32Id());
        assertEquals("Failed to insert", UPDATED_HIER3, updatedFT.getHier3Id());
        assertEquals("Failed to insert", UPDATED_IMSI, updatedFT.getIMSI());
        assertEquals("Failed to insert", UPDATED_NE_VERSION, updatedFT.getNeVersion());

        // em.remove(updatedFT);
        // FailureTrace shouldBeNull = em.find(FailureTrace.class, id);
        // assertNull("Failed to delete", shouldBeNull);

        // em.remove(loadedFT);
    }

    // private void clearData() throws Exception {
    // utx.begin();
    // em.joinTransaction();
    // System.out.println("Dumping old records...");
    // em.createQuery("delete from group5.entities.FailureTrace").executeUpdate();
    // utx.commit();
    // }
    //
    // private void startTransaction() throws Exception {
    // utx.begin();
    // em.joinTransaction();
    // }
}
