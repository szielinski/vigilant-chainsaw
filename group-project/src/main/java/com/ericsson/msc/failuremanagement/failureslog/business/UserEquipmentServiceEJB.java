package com.ericsson.msc.failuremanagement.failureslog.business;

import java.util.Collection;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.data.UserEquipment;
import com.ericsson.msc.failuremanagement.failureslog.data.UserEquipmentDAO;

@Stateless
@Local
public class UserEquipmentServiceEJB implements UserEquipmentService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserEquipmentDAO dao;

	@Override
	public Collection <UserEquipment> getAllUserEquipments() {
		return dao.getAllUserEquipment();
	}

	@Override
	public void addUserEquipments(Collection <UserEquipment> userEquipments) {
		dao.batchInsertUserEquipment(userEquipments);
	}

	@Override
	public void addUserEquipment(UserEquipment userEquipment) {
		dao.insertUserEquipment(userEquipment);
	}

}
