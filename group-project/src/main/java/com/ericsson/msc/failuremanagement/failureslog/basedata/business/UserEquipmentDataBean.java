package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.UserEquipmentDAO;

@Stateless
@Local
public class UserEquipmentDataBean implements UserEquipmentData {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserEquipmentDAO dao;

	@Override
	public Collection <UserEquipmentEntity> getAllUserEquipments() {
		return dao.getAllUserEquipment();
	}

	@Override
	public void addUserEquipments(Collection <UserEquipmentEntity> userEquipments) {
		dao.batchInsertUserEquipment(userEquipments);
	}

	@Override
	public void addUserEquipment(UserEquipmentEntity userEquipment) {
		dao.insertUserEquipment(userEquipment);
	}

}
