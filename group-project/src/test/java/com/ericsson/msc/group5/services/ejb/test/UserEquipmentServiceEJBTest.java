package com.ericsson.msc.group5.services.ejb.test;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.msc.failuremanagement.failureslog.basedata.business.UserEquipmentData;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;

@RunWith(Arquillian.class)
@Transactional
public class UserEquipmentServiceEJBTest {

	@EJB
	private UserEquipmentData service;

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void addUserEquipmentTest() {
		UserEquipmentEntity [] userEquipmentArray = {
				new UserEquipmentEntity(1, "one", "one", "one", "one", "one", "one", "one", "one"),
				new UserEquipmentEntity(2, "two", "two", "two", "two", "two", "two", "two", "two")};

		Collection <UserEquipmentEntity> userEquipments = new ArrayList <>();
		for (UserEquipmentEntity ue : userEquipmentArray) {
			userEquipments.add(ue);
		}

		service.addUserEquipments(userEquipments);

		Collection <UserEquipmentEntity> retrievedUserEquipment = service.getAllUserEquipments();

		for (UserEquipmentEntity ue : retrievedUserEquipment) {
			assertTrue("An object failed to be retrieved", userEquipments.contains(ue));
			assertTrue(ue.equals(ue));
			assertTrue(ue.hashCode() == ue.hashCode());
		}
	}

}
