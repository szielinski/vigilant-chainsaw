package com.ericsson.msc.group5.services.ejb.test;

import com.ericsson.msc.failuremanagement.network.data.business.UserEquipmentDataBean;
import data.UserEquipmentEntity;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@Transactional
public class UserEquipmentServiceEJBTest {

    @EJB
    private UserEquipmentDataBean service;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void addUserEquipmentTest() {
        UserEquipmentEntity[] userEquipmentArray = {
                new UserEquipmentEntity(1, "one", "one", "one", "one", "one", "one", "one", "one"),
                new UserEquipmentEntity(2, "two", "two", "two", "two", "two", "two", "two", "two")};

        Collection<UserEquipmentEntity> userEquipments = new ArrayList<>();
        for (UserEquipmentEntity ue : userEquipmentArray) {
            userEquipments.add(ue);
        }

        service.addUserEquipments(userEquipments);

        Collection<UserEquipmentEntity> retrievedUserEquipment = service.getAllUserEquipments();

        for (UserEquipmentEntity ue : retrievedUserEquipment) {
            assertTrue("An object failed to be retrieved", userEquipments.contains(ue));
            assertTrue(ue.equals(ue));
            assertTrue(ue.hashCode() == ue.hashCode());
        }
    }

}
